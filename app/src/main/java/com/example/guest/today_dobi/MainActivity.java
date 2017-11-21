package com.example.guest.today_dobi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.EnvironmentalReverb;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static android.os.Build.VERSION_CODES.O;
import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button b = findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String clientId = "W8r9m2AbNrNLoFeyneMx";//애플리케이션 클라이언트 아이디값";
                        String clientSecret = "nW9REFjRpu";//애플리케이션 클라이언트 시크릿값";

                        try {
                            String text = URLEncoder.encode("면접관님덜 클라스 오지구요.", "UTF-8"); // 13자
                            String apiURL = "https://openapi.naver.com/v1/voice/tts.bin";
                            URL url = new URL(apiURL);
                            HttpURLConnection con = (HttpURLConnection)url.openConnection();
                            con.setRequestMethod("POST");
                            con.setRequestProperty("X-Naver-Client-Id", clientId);
                            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
                            // post request
                            String postParams = "speaker=jinho&speed=0&text=" + text;
                            con.setDoOutput(true);
                            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                            wr.writeBytes(postParams);
                            wr.flush();
                            wr.close();
                            int responseCode = con.getResponseCode();
                            BufferedReader br;
                            if(responseCode==200) { // 정상 호출
                                InputStream is = con.getInputStream();
                                int read = 0;
                                byte[] bytes = new byte[1024];
                                // 랜덤한 이름으로 mp3 파일 생성
//
//                                File dir = new File(android.os.Environment.getExternalStorageDirectory() + "/", "Dobi");
//                                if(!dir.exists()){
//                                    dir.mkdirs();
//                                }


                                String tempname = "dobis";
//                                File f = new File(android.os.Environment.getExternalStorageDirectory() + File.separator + "Dobi/" + tempname + ".mp3");
//                                File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + tempname + ".mp3");
//                                File f = new File(getApplicationContext().getFilesDir().getPath() + "/" + tempname + ".mp3");
                                File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "test.mp3");
                                f.createNewFile();
                                OutputStream outputStream = new FileOutputStream(f);
                                while ((read =is.read(bytes)) != -1) {
                                    outputStream.write(bytes, 0, read);
                                }

                                is.close();


//                                String Path_to_file = android.os.Environment.getExternalStorageDirectory() + File.separator + "Dobi/" + tempname + ".mp3";
////                                String Path_to_file = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + tempname + ".mp3";
//////                                String Path_to_file = getApplicationContext().getFilesDir().getPath() + "/" + tempname + ".mp3";
//                                MediaPlayer audioPlay = new MediaPlayer();
//                                audioPlay.setDataSource(Path_to_file);
//                                audioPlay.prepare();
//                                audioPlay.start();


                            } else {  // 에러 발생
                                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                                String inputLine;
                                StringBuffer response = new StringBuffer();
                                while ((inputLine = br.readLine()) != null) {
                                    response.append(inputLine);
                                }
                                br.close();
                                System.out.println(response.toString());
                            }

                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                }).start();


            }
        });

        Button c_btn = findViewById(R.id.create_btn);
        c_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

//                String path = getApplicationContext().getFilesDir().getPath();
//                String path = getApplication().getFilesDir().getPath();
                String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                Log.v("external", path);

//                String Path_to_file = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "dobis" + ".mp3";
//                String Path_to_file = android.os.Environment.getExternalStorageDirectory() + File.separator + "Dobi/" + "dobis" + ".mp3";


                MediaPlayer mp = new MediaPlayer();
                try {
                    mp.setDataSource(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)) + "/test.mp3");
                    mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mp.prepare();
                    mp.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

//                mp.release();

            }
        });

    }



}
