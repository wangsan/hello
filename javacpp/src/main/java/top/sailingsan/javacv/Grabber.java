package top.sailingsan.javacv;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;

import javax.imageio.ImageIO;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.util.StopWatch;

public class Grabber {
    public static void main(String[] args) throws Exception {
        //        String url = "/Users/wangqingpeng/git/github/hello/javacpp/src/main/resources/test_human.mp4";
        String url = "rtmp://10.234.161.173:8800/live/11010800001320000243"; // H264
        //        String url = "rtmp://10.234.161.173:8800/live/11010800001320000242"; // H265
        StopWatch watch = new StopWatch();
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(url);

        //        FrameGrabber grabber = FrameGrabber.createDefault(1); // 使用摄像头，由于obs是0

        watch.start("start");
        grabber.start();
        watch.stop();

        Java2DFrameConverter converter = new Java2DFrameConverter();

        for (int i = 0; i < 5; i++) {
            long time = new Date().getTime();
            watch.start("start_" + time);
            Frame frame = grabber.grabImage();
            if (frame != null && frame.image != null) {
                System.out.println(
                        "pictType: " + frame.pictType + " type: " + frame.type + " time: " + frame.timestamp);
                BufferedImage bufferedImage = converter.convert(frame);
                ImageIO.write(bufferedImage, "jpg", new File("img_" + time + ".jpg"));
            }
            watch.stop();
        }

        System.out.println(watch.prettyPrint());
    }
}
