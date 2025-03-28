package cn.chahuyun.teabot.util;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Description : 通过调用TTS服务，生成语音文件
 * @Author :Obi
 * @Date: 2025/3/27 15:27
 */
public class VoiceUtil {

    /**
     * 请求Spark-TTS
     */
    public static void SparkTTS(){
        try {
            // 创建 URL 对象
            URL url = new URL("http://192.168.0.113:7860/voice_clone");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            // 设置请求方法
            httpURLConnection.setRequestMethod("GET");

            // 获取响应码
            int responseCode = httpURLConnection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // 读取响应内容
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // 打印响应内容
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取一段WAV语音的秒数
     */
    public static double WAVDuration(String address){
        File file = new File("address");
        double duration = 0;
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            AudioFormat format = audioInputStream.getFormat();
            long frameLength = audioInputStream.getFrameLength();
            duration = (double) frameLength / format.getFrameRate();
            System.out.printf("Duration: %.2f seconds (%d frames, %d frame rate)",
                    duration, frameLength, (int) format.getFrameRate());
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        return duration;
    }

    /**
     *
     */

}