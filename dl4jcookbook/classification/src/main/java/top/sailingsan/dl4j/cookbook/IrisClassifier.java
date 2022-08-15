/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */
package top.sailingsan.dl4j.cookbook;

import java.util.stream.IntStream;

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
import org.nd4j.common.io.ClassPathResource;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.SplitTestAndTrain;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Sgd;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import lombok.extern.slf4j.Slf4j;

/**
 * created by wangsan on 2022/04/02.
 *
 * @author wangsan
 */
@Slf4j
public class IrisClassifier {

    public static void main(String[] args) throws Exception {
        // 1. load data
        RecordReader recordReader = new CSVRecordReader();
        recordReader.initialize(new FileSplit(new ClassPathResource("iris.txt").getFile()));

        // 2. get dataset
        RecordReaderDataSetIterator dataSetIterator = new RecordReaderDataSetIterator(recordReader, 150, 4, 3);
        System.out.println("total label： " + dataSetIterator.totalOutcomes());

        DataSet dataSet = dataSetIterator.next();
        dataSet.shuffle(7);
        System.out.println("total size： " + dataSet.numExamples());
        SplitTestAndTrain testAndTrain = dataSet.splitTestAndTrain(0.8);
        DataSet trainData = testAndTrain.getTrain();
        DataSet testData = testAndTrain.getTest();

        // 3. normalize data
        NormalizerStandardize standardize = new NormalizerStandardize();
        standardize.fit(trainData);
        standardize.transform(trainData);
        standardize.transform(testData);

        // 4. config net
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(7)
                .weightInit(WeightInit.XAVIER)
                .updater(new Sgd(0.1))
                .activation(Activation.RELU)
                .list()
                .layer(new DenseLayer.Builder().nIn(4).nOut(3).build())
//                .layer(new DenseLayer.Builder().nIn(3).nOut(3).build())
                .layer(new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .activation(Activation.SOFTMAX)
                        //                        .dropOut(0.9)
                        .nIn(3)
                        .nOut(3)
                        .build())
                .build();

        // 5. train model
        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();
        model.setListeners(new ScoreIterationListener(10));
        IntStream.range(0, 100).forEach(i -> model.fit(trainData));

        // 6. evaluate model
        INDArray output = model.output(testData.getFeatures());
        Evaluation evaluation = new Evaluation(3);
        evaluation.eval(testData.getLabels(), output);
        System.out.println(evaluation.stats());

        System.out.println(model.output(Nd4j.create(new float[] {5.1f, 3.5f, 1.4f, 0.2f}, new int[] {1, 4})));
    }

}
