package com.example.vmusic;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class PlaySong extends AppCompatActivity {
   ImageView back;
   TextView firstDuration;
   MediaPlayer mediaPlayer;
   ImageView next;
   ImageView play;
   int position;
   ImageView previous;
   TextView secondDuration;
   SeekBar seekBar;
   ArrayList songs;
   boolean stopThread;
   String textContent;
   TextView textView;
   Thread updateSeek;

   // $FF: synthetic method
   public void lambda$onCreate$0$com_example_vmusic_PlaySong/* $FF was: lambda$onCreate$0$com-example-vmusic-PlaySong*/(View var1) {
      if (this.mediaPlayer.isPlaying()) {
         this.play.setImageResource(2131230870);
         this.mediaPlayer.pause();
      } else {
         this.play.setImageResource(2131230869);
         this.mediaPlayer.start();
      }
   }

   // $FF: synthetic method
   public void lambda$onCreate$1$com_example_vmusic_PlaySong/* $FF was: lambda$onCreate$1$com-example-vmusic-PlaySong*/(MediaPlayer var1, View var2) {
      var1.stop();
      var1.release();
      if (this.position != 0) {
         --this.position;
      } else {
         this.position = this.songs.size() - 1;
      }

      Uri var3 = Uri.parse(((File)this.songs.get(this.position)).toString());
      this.mediaPlayer = MediaPlayer.create(this.getApplicationContext(), var3);
      this.mediaPlayer.start();
      this.play.setImageResource(2131230869);
      this.seekBar.setMax(this.mediaPlayer.getDuration());
      this.textContent = ((File)this.songs.get(this.position)).getName().toString();
      this.textView.setText(this.textContent);
      int var4 = this.mediaPlayer.getDuration();
      Object[] var5 = new Object[2];
      TimeUnit var6 = TimeUnit.MILLISECONDS;
      long var7 = (long)var4;
      var5[0] = var6.toMinutes(var7);
      var5[1] = TimeUnit.MILLISECONDS.toSeconds(var7) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(var7));
      String var9 = String.format("%02d:%02d", var5);
      this.secondDuration.setText(var9);
   }

   // $FF: synthetic method
   public void lambda$onCreate$2$com_example_vmusic_PlaySong/* $FF was: lambda$onCreate$2$com-example-vmusic-PlaySong*/(View var1) {
      this.mediaPlayer.stop();
      this.mediaPlayer.release();
      if (this.position != this.songs.size() - 1) {
         ++this.position;
      } else {
         this.position = 0;
      }

      Uri var2 = Uri.parse(((File)this.songs.get(this.position)).toString());
      this.mediaPlayer = MediaPlayer.create(this.getApplicationContext(), var2);
      this.mediaPlayer.start();
      this.play.setImageResource(2131230869);
      this.seekBar.setMax(this.mediaPlayer.getDuration());
      this.textContent = ((File)this.songs.get(this.position)).getName().toString();
      this.textView.setText(this.textContent);
      int var3 = this.mediaPlayer.getDuration();
      Object[] var4 = new Object[2];
      TimeUnit var5 = TimeUnit.MILLISECONDS;
      long var6 = (long)var3;
      var4[0] = var5.toMinutes(var6);
      var4[1] = TimeUnit.MILLISECONDS.toSeconds(var6) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(var6));
      String var8 = String.format("%02d:%02d", var4);
      this.secondDuration.setText(var8);
   }

   // $FF: synthetic method
   public void lambda$onCreate$3$com_example_vmusic_PlaySong/* $FF was: lambda$onCreate$3$com-example-vmusic-PlaySong*/(View var1) {
      this.finish();
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2131558430);
      this.textView = (TextView)this.findViewById(2131362225);
      this.back = (ImageView)this.findViewById(2131361890);
      this.firstDuration = (TextView)this.findViewById(2131362275);
      this.secondDuration = (TextView)this.findViewById(2131362274);
      this.play = (ImageView)this.findViewById(2131362118);
      this.previous = (ImageView)this.findViewById(2131362124);
      this.next = (ImageView)this.findViewById(2131362095);
      this.seekBar = (SeekBar)this.findViewById(2131362157);
      Intent var2 = this.getIntent();
      this.songs = var2.getExtras().getParcelableArrayList("songList");
      String var3 = var2.getStringExtra("CurrentSong");
      this.textContent = var3;
      this.textView.setText(var3);
      this.textView.setSelected(true);
      int var4 = var2.getIntExtra("Position", 0);
      this.position = var4;
      MediaPlayer var5 = MediaPlayer.create(this, Uri.parse(((File)this.songs.get(var4)).toString()));
      this.mediaPlayer = var5;
      var5.start();
      this.seekBar.setMax(this.mediaPlayer.getDuration());
      this.stopThread = false;
      this.seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
         public void onProgressChanged(SeekBar var1, int var2, boolean var3) {
            StringBuilder var4 = new StringBuilder();
            var4.append(PlaySong.this.mediaPlayer.getCurrentPosition());
            var4.append(" and ");
            var4.append(PlaySong.this.mediaPlayer.getDuration());
            Log.d("duration", var4.toString());
            if (PlaySong.this.mediaPlayer.getCurrentPosition() >= -200 + PlaySong.this.mediaPlayer.getDuration()) {
               PlaySong.this.next.callOnClick();
            }

         }

         public void onStartTrackingTouch(SeekBar var1) {
         }

         public void onStopTrackingTouch(SeekBar var1) {
            PlaySong.this.mediaPlayer.seekTo(var1.getProgress());
         }
      });
      this.updateSeek = new Thread() {
         // $FF: synthetic method
         public void lambda$run$0$com_example_vmusic_PlaySong$2/* $FF was: lambda$run$0$com-example-vmusic-PlaySong$2*/(String var1) {
            PlaySong.this.firstDuration.setText(var1);
         }

         public void run() {
            while(!PlaySong.this.stopThread) {
               if (PlaySong.this.mediaPlayer != null) {
                  int var1 = PlaySong.this.mediaPlayer.getCurrentPosition();
                  Object[] var2 = new Object[2];
                  TimeUnit var3 = TimeUnit.MILLISECONDS;
                  long var4 = (long)var1;

                  try {
                     var2[0] = var3.toMinutes(var4);
                     var2[1] = TimeUnit.MILLISECONDS.toSeconds(var4) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(var4));
                     String var8 = String.format("%02d:%02d", var2);
                     PlaySong.this.runOnUiThread(new PlaySong$2$$ExternalSyntheticLambda0(this, var8));
                     int var9 = PlaySong.this.mediaPlayer.getCurrentPosition();
                     PlaySong.this.seekBar.setProgress(var9);
                     sleep(800L);
                     Log.d("threadCode", "Updating Success");
                  } catch (Exception var10) {
                     var10.printStackTrace();
                     Log.d("threadCode", "Updating Failed");
                  }
               }
            }

         }
      };
      this.play.setOnClickListener(new PlaySong$$ExternalSyntheticLambda0(this));
      this.previous.setOnClickListener(new PlaySong$$ExternalSyntheticLambda3(this, var5));
      this.next.setOnClickListener(new PlaySong$$ExternalSyntheticLambda1(this));
      int var6 = this.mediaPlayer.getDuration();
      Object[] var7 = new Object[2];
      TimeUnit var8 = TimeUnit.MILLISECONDS;
      long var9 = (long)var6;
      var7[0] = var8.toMinutes(var9);
      var7[1] = TimeUnit.MILLISECONDS.toSeconds(var9) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(var9));
      String var11 = String.format("%02d:%02d", var7);
      this.secondDuration.setText(var11);
      this.updateSeek.start();
      this.back.setOnClickListener(new PlaySong$$ExternalSyntheticLambda2(this));
   }

   protected void onDestroy() {
      super.onDestroy();
      this.stopThread = true;
      this.mediaPlayer.stop();
      this.mediaPlayer.reset();
      this.mediaPlayer.release();
   }
}
