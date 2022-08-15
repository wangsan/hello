package top.sailingsan.dl4j.cookbook;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class CookBookController {

    @Value("${modelFilePath}")
    private String modelFilePath;

    @GetMapping("/")
    public String main(final Model model) {
        model.addAttribute("message", "Welcome to Java deep learning!");
        return "welcome";
    }

    @PostMapping("/")
    public String fileUpload(final Model model, final @RequestParam("uploadFile") MultipartFile multipartFile)
            throws IOException, InterruptedException {
        final List<String> results = generateStringOutput(multipartFile, modelFilePath);
        model.addAttribute("message", "Welcome to Java deep learning!");
        model.addAttribute("results", results);
        return "welcome";
    }

    public List<String> generateStringOutput(MultipartFile multipartFile, String modelFilePath)
            throws IOException, InterruptedException {
        final List<String> results = new ArrayList<>();
        File convFile = File.createTempFile(multipartFile.getOriginalFilename(), null,
                new File(System.getProperty("user.dir") + "/"));
        multipartFile.transferTo(convFile);
        INDArray indArray = CustomerRetentionPredictionApi.generateOutput(convFile, modelFilePath);
        for (int i = 0; i < indArray.rows(); i++) {
            if (indArray.getDouble(i, 0) > indArray.getDouble(i, 1)) {
                results.add("Customer " + (i + 1) + "-> Happy Customer \n");
            } else {
                results.add("Customer " + (i + 1) + "-> Unhappy Customer \n");
            }
        }
        convFile.deleteOnExit();

        return results;
    }
}
