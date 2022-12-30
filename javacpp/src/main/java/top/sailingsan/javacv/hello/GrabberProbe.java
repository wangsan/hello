package top.sailingsan.javacv.hello;

import java.util.concurrent.TimeUnit;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber;

public class GrabberProbe {
    public static void main(String[] args) throws Exception {
//        testOpen("/Users/wangqingpeng/test/face.mp4");
//        testOpen("/Users/wangqingpeng/test/video-h265.mkv");
//        testOpen("rtsp://127.0.0.1/live/test264");
//        testOpen("rtsp://127.0.0.1/live/test265");
//        testOpen("rtmp://127.0.0.1:1935/live/test264");
        testOpen("rtmp://127.0.0.1:1935/live/test265");
    }

    /**
     * 本地摄像头，以swing打开
     */
    public static void testOpen(String source) throws FrameGrabber.Exception, InterruptedException {
        // opencv support deviceNUmber
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(source);
        grabber.start();
        System.out.println(grabber.getFrameRate());
        System.out.println(grabber.getFrameNumber());
        System.out.println(grabber.getVideoBitrate());
        System.out.println(grabber.getVideoCodecName());
        System.out.println(grabber.getFormat());
        System.out.println(grabber.getImageHeight());
        System.out.println(grabber.getImageWidth());
        System.out.println(grabber.getLengthInFrames());
        System.out.println(grabber.getLengthInVideoFrames());
        System.out.println(grabber.getLengthInTime());
        TimeUnit.SECONDS.sleep(2);
        grabber.stop();
    }
}
