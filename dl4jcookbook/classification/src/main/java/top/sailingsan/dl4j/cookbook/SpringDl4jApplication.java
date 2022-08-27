package top.sailingsan.dl4j.cookbook;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.Base64Utils;

@SpringBootApplication
public class SpringDl4jApplication {

    public static void main(String[] args) {
        try {
            System.out.println(Base64Utils.encodeToString(FileUtils.readFileToByteArray(new File(
                        "/Users/wangqingpeng/git/github/hello/dl4jcookbook/facereid/src/main/resources/b2.jpg"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        SpringApplication.run(SpringDl4jApplication.class, args);
    }

}
