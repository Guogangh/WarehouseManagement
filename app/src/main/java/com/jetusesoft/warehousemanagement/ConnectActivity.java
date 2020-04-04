package com.jetusesoft.warehousemanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.jetusesoft.warehousemanagement.util.HttpUtil;

public class ConnectActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar_connect;
    private Button btn_connect_confirm;
    private EditText et_connect_host, et_connect_port;
    private boolean isConnectionSuccessful;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        initView();
        initVariables();
    }

    private void initVariables() {
        Intent intent = getIntent();
        this.isConnectionSuccessful = intent.getBooleanExtra("isConnectSuccessful", false);
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
//            Intent intent = new Intent(ConnectActivity.this, LoginActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//            startActivity(intent);
            LoginActivity.actionStart(ConnectActivity.this, Intent.FLAG_ACTIVITY_REORDER_TO_FRONT, this.isConnectionSuccessful); // 确保 LoginActivity 的用户输入还在
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_connect_confirm:
                boolean isConnectSuccessful = HttpUtil.isConnectSuccessful(et_connect_host.getText().toString(), et_connect_port.getText().toString());
                if (isConnectSuccessful) {

                    // SVProgressHUD = ToastUtil
                    // https://github.com/saiwu-bigkoo/Android-SVProgressHUD
                    new SVProgressHUD(ConnectActivity.this).showSuccessWithStatus("连接成功");
                    this.isConnectionSuccessful = true;
                    // 进入 LoginActivity
                    LoginActivity.actionStart(ConnectActivity.this, Intent.FLAG_ACTIVITY_REORDER_TO_FRONT, true); // 确保 LoginActivity 的用户输入还在


                } else {
                    new SVProgressHUD(ConnectActivity.this).showErrorWithStatus("连接失败, 主机或端口错误");
                }
                break;
        }
    }

    private void handleConnectFail() {

    }

}
