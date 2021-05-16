package com.example.testhttpdownload;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.androidnetworking.interfaces.DownloadProgressListener;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Timestamp;

public class MainActivity extends AppCompatActivity {

    /// https://github.com/amitshekhariitbhu/Fast-Android-Networking

    private Button btn_downFile;
    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndroidNetworking.initialize(getApplicationContext());

        btn_downFile = (Button) this.findViewById(R.id.button);
        btn_downFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String fileName = Runtime.getRuntime() + ".jpg";
                Long datetime = System.currentTimeMillis();
                Timestamp timestamp = new Timestamp(datetime);
//                String fileName = timestamp.getDate() + ".jpg";
//                String fileName = datetime + ".jpg";
                String fileName = "DamagedHelmet.glb";

                /// @note SDCard
//                String dirPath = "/storage/emulated/0/Download/";
//                String dirPath = Environment.getExternalStorageDirectory() + "/Download/";
                String dirPath = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator + "models" ;
                /// @note APP 当前目录
//                String dirPath = MainActivity.this.getFilesDir().getAbsolutePath();
                File file = new File(dirPath +  File.separator + fileName);
                if (!file.exists() || file.length() == 0) {
                    Log.e(TAG, "File not exist !");

//                    String url = "https://jp.netcdn.space/digital/video/ssis00037/ssis00037pl.jpg";
//                    String url = "https://img-blog.csdnimg.cn/20210307162316694.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3BhbmRhMTIzNGxlZQ==,size_16,color_FFFFFF,t_70";
                    String url = "https://github.com/KhronosGroup/glTF-Sample-Models/blob/master/2.0/DamagedHelmet/glTF-Binary/DamagedHelmet.glb";
                    AndroidNetworking.download(url, dirPath, fileName)
                            .setTag("downloadTest")
                            .setPriority(Priority.MEDIUM)
                            .build()
//                        .setAnalyticsListener(new AnalyticsListener() {
//                            @Override
//                            public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
//                                Log.e(TAG, " timeTakenInMillis : " + timeTakenInMillis);
//                                Log.e(TAG, " bytesSent : " + bytesSent);
//                                Log.e(TAG, " bytesReceived : " + bytesReceived);
//                                Log.e(TAG, " isFromCache : " + isFromCache);
//                            }
//                        })
                            .setDownloadProgressListener(new DownloadProgressListener() {
                                @Override
                                public void onProgress(long bytesDownloaded, long totalBytes) {
                                    // do anything with progress
                                    Log.e(TAG, "onProgress:" + bytesDownloaded + " bytes downloaded (" + totalBytes + " bytes)");
                                }
                            })
                            .startDownload(new DownloadListener() {
                                @Override
                                public void onDownloadComplete() {
                                    // do anything after completion
                                    Log.e(TAG, "onDownloadComplete:" + file.getAbsolutePath());

                                    FileInputStream input = null;
                                    try {
                                        input = new FileInputStream(file);//得到读取流
                                        byte[] buf = new byte[(int) file.length()];
                                        input.read(buf);
                                        Log.e(TAG, "file.bytes: " + buf.length);
                                        input.close();
                                    } catch (Exception e) {
//                                        Log.e(TAG, "catch");
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onError(ANError error) {
                                    // handle error
                                    Log.e(TAG, "onError: " + error.getErrorDetail() + ", Message: " + error.getMessage());
                                }
                            });
                }
                else {
//                    https://www.cnblogs.com/jenson138/p/4300734.html
                    Log.e(TAG, "File exist !");
                    FileInputStream input = null;
                    try {
                        input = new FileInputStream(file);//得到读取流
                        byte[] buf = new byte[(int) file.length()];
                        input.read(buf);
                        Log.e(TAG, "file.bytes: " + buf.length);
                        input.close();
                    } catch (Exception e) {
//                        Log.e(TAG, "catch");
                        e.printStackTrace();
                    }
                }

            }
        });

    }
}