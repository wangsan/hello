package top.sailingsan.dl4j.cookbook.face.reid;

import java.io.File;
import java.io.IOException;

import org.datavec.image.loader.NativeImageLoader;
import org.datavec.image.transform.ResizeImageTransform;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.ComputationGraphConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.deeplearning4j.nn.conf.layers.LossLayer;
import org.deeplearning4j.nn.conf.layers.SubsamplingLayer;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.autodiff.samediff.SDVariable;
import org.nd4j.autodiff.samediff.SameDiff;
import org.nd4j.common.io.ClassPathResource;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;

public class VGG16Test {
    public static void main(String[] args) throws IOException {
        //        ZooModel zooModel = VGG16.builder().build();
        //        ComputationGraph vggFace = (ComputationGraph) zooModel.initPretrained(PretrainedType.VGGFACE);
        //        System.out.println(vggFace.summary());
        //
        //        String vggLayerNames =
        //                "conv1_1,conv1_2,conv2_1,conv2_2,conv3_1,conv3_2,conv3_3,conv4_1,conv4_2,conv4_3,conv5_1,
        //                conv5_2,"
        //                        + "conv5_3";
        //        ComputationGraph model = buildModel();

        //
        //
        //        for (String name : vggLayerNames.split(",")) {
        //            model.getLayer(name).setParams(vggFace.getLayer(name).params().dup());
        //        }

        ComputationGraph model = ModelSerializer.restoreComputationGraph(new File(
                "/Users/wangqingpeng/git/github/hello/dl4jcookbook/vggfacereid/src/main/resources/face_reid_v1.zip"));

        System.out.println(model.summary());

        NativeImageLoader imageLoader = new NativeImageLoader(224, 224, 3, new ResizeImageTransform(224, 224));

        INDArray output1 = model.output(imageLoader.asMatrix(new ClassPathResource("b1.jpg").getFile()))[0];
        INDArray output2 = model.output(imageLoader.asMatrix(new ClassPathResource("b1.jpg").getFile()))[0];
        System.out.println(output1);
        System.out.println(output1.length());

        SameDiff samediff = SameDiff.create();
        SDVariable variable1 = samediff.var("input1", output1);
        SDVariable variable2 = samediff.var("input2", output2);
        samediff.math.cosineSimilarity(variable1, variable2);
        System.out.println(samediff.outputSingle(null, "cosinesimilarity"));

        //        final File file = new File("face_reid_v1.zip");
        //        ModelSerializer.writeModel(model, file, true);

        // perf rt=300ms
        //        long start = System.currentTimeMillis();
        //        for (int i = 0; i < 100; i++) {
        //            INDArray output = model.output(imageLoader.asMatrix(new ClassPathResource("b1.jpg").getFile()))
        //            [0];
        //            System.out.println(output);
        //        }
        //        System.out.println("use time " + (System.currentTimeMillis() - start) + " ms");
    }

    private static ComputationGraph buildModel() {
        ComputationGraphConfiguration conf = new NeuralNetConfiguration.Builder().seed(123)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT).activation(Activation.RELU)
                .graphBuilder()
                .addInputs("input1")
                .layer("conv1_1",
                        new ConvolutionLayer.Builder().kernelSize(3, 3).stride(1, 1).padding(1, 1).nIn(3).nOut(64)
                                .build(),
                        "input1")
                .layer("conv1_2",
                        new ConvolutionLayer.Builder().kernelSize(3, 3).stride(1, 1).padding(1, 1).nOut(64).build(),
                        "conv1_1")
                .layer("pool1",
                        new SubsamplingLayer.Builder().poolingType(SubsamplingLayer.PoolingType.MAX).kernelSize(2, 2)
                                .stride(2, 2).build(),
                        "conv1_2")
                // block 2
                .layer("conv2_1",
                        new ConvolutionLayer.Builder().kernelSize(3, 3).stride(1, 1).padding(1, 1).nOut(128).build(),
                        "pool1")
                .layer("conv2_2",
                        new ConvolutionLayer.Builder().kernelSize(3, 3).stride(1, 1).padding(1, 1).nOut(128).build(),
                        "conv2_1")
                .layer("pool2",
                        new SubsamplingLayer.Builder().poolingType(SubsamplingLayer.PoolingType.MAX).kernelSize(2, 2)
                                .stride(2, 2).build(),
                        "conv2_2")
                // block 3
                .layer("conv3_1",
                        new ConvolutionLayer.Builder().kernelSize(3, 3).stride(1, 1).padding(1, 1).nOut(256).build(),
                        "pool2")
                .layer("conv3_2",
                        new ConvolutionLayer.Builder().kernelSize(3, 3).stride(1, 1).padding(1, 1).nOut(256).build(),
                        "conv3_1")
                .layer("conv3_3",
                        new ConvolutionLayer.Builder().kernelSize(3, 3).stride(1, 1).padding(1, 1).nOut(256).build(),
                        "conv3_2")
                .layer("pool3",
                        new SubsamplingLayer.Builder().poolingType(SubsamplingLayer.PoolingType.MAX).kernelSize(2, 2)
                                .stride(2, 2).build(),
                        "conv3_3")
                // block 4
                .layer("conv4_1",
                        new ConvolutionLayer.Builder().kernelSize(3, 3).stride(1, 1).padding(1, 1).nOut(512).build(),
                        "pool3")
                .layer("conv4_2",
                        new ConvolutionLayer.Builder().kernelSize(3, 3).stride(1, 1).padding(1, 1).nOut(512).build(),
                        "conv4_1")
                .layer("conv4_3",
                        new ConvolutionLayer.Builder().kernelSize(3, 3).stride(1, 1).padding(1, 1).nOut(512).build(),
                        "conv4_2")
                .layer("pool4",
                        new SubsamplingLayer.Builder().poolingType(SubsamplingLayer.PoolingType.MAX).kernelSize(2, 2)
                                .stride(2, 2).build(),
                        "conv4_3")
                // block 5
                .layer("conv5_1",
                        new ConvolutionLayer.Builder().kernelSize(3, 3).stride(1, 1).padding(1, 1).nOut(512).build(),
                        "pool4")
                .layer("conv5_2",
                        new ConvolutionLayer.Builder().kernelSize(3, 3).stride(1, 1).padding(1, 1).nOut(512).build(),
                        "conv5_1")
                .layer("conv5_3",
                        new ConvolutionLayer.Builder().kernelSize(3, 3).stride(1, 1).padding(1, 1).nOut(512).build(),
                        "conv5_2")
                .layer("pool5",
                        new SubsamplingLayer.Builder().poolingType(SubsamplingLayer.PoolingType.MAX).kernelSize(2, 2)
                                .stride(2, 2).build(),
                        "conv5_3")
                .addLayer("out", new LossLayer.Builder().build(), "pool5").setOutputs("out")
                .setInputTypes(InputType.convolutionalFlat(224, 224, 3))
                .build();
        ComputationGraph network = new ComputationGraph(conf);
        network.init();
        return network;
    }

}
