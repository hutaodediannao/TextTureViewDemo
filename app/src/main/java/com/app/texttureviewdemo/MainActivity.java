package com.app.texttureviewdemo;

import android.content.res.AssetManager;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener {

    private MediaPlayer mMediaPlayer, mMediaPlayer2;
    private Surface surface, surface2;
    private ImageView videoImage;
    private TextureView textureView, textureview2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textureView = findViewById(R.id.textureview);
        textureview2 = findViewById(R.id.textureview2);
//        videoImage = findViewById(R.id.video_image);
        textureView.setSurfaceTextureListener(this);//设置监听函数  重写4个方法
        textureview2.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                surface2 = new Surface(surface);
                new PlayerVideo2().start();//开启一个线程去播放视频
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                surface = null;
                surface2 = null;
                mMediaPlayer2.stop();
                mMediaPlayer2.release();
                return true;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {

            }
        });//设置监听函数  重写4个方法
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        System.out.println("onSurfaceTextureAvailable onSurfaceTextureAvailable");
        surface = new Surface(surfaceTexture);
        new PlayerVideo().start();//开启一个线程去播放视频
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        System.out.println("onSurfaceTextureSizeChanged onSurfaceTextureSizeChanged");
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        System.out.println("onSurfaceTextureDestroyed onSurfaceTextureDestroyed");
        surfaceTexture = null;
        surface = null;
        mMediaPlayer.stop();
        mMediaPlayer.release();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
//		System.out.println("onSurfaceTextureUpdated onSurfaceTextureUpdated");
    }

    private class PlayerVideo extends Thread {
        @Override
        public void run() {
            try {
                File file = new File(Environment.getExternalStorageDirectory() + "/test/a.mp4");
                if (!file.exists()) {//文件不存在
                    copyFile();
                }
                mMediaPlayer = new MediaPlayer();
                //在线播放
                String videoUrl = "https://video.pearvideo.com/mp4/adshort/20200731/cont-1689163-15298530_adpkg-ad_hd.mp4";
                mMediaPlayer.setDataSource(videoUrl);

//                mMediaPlayer.setDataSource(file.getAbsolutePath());
                mMediaPlayer.setSurface(surface);
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
//                        videoImage.setVisibility(View.GONE);
                        mMediaPlayer.start();
                    }
                });
                mMediaPlayer.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class PlayerVideo2 extends Thread {
        @Override
        public void run() {
            try {
                File file = new File(Environment.getExternalStorageDirectory() + "/test/b.mp4");
                if (!file.exists()) {//文件不存在
                    copyFile();
                }
                mMediaPlayer2 = new MediaPlayer();
                mMediaPlayer2.setDataSource(file.getAbsolutePath());
                mMediaPlayer2.setSurface(surface2);
                mMediaPlayer2.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mMediaPlayer2.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mMediaPlayer2.start();
                    }
                });
                mMediaPlayer2.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 如果sdcard没有文件就复制过去
     */
    private void copyFile() {
        AssetManager assetManager = this.getAssets();
        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open("a.mp4");
            String newFileName = Environment.getExternalStorageDirectory() + "/a.mp4";
            out = new FileOutputStream(newFileName);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }


}
