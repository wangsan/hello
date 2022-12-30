package top.sailingsan.javacv.hello;

import javax.swing.JFrame;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameRecorder.Exception;

/**
 * 录制hls分片
 *
 * @author eguid
 */
public class RecordHls {

    /**
     * hls通用推流和录制
     *
     * @param input     可以是动态图片(apng,gif等等)，视频文件（mp4,flv,avi等等）,流媒体地址（http-flv,rtmp，rtsp等等）
     * @param output    hls切片
     * @param width     录制/推流的视频图像宽度
     * @param height    录制/推流的视频图像高度
     * @param frameRate 录制/推流的视频帧率
     * @author eguid
     */
    public static void pushAndRecordHls(String input, String output, Integer width, Integer height, Integer frameRate)
            throws Exception, org.bytedeco.javacv.FrameGrabber.Exception {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(input);

        grabber.start();

        if (width == null || height == null) {
            width = grabber.getImageWidth();
            height = grabber.getImageHeight();
        }

        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(output, width, height, grabber.getAudioChannels());

        recorder.setFormat("hls");

        //关于hls_wrap的说明，hls_wrap表示重复覆盖之前ts切片，这是一个过时配置，ffmpeg官方推荐使用hls_list_size 和hls_flags delete_segments代替hls_wrap
        //设置单个ts切片的时间长度（以秒为单位）。默认值为2秒
        recorder.setOption("hls_time", "2");

        //设置播放列表条目的最大数量。如果设置为0，则列表文件将包含所有片段，默认值为5
        recorder.setOption("hls_list_size", "6");

        //自动删除切片，如果切片数量大于hls_list_size的数量，则会开始自动删除之前的ts切片，只保留hls_list_size个数量的切片
        recorder.setOption("hls_flags", "delete_segments");
        //ts切片自动删除阈值，默认值为1，表示早于hls_list_size+1的切片将被删除
        recorder.setOption("hls_delete_threshold", "1");

        /*hls的切片类型：
         * 'mpegts'：以MPEG-2传输流格式输出ts切片文件，可以与所有HLS版本兼容。
         * 'fmp4':以Fragmented MP4(简称：fmp4)格式输出切片文件，类似于MPEG-DASH，fmp4文件可用于HLS version 7和更高版本。
         */
        recorder.setOption("hls_segment_type", "mpegts");

        //指定ts切片生成名称规则，按数字序号生成切片,例如'file%03d.ts'，就会生成file000.ts，file001.ts，file002.ts等切片文件
        recorder.setOption("hls_segment_filename", "eguid_blog_%03d.ts");

        if (frameRate == null) {
            frameRate = 25;
        }
        recorder.setFrameRate(frameRate);//设置帧率
        //因为我们是直播，如果需要保证最小延迟，gop最好设置成帧率相同或者帧率*2
        //一个gop表示关键帧间隔，假设25帧/秒视频，gop是50，则每隔两秒有一个关键帧，播放器必须加载到关键帧才能够开始解码播放，也就是说这个直播流最多有2秒延迟
        recorder.setGopSize(2 * frameRate);//设置gop
        recorder.setVideoQuality(1.0); //视频质量
        recorder.setVideoBitrate(10 * 1024);//码率
        //		recorder.setVideoCodecName("h264");//设置视频编码
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);//这种方式也可以
        //		recorder.setAudioCodecName("aac");//设置音频编码，这种方式设置音频编码也可以
        recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);//设置音频编码

        recorder.start();

        CanvasFrame canvas = new CanvasFrame("eguid原创文章专用视频预览");// 新建一个窗口
        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame frame = null;

        // 只抓取图像画面
        for (; (frame = grabber.grabImage()) != null; ) {
            try {
                //录制/推流
                recorder.record(frame);
                //显示画面
                canvas.showImage(frame);
            } catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
                e.printStackTrace();
            }
        }

        recorder.close();//close包含stop和release方法。录制文件必须保证最后执行stop()方法，才能保证文件头写入完整，否则文件损坏。
        grabber.close();//close包含stop和release方法
        canvas.dispose();
    }

    public static void main(String[] args) throws Exception, org.bytedeco.javacv.FrameGrabber.Exception {
        pushAndRecordHls("rtmp://127.0.0.1:1935/live/test264", "eguid.m3u8", 400, 300, 25);
    }
}
