package top.sailingsan.javacv;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.bytedeco.ffmpeg.avcodec.AVPacket;
import org.bytedeco.ffmpeg.avutil.AVFrame;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

/**
 * see https://www.javagame.top/article/21091317614503356035/detail.html
 */
public class QuickStart {
    public static void main(String[] args) throws Exception {
        String url = "rtmp://media3.scctv.net/live/scctv_800";
        //解码流程
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(url); //创建一个拉流器，url可以是音视频文件后者流媒体地址
        grabber.start();//初始化，网络不好可能会阻塞，会读取一些音视频分析得到音视频格式编码信息等
        for (int i = 0; i < 5; i++) {
            //该操作完成了解协议，解封装/解复用和解码操作，默认得到的图像是yuv像素，音频则是pcm采样。
            Frame frame = grabber.grab();
            Java2DFrameConverter converter = new Java2DFrameConverter();
            BufferedImage bi = converter.getBufferedImage(frame);

            AVFrame avframe = (AVFrame) frame.opaque;//把Frame直接强制转换为AVFrame
            long lastPts = avframe.pts();
            System.out.println("显示时间：" + lastPts);
            File output = new File("fileName" + lastPts);
            ImageIO.write(bi, "jpg", output);
        }

        //        for(;;){
        //            //该操作完成了解协议，解封装/解复用操作，如果视频源是h264/pcma默认得到的未解码的是一帧h264编码数据（i帧/P帧/B帧），音频则是未解码的pcma编码数据。
        //            AVPacket pkt=grabber.grabPacket();
        //        }
    }

    public static void trans() throws Exception {
        //推流转码流程
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber("xx.mp4");//创建一个拉流器，url是视频文件，假设xx.mp4是h265/aac编码的视频文件
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder("xx.flv", 0);//创建一个推流器，url是视频文件，假设我们需要转成xx
        // .flv的格式是h264/aac编码的视频文件
        grabber.start();//初始化，网络不好可能会阻塞，会读取一些音视频分析得到音视频格式编码信息等
        recorder.start();//初始化，会读取一些推流地址信息
        recorder.setFormat("flv");//设置视频封装格式是flv
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); // 设置h264编码
        recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);//设置aac编码

        for (; ; ) {
            //该操作完成了解协议，解封装/解复用和解码操作，默认得到的图像是yuv像素，音频则是pcm采样。
            Frame frame = grabber.grab();
            //该操作完成了编码、封装/复用、推流/录制的操作，录制的音视频已经是h264/aac编码的flv文件了
            recorder.record(frame);
        }
    }

    public static void reuse() throws Exception {
        //推流复用/封装流程
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber("xx.mp4");//创建一个拉流器，url可以是音视频文件
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder("xx.mp4", 0);//创建一个推流器，url可以是音视频文件后者流媒体地址
        grabber.start();//初始化，网络不好可能会阻塞，会读取一些音视频分析得到音视频格式编码信息等
        recorder.start(grabber.getFormatContext());//初始化，由于是复用/封装流程，推流器需要拉流器的格式上下文信息才能初始化

        for (; ; ) {
            //该操作完成了解协议，解封装/解复用操作，如果视频源是h264/pcma默认得到的未解码的是一帧h264编码数据（i帧/P帧/B帧），音频则是未解码的pcma编码数据。
            AVPacket pkt = grabber.grabPacket();
            //该操作完成了复用/封装推流，并不涉及编解码
            recorder.recordPacket(pkt);
        }
    }

}
