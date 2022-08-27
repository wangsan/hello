package top.sailingsan.dl4j.reid.controller;

import java.io.ByteArrayInputStream;

import org.datavec.image.loader.NativeImageLoader;
import org.datavec.image.transform.ResizeImageTransform;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.autodiff.samediff.SDVariable;
import org.nd4j.autodiff.samediff.SameDiff;
import org.nd4j.common.io.ClassPathResource;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FaceRecognitionController implements InitializingBean {

    private NativeImageLoader imageLoader = new NativeImageLoader(224, 224, 3, new ResizeImageTransform(224, 224));
    private ComputationGraph model;

    @ResponseBody
    @RequestMapping("/predict")
    public PredictResp predict(@RequestBody PredictReq req) {
        try {
            byte[] bytes = Base64Utils.decode(req.getImage().getBytes());
            INDArray indArray = imageLoader.asMatrix(new ByteArrayInputStream(bytes));
            float[] floats = model.output(indArray)[0].getRow(0).toFloatVector();
            return PredictResp.builder().code("0").message("success").data(floats).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @ResponseBody
    @RequestMapping("/cosine")
    public CosineResp cosine(@RequestBody CosineReq req) {
        try {
            byte[] bytes = Base64Utils.decode(req.getImage1().getBytes());
            INDArray indArray1 = imageLoader.asMatrix(new ByteArrayInputStream(bytes));
            float[] floats1 = model.output(indArray1)[0].getRow(0).toFloatVector();

            byte[] bytes2 = Base64Utils.decode(req.getImage2().getBytes());
            INDArray indArray2 = imageLoader.asMatrix(new ByteArrayInputStream(bytes2));
            float[] floats2 = model.output(indArray2)[0].getRow(0).toFloatVector();

            SameDiff samediff = SameDiff.create();
            SDVariable variable1 = samediff.var("input1", Nd4j.create(floats1));
            SDVariable variable2 = samediff.var("input2", Nd4j.create(floats2));
            samediff.math.cosineSimilarity(variable1, variable2);
            float distance = samediff.outputSingle(null, "cosinesimilarity").toFloatVector()[0];
            System.out.println(distance);
            return CosineResp.builder().code("0").message("success").data(distance).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/getModel")
    @ResponseBody
    public String getModel() {
        return model.summary();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        model = ModelSerializer.restoreComputationGraph(new ClassPathResource("face_reid_v1.zip").getFile());
    }
}
