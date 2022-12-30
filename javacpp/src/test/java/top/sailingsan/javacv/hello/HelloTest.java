package top.sailingsan.javacv.hello;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.junit.jupiter.api.Test;

/**
 * Created on 2022/11/26.
 *
 * @author wangsan
 */
class HelloTest {
    @Test
    public void testOpenDevice2() throws FrameGrabber.Exception {
        // opencv support deviceNUmber
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        grabber.start();

        CanvasFrame canvasFrame = new CanvasFrame("wangsan camera");
        while (canvasFrame.isVisible()) {
            canvasFrame.showImage(grabber.grab());
        }

        canvasFrame.dispose();
        grabber.stop();
    }
}
