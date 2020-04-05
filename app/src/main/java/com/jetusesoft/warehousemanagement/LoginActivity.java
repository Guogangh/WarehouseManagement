package com.jetusesoft.warehousemanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.jetusesoft.warehousemanagement.entity.Connection;
import com.jetusesoft.warehousemanagement.entity.User;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private Button btn_login;
    private ImageView iv_connect;
    private Connection connection;

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
                    User user = new User("asd", "123");
                    HomeActivity.actionStart(LoginActivity.this, Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP, user);
                    this.finish();
                } else {
                    new SVProgressHUD(LoginActivity.this).showErrorWithStatus("登录失败, 服务器未连接");
                }
                break;
            default:
                break;
        }
    }

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

}
