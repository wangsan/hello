/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */
package top.sailingsan.dl4j.example;

import java.io.File;

import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.SplitTestAndTrain;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
import org.nd4j.linalg.learning.config.Sgd;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import lombok.extern.slf4j.Slf4j;
import top.sailingsan.dl4j.utils.DownloaderUtility;

/**
 * created by wangsan on 2022/04/02.
 *
 * @author wangsan
 */
@Slf4j
public class IrisClassifier {

    public static void main(String[] args) {
        //        System.setProperty("org.bytedeco.javacpp.logger.debug", "true");

        System.out.println(System.getProperty("os.name"));
        System.out.println(System.getProperty("os.version"));
        System.out.println(System.getProperty("os.arch"));
        System.out.println(System.getProperty("java.vm.name"));
        System.out.println(System.getProperty("java.vm.version"));
        System.out.println(System.getProperty("org.bytedeco.javacpp.logger.debug"));

        // 1. get the dataset using the record reader. CSVRecordReader handles loading/parsing
        RecordReader recordReader = new CSVRecordReader();
        try {
            recordReader.initialize(new FileSplit(new File(DownloaderUtility.IRISDATA.Download(), "iris.txt")));
        } catch (Exception e) {
            log.error("download dataset error");
        }

        // 2.  RecordReaderDataSetIterator handles conversion to DataSet objects, ready for use in neural network
        RecordReaderDataSetIterator dataSetIterator = new RecordReaderDataSetIterator(recordReader, 150, 4, 3);
        DataSet allData = dataSetIterator.next();
        allData.shuffle();

        SplitTestAndTrain testAndTrain = allData.splitTestAndTrain(0.618);
        DataSet trainData = testAndTrain.getTrain();
        DataSet testData = testAndTrain.getTest();

        // 3. We need to normalize our data. We'll use NormalizeStandardize (which gives us mean 0, unit variance):
        DataNormalization normalizer = new NormalizerStandardize();
        normalizer.fit(trainData);
        normalizer.transform(trainData);
        normalizer.transform(testData);

        // 4. set net config
        log.info("Build model....");
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(6)
                .activation(Activation.TANH)
                .weightInit(WeightInit.XAVIER)
                .updater(new Sgd(0.2))
                .l2(1e-4)
                .list()
                .layer(new DenseLayer.Builder().nIn(4).nOut(3).build())
                .layer(new DenseLayer.Builder().nIn(3).nOut(3).build())
                .layer(new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        //Override the global TANH activation with softmax for this layer
                        .activation(Activation.SOFTMAX)
                        .nIn(3)
                        .nOut(3)
                        .build())
                .build();

        // 5. run the model
        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();
        // record score every 100 iter
        model.setListeners(new ScoreIterationListener(100));

        for (int i = 0; i < 1000; i++) {
            model.fit(trainData);
        }

        // 6. evaluate the model on the test set
        Evaluation eval = new Evaluation(3);
        INDArray output = model.output(testData.getFeatures());
        eval.eval(testData.getLabels(), output);
        log.info(eval.stats());
    }

}
