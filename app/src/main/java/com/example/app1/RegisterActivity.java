package com.example.app1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private EditText Username, Password, ConfirmPassword,Email;
    private DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 初始化数据库帮助类
        mDatabaseHelper = new DatabaseHelper(this);

        // 绑定控件
        Username = findViewById(R.id.username_edittext);
        Password = findViewById(R.id.password_edittext);
        ConfirmPassword = findViewById(R.id.repeat);
        Email = findViewById(R.id.email);

    }

    //已有账号，立刻登录
    public void GoLog(View btn){
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }
    //点击按钮，进行注册
    public void Register(View btn) {
        registerUser();
    }
    //注册方法
    private void registerUser() {
        // 获取输入内容
        String username = this.Username.getText().toString().trim();
        String password = this.Password.getText().toString().trim();
        String confirmPassword = this.ConfirmPassword.getText().toString().trim();
        String email = this.Email.getText().toString().trim();
    //检查用户是否存在
    if (!mDatabaseHelper.isUsernameExists(username)){
    // 检查用户名和密码是否为空
    if (username.isEmpty() || password.isEmpty()) {
        Toast.makeText(this, "请输入账号或密码", Toast.LENGTH_SHORT).show();
        return;
    }


    // 检查两次输入的密码是否一致
    if (password.equals(confirmPassword)) {
        // 插入数据库
        boolean isInserted = mDatabaseHelper.insertData(username, password,email);

        if (isInserted) {
            // 注册成功
            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
            // 跳转到登录页面
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            // 关闭当前页面
            finish();
        } else {
            // 注册失败
            Toast.makeText(this, "注册失败", Toast.LENGTH_SHORT).show();
        }
    } else {
        // 密码不一致
        Toast.makeText(this, "两次密码不同", Toast.LENGTH_SHORT).show();
    }
}else Toast.makeText(this, "此用户已存在", Toast.LENGTH_SHORT).show();
}

}