package com.example.app1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;


public class PwdFindActivity extends AppCompatActivity {
    private EditText Username, Password, ConfirmPassword,Email;
    private DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_find);

        // 初始化数据库帮助类
        mDatabaseHelper = new DatabaseHelper(this);

        // 绑定控件
        Username = findViewById(R.id.username_pwdf);
        Email = findViewById(R.id.email_pwdf);
        ConfirmPassword = findViewById(R.id.npwd_pwdf);

    }

    //终止操作，立刻登录
    public void GoLog(View btn){
        Intent intent = new Intent(PwdFindActivity.this, LoginActivity.class);
        startActivity(intent);
    }
    //点击按钮，进行重置密码
    public void PwdChange(View btn) {
        pwdchange();
    }
    //注册方法
    private void pwdchange() {
        // 获取输入内容
        String username = this.Username.getText().toString().trim();
        String email = this.Email.getText().toString().trim();
        String confirmPassword = this.ConfirmPassword.getText().toString().trim();

        // 检查用户名和密码是否为空
        if (username.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "请输入账号或邮箱", Toast.LENGTH_SHORT).show();
            return;
        }

        // 进行数据库重置密码的检验
        if (mDatabaseHelper.PwdChange_Data(username,email,confirmPassword)) {
                // 注册成功
                Toast.makeText(this, "重置成功", Toast.LENGTH_SHORT).show();
                // 跳转到登录页面
                Intent intent = new Intent(PwdFindActivity.this, LoginActivity.class);
                startActivity(intent);
                // 关闭当前页面
            }
        else {
            // 密码不一致
            Toast.makeText(this, "输入有误，请重新检查", Toast.LENGTH_SHORT).show();
             }
        }
    }