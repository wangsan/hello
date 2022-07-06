package top.sailingsan.dl4j.lesson2.lr;

import java.util.List;
import java.util.Random;

import org.deeplearning4j.datasets.iterator.utilty.ListDataSetIterator;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Sgd;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SingleLineRegression {
    public static void main(String[] args) {
        int batchSize = 100;
        int seed = 12345;
        double learningRate = 0.05;
        int dataLength = 1000;
        Random rand = new Random(seed);

        int epoch = 20;

        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(seed)
                .weightInit(WeightInit.XAVIER)
                .activation(Activation.RELU)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .updater(new Sgd(learningRate))
                .list()
                .layer(0, new OutputLayer.Builder()
                        .lossFunction(LossFunctions.LossFunction.MSE)
                        .activation(Activation.IDENTITY) // y=x
                        .nIn(1)
                        .nOut(1)
                        .build())
                .build();

        MultiLayerNetwork net = new MultiLayerNetwork(conf);
        net.init();
        net.init();
        log.info("net is {}", net.summary());

//        net.setListeners(new ScoreIterationListener(10));

        double[] input = new double[dataLength];
        double[] output = new double[dataLength];
        for (int i = 0; i < input.length; i++) {
            input[i] = rand.nextDouble() * 3;
            // y=0.5x+0.1
            output[i] = input[i] * 0.5 + 0.1;
        }
        INDArray inputArray = Nd4j.create(input, new int[] {input.length, 1});
        INDArray outputArray = Nd4j.create(output, new int[] {output.length, 1});
        DataSet dataSet = new DataSet(inputArray, outputArray);
        List<DataSet> listDs = dataSet.asList();

        ListDataSetIterator<DataSet> dataSetIterator = new ListDataSetIterator<>(listDs, batchSize);

        for (int i = 0; i < epoch; i++) {
            dataSetIterator.reset();
            net.fit(dataSetIterator);
            net.paramTable().forEach((k, v) -> System.out.println(" key: " + k + " value:" + v));
        }

        INDArray testInput = Nd4j.create(new double[] {10, 100}, new int[] {2, 1});
        INDArray testOutput = net.output(testInput);
        System.out.println(testInput);
        System.out.println(testOutput);

    }
}
