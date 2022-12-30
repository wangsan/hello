package top.sailingsan.javacv.hello;

import org.bytedeco.ffmpeg.avutil.AVFrame;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;

public class GrabberRtmp {
    public static void main(String[] args) throws Exception {
//        testOpen("/Users/wangqingpeng/test/face.mp4");
//        testOpen("rtsp://127.0.0.1/live/test264");
//        testOpen("rtsp://127.0.0.1/live/test265");
//        testOpen("rtmp://127.0.0.1/live/test264");
//        testOpen("rtmp://127.0.0.1/live/test265");
        testOpen("http://127.0.0.1/rtp/test/hls.m3u8");
    }

    /**
     * 本地摄像头，以swing打开
     */
    public static void testOpen(String source) throws FrameGrabber.Exception {
        // opencv support deviceNUmber
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(source);
        grabber.start();
        CanvasFrame canvasFrame = new CanvasFrame("wangsan camera");
        int i = 0;
        while (canvasFrame.isVisible()) {
            Frame frame = grabber.grabFrame();
            canvasFrame.showImage(frame);
            AVFrame avframe = (AVFrame) frame.opaque;//把Frame直接强制转换为AVFrame
            if (i++ % 10 == 0) {
                System.out.println(avframe);
                System.out.println("quality: " + avframe.quality());
                System.out.println("height: " + avframe.height());
                System.out.println("width: " + avframe.width());
                System.out.println("pts: " + avframe.pts());
                System.out.println("pkt_dts: " + avframe.pkt_dts());
            }
        }

        canvasFrame.dispose();
        grabber.stop();
    }
}
