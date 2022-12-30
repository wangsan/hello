package top.sailingsan.javacv.hello;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.bytedeco.ffmpeg.avutil.AVFrame;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

/**
 * Created on 2022/11/27.
 *
 * @author wangsan
 */
public class CaptureImage {

    public static void main(String[] args) throws Exception {
        // 录制器方式截图
        System.out.println("\n------  recorder image  ------\n");
        captureImage("rtsp://127.0.0.1/live/test264", 400, 300, "wangsan1.png");
        captureImage("rtsp://127.0.0.1/live/test265", 400, 300, "wangsan2.png");
        // java2d方式截图
        captureBufferedImage("rtsp://127.0.0.1/live/test264", "wangsan3.jpg", "jpg");
        captureBufferedImage("rtsp://127.0.0.1/live/test265", "wangsan4.jpg", "jpg");

        System.out.println("\n------  rtmp test  ------\n");
        captureImage("rtmp://127.0.0.1/live/test264", 400, 300, "wangsan21.png");
        captureImage("rtmp://127.0.0.1/live/test265", 400, 300, "wangsan22.png");
        captureBufferedImage("rtmp://127.0.0.1/live/test264", "wangsan23.jpg", "jpg");
        captureBufferedImage("rtmp://127.0.0.1/live/test265", "wangsan24.jpg", "jpg");

    }

    /**
     * 只截一张图
     *
     * @param input  可以是动态图片(apng,gif等等)，视频文件（mp4,flv,avi等等）,流媒体地址（http-flv,rtmp，rtsp等等）
     * @param width  图像宽度
     * @param height 图像高度
     * @param output 截图保存地址
     * @author eguid
     */
    public static void captureImage(String input, Integer width, Integer height, String output)
            throws Exception {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(input);
        grabber.start();
        if (width == null || height == null) {
            width = grabber.getImageWidth();
            height = grabber.getImageHeight();
        }
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(output, 0);
        recorder.setImageWidth(width);
        recorder.setImageHeight(height);
        recorder.start();
        Frame frame = null;
        // 只抓取视频图像
        if ((frame = grabber.grabImage()) != null) {
            //保存截图
            recorder.record(frame);
            System.out.println("capture image " + ((AVFrame) (frame.opaque)).pts());
        }
        recorder.close();//录制文件必须保证最后执行close()或者stop()方法，才能保证文件头写入完整，否则文件会损坏。
        grabber.close();
    }

    /**
     * 只截一张图
     *
     * @param input  可以是动态图片(apng,gif等等)，视频文件（mp4,flv,avi等等）,流媒体地址（http-flv,rtmp，rtsp等等）
     * @param output 截图保存路径或者地址
     * @param format 截图格式，比如：jpg,png等
     * @author eguid
     */
    public static void captureBufferedImage(String input, String output, String format) throws Exception {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(input);
        grabber.start();

        Frame frame = null;
        Java2DFrameConverter converter = new Java2DFrameConverter();
        // 只抓取图像画面
        if ((frame = grabber.grabImage()) != null) {
            BufferedImage image = converter.convert(frame);
            try {
                ImageIO.write(image, format, new FileOutputStream(output));
                System.out.println("capture image " + ((AVFrame) (frame.opaque)).pts());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        grabber.close();//close包含stop和release方法
    }

}
