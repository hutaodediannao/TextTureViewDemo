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

    private MediaPlayer mMediaPlayer, mMediaPlayer2, mMediaPlayer3, mMediaPlayer4;
    private Surface surface, surface2, surface3, surface4;
    private TextureView textureView, textureview2, textureview3, textureview4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textureView = findViewById(R.id.textureview);
        textureview2 = findViewById(R.id.textureview2);
        textureview3 = findViewById(R.id.textureview3);
        textureview4 = findViewById(R.id.textureview4);

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
        });
        textureview3.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                surface3 = new Surface(surface);
                new PlayerVideo3().start();//开启一个线程去播放视频
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                surface = null;
                surface3 = null;
                mMediaPlayer3.stop();
                mMediaPlayer3.release();
                return true;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {

            }
        });
        textureview4.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                surface4 = new Surface(surface);
                new PlayerVideo4().start();//开启一个线程去播放视频
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                surface = null;
                surface4 = null;
                mMediaPlayer4.stop();
                mMediaPlayer4.release();
                return true;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {

            }
        });
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
                mMediaPlayer.setVolume(0, 0);
                //在线播放
                String videoUrl = "https://video.pearvideo.com/mp4/third/20200731/cont-1689202-10163759-145056-hd.mp4";
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
                mMediaPlayer2.setVolume(0, 0);
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

    private class PlayerVideo3 extends Thread {
        @Override
        public void run() {
            try {
                String videoUrl = "https://video.pearvideo.com/mp4/third/20200731/cont-1689193-12825697-143136-hd.mp4";
                mMediaPlayer3 = new MediaPlayer();
                mMediaPlayer3.setVolume(0, 0);
                mMediaPlayer3.setDataSource(videoUrl);
                mMediaPlayer3.setSurface(surface3);
                mMediaPlayer3.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mMediaPlayer3.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mMediaPlayer3.start();
                    }
                });
                mMediaPlayer3.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class PlayerVideo4 extends Thread {
        @Override
        public void run() {
            try {
                String videoUrl = "https://video.pearvideo.com/mp4/short/20200801/cont-1680683-15300358-hd.mp4";
                mMediaPlayer4 = new MediaPlayer();
                mMediaPlayer4.setVolume(0, 0);
                mMediaPlayer4.setDataSource(videoUrl);
                mMediaPlayer4.setSurface(surface4);
                mMediaPlayer4.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mMediaPlayer4.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mMediaPlayer4.start();
                    }
                });
                mMediaPlayer4.prepare();
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
