package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.api.RetrofitClient;
import com.example.myapplication.models.LoginResponse;
import com.example.myapplication.storage.SharedPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextEmail, editTextPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.e_mail);
        editTextPassword = findViewById(R.id.pass);

        findViewById(R.id.signup).setOnClickListener(this);
        findViewById(R.id.viewlogin).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(SharedPreferenceManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        }
    }

    private void userLogin() {
        String e_mail = editTextEmail.getText().toString().trim();
        String pass = editTextPassword.getText().toString().trim();

        if(e_mail.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(e_mail).matches()){
            editTextEmail.setError("enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if(pass.isEmpty()) {
            editTextPassword.setError("password Required");
            editTextPassword.requestFocus();
            return;
        }

        if(pass.length() < 6) {
            editTextPassword.setError("password should at least be 6 character long");
            editTextPassword.requestFocus();
            return;
        }

        Call<LoginResponse> call = RetrofitClient.getInstance()
                .getApi().UserLogin(e_mail, pass);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse =response.body();

                if(!loginResponse.isError()) {

                    SharedPreferenceManager.getInstance(Login.this)
                            .saveUser(loginResponse.getUser());

                    Intent intent = new Intent(Login.this, ProfileActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    startActivity(intent);

                } else {
                    Toast.makeText(Login.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signup:
                userLogin();
                break;
            case R.id.viewsignup:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}
