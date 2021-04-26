package com.example.blanjaumkm.login.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.blanjaumkm.R;
import com.example.blanjaumkm.home.Home;
import com.example.blanjaumkm.login.model.Success;
import com.example.blanjaumkm.login.service.ApiService;
import com.example.blanjaumkm.login.service.RetrofitBuilder;
import com.example.blanjaumkm.login.service.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Loginpage extends AppCompatActivity {
    private TextView txtEmail,txtPasswowrd;
    private Button btnlogin;
    private TextView tvLupapass, tvDaftar;

    private ApiService service;
    private TokenManager tokenManager;
    private Call<Success> call;

    Context mContext;
    ProgressDialog loading;
    AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        mContext = this;
        initComponents();

        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        if (tokenManager.getToken().getToken() != null) {
            startActivity(new Intent(this, Home.class));
            finish();
        }
        service = RetrofitBuilder.createService(ApiService.class);
    }

    private void initComponents (){
        txtEmail = findViewById(R.id.txtEmail);
        txtPasswowrd = findViewById(R.id.txtPassword);
        tvDaftar = findViewById(R.id.tvDaftar_);
        tvLupapass = findViewById(R.id.tvLupapass);
        btnlogin = findViewById(R.id.Btnlogin);

        tvLupapass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, Resetpassword.class));
            }
        });
        tvDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, Daftar.class));
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                requestLogin();
            }
        });
    }

    private void requestLogin() {
        String email = txtEmail.getText().toString();
        String password = txtPasswowrd.getText().toString();

        call = service.login(email,password);
        call.enqueue(new Callback<Success>() {
            @Override
            public void onResponse(Call<Success> call, Response<Success> response) {
                if (response.isSuccessful()) {
                    tokenManager.saveToken(response.body().getSuccess());
                    startActivity(new Intent(Loginpage.this, Home.class));
                    finish();
                    Toast.makeText(Loginpage.this,"Success", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Loginpage.this,"Gagal", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Success> call, Throwable t) {
                Toast.makeText(Loginpage.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

};

