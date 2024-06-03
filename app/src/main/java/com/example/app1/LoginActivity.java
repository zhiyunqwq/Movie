package com.example.app1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 初始化数据库帮助类
        mDatabaseHelper = new DatabaseHelper(this);

        // 绑定控件
        etUsername = findViewById(R.id.username_login);
        etPassword = findViewById(R.id.password_login);

    }
    //前往注册
    public void GoReg(View btn){
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
    //前往找回密码
        public void GoPwd(View btn){
            Intent intent = new Intent(LoginActivity.this,PwdFindActivity.class);
            startActivity(intent);
        }

    //登录方法
    public void Log(View btn) {

        loginUser();
    }

    private void loginUser() {
        // 获取输入内容
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // 检查用户名和密码是否为空
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "请输入账号或密码", Toast.LENGTH_SHORT).show();
            return;
        }

        // 检查用户名和密码是否匹配
        boolean isMatch = mDatabaseHelper.checkUser(username, password);

        if (isMatch) {
            User.setCurrentUsername(username);
            // 登录成功
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
            // 跳转到MainActivity
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish(); // 结束当前Activity
        } else {
            // 登录失败
            Toast.makeText(this, "账号或密码错误", Toast.LENGTH_SHORT).show();
        }
    }
}