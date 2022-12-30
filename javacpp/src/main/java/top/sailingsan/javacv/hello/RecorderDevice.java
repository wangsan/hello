package top.sailingsan.javacv.hello;

import javax.swing.JFrame;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.Mat;

public class RecorderDevice {
    public static void main(String[] args) throws Exception {
        recordCamera("h264.flv", avcodec.AV_CODEC_ID_H264, 25);

        // 无法录制265的flv mp4等  可以录制ts，还得再研究
//        recordCamera("h265.ts", avcodec.AV_CODEC_ID_HEVC, 25);
    }

    /**
     * 按帧录制本机摄像头视频（边预览边录制，停止预览即停止录制）
     *
     * @param outputFile -录制的文件路径，也可以是rtsp或者rtmp等流媒体服务器发布地址
     * @param frameRate  - 视频帧率open
     * @throws Exception
     * @throws InterruptedException
     * @throws FrameRecorder.Exception
     * @author eguid
     */
    public static void recordCamera(String outputFile, int avcCodecId, double frameRate)
            throws FrameGrabber.Exception, FrameRecorder.Exception, InterruptedException {
        // 另一种方式获取摄像头，opencv抓取器方式获取摄像头请参考第一章，FrameGrabber会自己去找可以打开的摄像头的抓取器。
        FrameGrabber grabber = FrameGrabber.createDefault(0);// 本机摄像头默认0
        grabber.start();//开启抓取器

        // 获取第一帧
        OpenCVFrameConverter.ToMat converter = new OpenCVFrameConverter.ToMat();
        Mat grabbedImage = converter.convert(grabber.grab());
        int height = grabbedImage.rows();
        int width = grabbedImage.cols();

        // 设置录制器
        FrameRecorder recorder = FrameRecorder.createDefault(outputFile, width, height);
        recorder.setVideoCodec(avcCodecId); // avcodec.AV_CODEC_ID_H264，编码
        recorder.setFrameRate(frameRate);
        recorder.start();//开启录制器

        CanvasFrame frame = new CanvasFrame("camera", CanvasFrame.getDefaultGamma() / grabber.getGamma());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        while (frame.isVisible() && (grabbedImage = converter.convert(grabber.grab())) != null) {
            Frame rotatedFrame = converter.convert(grabbedImage);
            frame.showImage(rotatedFrame);
            recorder.record(rotatedFrame);
        }
        frame.dispose();//关闭窗口
        recorder.close();//关闭推流录制器，close包含release和stop操作
        grabber.close();//关闭抓取器
    }

}
