package com.example.guest.today_dobi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button b = findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                MediaPlayer mp = new MediaPlayer();
                try {
                    mp.setDataSource("/data/user/0/com.example.guest.today_dobi/files/test.mp3");
                    mp.prepare();
                    mp.start();
                } catch (IOException e) {
                    Log.v("알림 : ", "go to catch");
                    e.printStackTrace();
                }
                mp.stop();


//                Context c = v.getContext();


//                MediaPlayer m = MediaPlayer.create(this, Path);
//
//                m.start();
//
//                m.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
//                    @Override
//                    public void onCompletion(MediaPlayer mp){
//                        mp.stop();
//                        mp.release();
//                    }
//                });
            }
        });

        Button c_btn = findViewById(R.id.create_btn);
        c_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                new Thread(){

                    @Override
                    public void run(){
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
                                String filePath = getApplication().getFilesDir().getPath().toString() + "/test.mp3";

                                String tempname = "test";
//                                File f = new File(filePath);
                                java.io.File f = new java.io.File(filePath);
                                Log.v("주소 : ", filePath);
                                f.createNewFile();
                                OutputStream outputStream = new FileOutputStream(f);
                                while ((read =is.read(bytes)) != -1) {
                                    outputStream.write(bytes, 0, read);
                                }

                                is.close();




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
//                            try {
//                                FileInputStream fis = new FileInputStream("./UU.mp3");
//                                Player mp3 = new Player(fis);
//                                mp3.play();
//                            }catch(Exception e) {
//                                System.out.println(e);
//                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }

                    }

                }.start();
            }
        });

    }



}
