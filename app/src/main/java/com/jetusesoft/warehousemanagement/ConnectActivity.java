package com.jetusesoft.warehousemanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.jetusesoft.warehousemanagement.entity.Connection;
import com.jetusesoft.warehousemanagement.util.HttpUtil;

public class ConnectActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar_connect;
    private Button btn_connect_confirm;
    private EditText et_connect_host, et_connect_port;
    private Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
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
        }
    }

    private void initData() {
        Intent intent = getIntent();
        this.connection = (Connection) intent.getSerializableExtra("connection");
    }

    private void initView() {
        toolbar_connect = findViewById(R.id.toolbar_connect);
        setSupportActionBar(toolbar_connect);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // 隐藏Toolbar的自带Title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 设置左上角返回箭头
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btn_connect_confirm = findViewById(R.id.btn_connect_confirm);
        btn_connect_confirm.setOnClickListener(this);

        et_connect_host = findViewById(R.id.et_connect_host);
        et_connect_port = findViewById(R.id.et_connect_port);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // 左上角返回箭头点击事件
        if (item.getItemId() == android.R.id.home) {
            LoginActivity.actionStart(ConnectActivity.this, Intent.FLAG_ACTIVITY_REORDER_TO_FRONT, this.connection); // 确保 LoginActivity 的用户输入还在
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_connect_confirm:
                boolean isConnectSuccessful = HttpUtil.checkConnect(et_connect_host.getText().toString(), et_connect_port.getText().toString());
                if (isConnectSuccessful) {
                    this.connection.setHost(et_connect_host.getText().toString());
                    this.connection.setPort(Integer.parseInt(et_connect_port.getText().toString()));
                    this.connection.setConnectSuccessful(true);
                    // SVProgressHUD = ToastUtil
                    // https://github.com/saiwu-bigkoo/Android-SVProgressHUD
                    new SVProgressHUD(ConnectActivity.this).showSuccessWithStatus("连接成功");
                    // 进入 LoginActivity
                    LoginActivity.actionStart(ConnectActivity.this, Intent.FLAG_ACTIVITY_REORDER_TO_FRONT, this.connection); // 确保 LoginActivity 的用户输入还在


                } else {
                    this.connection.setConnectSuccessful(false);
                    new SVProgressHUD(ConnectActivity.this).showErrorWithStatus("连接失败, 主机或端口错误");
                }
                break;
        }
    }

    public static void actionStart(Context context, @Nullable Integer intentFlag, @Nullable Connection connection) {
        Intent intent = new Intent(context, ConnectActivity.class);
        if (intentFlag != null) {
            intent.setFlags(intentFlag);
        }
        if (connection != null) {
            intent.putExtra("connection", connection);
        }
        context.startActivity(intent);
    }

}
