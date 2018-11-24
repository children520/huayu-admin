package com.bignerdranch.android.huayu_admin;

import android.app.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends Activity {
    TextView responseText;
    EditText inputHomeUrlText;
    EditText inputBaiBaoShuUrlText;
    private static final String URL_REG="https://mp.weixin.qq.com/s/[^\\s]{22}";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bmob.initialize(this, "c16bef06a867de181070610c9681e9c0");
        setContentView(R.layout.activity_main);
        inputHomeUrlText=(EditText)findViewById(R.id.input_home_url);
        inputBaiBaoShuUrlText=(EditText)findViewById(R.id.input_baibaoshu_url);
        Button sendHomeRequest = (Button) findViewById(R.id.send_home_request);
        Button sendBaiBaoShuRequest=(Button)findViewById(R.id.send_baibaoshu_request);
        sendHomeRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.send_home_request&&!inputHomeUrlText.getText().toString().equals("")) {
                    if(UrlIsCorrect(inputHomeUrlText.getText().toString())){
                        HomeSendBombServer();
                    }else {
                        Toast.makeText(getApplicationContext(),"地址格式不符",Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(getApplicationContext(),"你还没有输入首页地址哦",Toast.LENGTH_SHORT).show();
                }
            }
        });
        sendBaiBaoShuRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.send_baibaoshu_request&&!inputBaiBaoShuUrlText.getText().toString().equals("")) {
                    if(UrlIsCorrect(inputBaiBaoShuUrlText.getText().toString())){
                        BaiBaoShuSendBombServer();
                    }else {
                        Toast.makeText(getApplicationContext(),"地址格式不符",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"你还没有输入百宝书地址哦",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void BaiBaoShuSendBombServer(){
        BaiBaoShuInformation baiBaoShuInformation=new BaiBaoShuInformation();
        baiBaoShuInformation.setBaiBaoShuUrl(inputBaiBaoShuUrlText.getText().toString());
        baiBaoShuInformation.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e!=null){
                    Toast.makeText(MainActivity.this,"发送成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"发送失败"+e.getMessage(),Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    private void HomeSendBombServer(){
        WebInformation webInformation=new WebInformation();
        webInformation.setUrl(inputHomeUrlText.getText().toString());
        webInformation.setId(UUID.randomUUID());
        webInformation.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e!=null){
                    Toast.makeText(MainActivity.this,"发送成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"发送失败"+e.getMessage(),Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    private boolean UrlIsCorrect(String url){
        Matcher matcher=Pattern.compile(URL_REG).matcher(url);
        if(matcher.lookingAt()){
            Log.d("lookat","true");
            return true;
        }else {
            Log.d("lookat","false");
            return false;
        }
    }

}
