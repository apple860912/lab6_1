package com.example.user.test;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class test extends AppCompatActivity {

    protected Button start;
    protected SeekBar t_seekBar,r_seekBar;
    int r_count = 0, t_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        start = (Button) findViewById(R.id.start);
        t_seekBar = (SeekBar)findViewById(R.id.t_seekBar);
        r_seekBar = (SeekBar)findViewById(R.id.r_seekBar);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(test.this,"start",Toast.LENGTH_LONG).show();
                runThead();
                runAsyncTask();
            }
        });
    }
    private  void runThead(){
        r_count = 0;
        new Thread(){
            public void run(){
                do{
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    r_count+=(int)(Math.random()*6);
                    Message msg = new Message();
                    msg.what = 1;
                    mHandler.sendMessage(msg);
                } while(r_count<=100);
            }
    }.start();
}
    private Handler  mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 1:
                    r_seekBar.setProgress(r_count);
                    break;
            }

            if(r_count>=100)
                if(t_count<100)
                    Toast.makeText(test.this,"rabbit win",Toast.LENGTH_SHORT).show();
        }
    };
    private  void runAsyncTask(){
        new AsyncTask<Void,Integer,Boolean>(){
            @Override
            protected Boolean doInBackground(Void... voids) {
                do{
                    try{
                        Thread.sleep(100);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    t_count+=(int)(Math.random()*6);
                    publishProgress(t_count);
                }while(t_count<=100);
                return true;
            }

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                t_count=0;
            }

            @Override
            protected void onProgressUpdate(Integer...values){
                super.onProgressUpdate(values);
                t_seekBar.setProgress(values[0]);
            }
            @Override
            protected void onPostExecute(Boolean status){
                if(r_count<100)
                    Toast.makeText(test.this,"turtle win",Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }
}

