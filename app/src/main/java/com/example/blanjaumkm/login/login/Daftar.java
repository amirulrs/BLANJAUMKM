package com.example.blanjaumkm.login.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    public class Daftar extends AppCompatActivity {
    private EditText txtNama, txtEmail, txtPass, txtTelp;
    private Button btnDaftar;
    private TextView tvMasuk;
    ProgressDialog loading;
    Context mContext;
    private Call<Success> call;
    private TokenManager tokenManager;
    private ApiService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        mContext = this;
        initComponents();
        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        if (tokenManager.getToken().getToken() != null) {
            startActivity(new Intent(this, Daftar.class));
            finish();
        }
        service = RetrofitBuilder.createService(ApiService.class);
    }

    private void initComponents() {
        txtNama = findViewById(R.id.txtNama);
        txtEmail = findViewById(R.id.txtEmail);
        txtPass = findViewById(R.id.txtPassword);
        txtTelp = findViewById(R.id.txtTlp);
        btnDaftar = findViewById(R.id.btnDaftar);
        tvMasuk = findViewById(R.id.tvMasuk);

        tvMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, Loginpage.class));
            }
        });

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                requestRegisterr();
            }
        });
    }

        private void requestRegisterr() {
            String nama = txtNama.getText().toString();
            String email = txtEmail.getText().toString();
            String password = txtPass.getText().toString();
            String telp = txtTelp.getText().toString();


            call = service.daftar(nama,email,telp,password);
            call.enqueue(new Callback<Success>() {
                @Override
                public void onResponse(Call<Success> call, Response<Success> response) {
                    if (response.isSuccessful()) {
                        tokenManager.saveToken(response.body().getSuccess());
                        startActivity(new Intent(Daftar.this, Home.class));
                        finish();
                        Toast.makeText(Daftar.this,"Success", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(Daftar.this, "Error", Toast.LENGTH_LONG).show();;
                    }
                }
                @Override
                public void onFailure(Call<Success> call, Throwable t) {
                    Toast.makeText(Daftar.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }


        };
