package com.jetusesoft.warehousemanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.jetusesoft.warehousemanagement.entity.Connection;
import com.jetusesoft.warehousemanagement.entity.User;
import com.jetusesoft.warehousemanagement.util.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Callback {

    private Toolbar toolbar;
    private Button btn_login;
    private ImageView iv_connect;
    private Connection connection;

    private final static int REQUEST_CODE_INTERNET = 0x123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initData();
    }
    
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        Connection newConnection = (Connection) intent.getSerializableExtra("connection");
        if (!this.connection.equals(newConnection)) {
            this.connection = newConnection;
            onConnectionChange();
        }

    }

    private void initData() {
        Intent intent = getIntent();
        Connection connection = (Connection) intent.getSerializableExtra("connection");
        this.connection = connection;
    }

    private void onConnectionChange() {
        if (this.connection.isConnectSuccessful()) {
            iv_connect.setImageResource(R.drawable.connected);
        } else {
            iv_connect.setImageResource(R.drawable.connect);
        }

    }

    private void initView() {
        // Toolbar
        toolbar = findViewById(R.id.toolbar_login);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // 隐藏Toolbar的自带Title

        iv_connect = findViewById(R.id.iv_connect);
        btn_login = findViewById(R.id.btn_login);

        iv_connect.setOnClickListener(this);
        btn_login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_connect:
                ConnectActivity.actionStart(LoginActivity.this, Intent.FLAG_ACTIVITY_REORDER_TO_FRONT, this.connection);
                break;
            case R.id.btn_login:
                if (this.connection.isConnectSuccessful()) {

//                    checkInternetPermission();

                    HttpUtil.checkLogin("asd", "123", this);

                    User user = new User("asd", "123");
                    HomeActivity.actionStart(LoginActivity.this, Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP, user);
//                    this.finish();
                } else {
                    new SVProgressHUD(LoginActivity.this).showErrorWithStatus("登录失败, 服务器未连接");
                }
                break;
            default:
                break;
        }
    }

//    private void checkInternetPermission() {
//        if (Build.VERSION.SDK_INT < 23)
//            return;
//        int checkInternetPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
//        if (checkInternetPermission != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, REQUEST_CODE_INTERNET);
//            return;
//        } else {
//            Log.d("TAG", "checkInternetPermission: " + "has been granted.");
//        }
//
//    }

    public static void actionStart(Context context, @Nullable Integer intentFlag, @Nullable Connection connection) {
        Intent intent = new Intent(context, LoginActivity.class);
        if (intentFlag != null) {
            intent.setFlags(intentFlag);
        }
        if (connection != null) {
            intent.putExtra("connection", connection);
        }
        context.startActivity(intent);
    }

    @Override
    public void onFailure(Call call, IOException e) {
        Log.d("TAG", "onFailure: failed");
        Log.d("TAG", "onFailure: " + e.getMessage());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        Log.d("TAG", "onResponse: " + response.body().string());
    }
}
