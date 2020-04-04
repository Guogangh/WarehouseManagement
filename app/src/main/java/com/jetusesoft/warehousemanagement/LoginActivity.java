package com.jetusesoft.warehousemanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private Button btn_login;
    private ImageView iv_connect;
    private boolean isConnectSuccessful;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void checkIsConnectSuccessful() {
        Intent intent = getIntent();
        this.isConnectSuccessful = intent.getBooleanExtra("isConnectSuccessful", false);
        if (this.isConnectSuccessful) {
            iv_connect.setImageResource(R.drawable.connected);
//            iv_connect.setBackgroundResource(R.drawable.connected);
        }

        else {
            iv_connect.setImageResource(R.drawable.connect);
//            iv_connect.setBackgroundResource(R.drawable.connected);
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

        checkIsConnectSuccessful();

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
                Intent intent = new Intent(LoginActivity.this, ConnectActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); // 确保 ConnectActivity 的用户输入还在
                startActivity(intent);
                break;
            case R.id.btn_login:

                break;
            default:
                break;
        }
    }

    public static void actionStart(Context context, @Nullable Integer intentFlag, @Nullable Boolean isConnectSuccessful) {
        Intent intent = new Intent(context, LoginActivity.class);
        if (intentFlag != null) {
            intent.setFlags(intentFlag);
        }
        if (isConnectSuccessful != null) {
            intent.putExtra("isConnectSuccessful", isConnectSuccessful);
        }

        context.startActivity(intent);
    }

}
