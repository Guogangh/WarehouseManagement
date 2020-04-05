package com.jetusesoft.warehousemanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.jetusesoft.warehousemanagement.entity.Connection;
import com.jetusesoft.warehousemanagement.entity.User;
import com.jetusesoft.warehousemanagement.util.StorageUtil;

import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
        initData();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        User newUser = (User)intent.getSerializableExtra("user");
        if (this.user == null || !this.user.equals(newUser)) {
            this.user = newUser;
            Log.d("TAG", "onNewIntent: " + this.user.getUsername());
            Log.d("TAG", "onNewIntent: " + this.user.getPassword());
        }

    }

    private void initData() {
        this.user = StorageUtil.getUser();
        if (this.user == null) {
            LoginActivity.actionStart(HomeActivity.this, null, StorageUtil.getConnection());
        }

    }

    private void initView() {
    }

    public static void actionStart(Context context, @Nullable Integer intentFlag, @Nullable User user) {
        Intent intent = new Intent(context, HomeActivity.class);
        if (intentFlag != null) {
            intent.setFlags(intentFlag);
        }
        if (user != null) {
            intent.putExtra("user", user);
        }
        context.startActivity(intent);
    }


}
