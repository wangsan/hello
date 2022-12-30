package top.sailingsan.javacv.hello;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameGrabber;

public class GrabberDevice {
    public static void main(String[] args) throws Exception {
        testOpenDevice();
    }

    /**
     * 本地摄像头，以swing打开
     */
    public static void testOpenDevice() throws FrameGrabber.Exception {
        // opencv support deviceNUmber
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        grabber.start();

        CanvasFrame canvasFrame = new CanvasFrame("wangsan camera");
        while (canvasFrame.isVisible()) {
            Frame frame = grabber.grabFrame();
            canvasFrame.showImage(frame);
        }

        canvasFrame.dispose();
        grabber.stop();
    }
}
