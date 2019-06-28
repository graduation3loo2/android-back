package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.api.Api;
import com.example.myapplication.api.RetrofitClient;
import com.example.myapplication.api.Trip;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextname, editTextemail, editTextphone, editTextcity, editTextpassword, editTextconfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextname = findViewById(R.id.username);
        editTextemail = findViewById(R.id.email);
        editTextphone = findViewById(R.id.phone);
        editTextcity = findViewById(R.id.city);
        editTextpassword = findViewById(R.id.password);
        editTextconfirm = findViewById(R.id.confirmPassword);

        findViewById(R.id.signup).setOnClickListener(this);
        findViewById(R.id.viewlogin).setOnClickListener(this);
    }

    private void userSignup() {
        String name = editTextname.getText().toString().trim();
        String email = editTextemail.getText().toString().trim();
        String phone = editTextphone.getText().toString().trim();
        String city = editTextcity.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();
        String confirm_password = editTextpassword.getText().toString().trim();

        if(email.isEmpty()) {
            editTextemail.setError("Email is required");
            editTextemail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextemail.setError("enter a valid email");
            editTextemail.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            editTextpassword.setError("password Required");
            editTextpassword.requestFocus();
            return;
        }

        if(password.length() < 6) {
            editTextpassword.setError("password should at least be 6 character long");
            editTextpassword.requestFocus();
            return;
        }

        if(name.isEmpty()) {
            editTextname.setError("name Required");
            editTextname.requestFocus();
            return;
        }

        if(city.isEmpty()) {
            editTextcity.setError("city Required");
            editTextcity.requestFocus();
            return;
        }

        if(!confirm_password.equals(password)) {
            editTextconfirm.setError("password and confirm password are not equal");
            editTextconfirm.requestFocus();
            return;
        }

        if(phone.isEmpty()) {
            editTextphone.setError("phone Required");
            editTextphone.requestFocus();
            return;
        }

        if(!Patterns.PHONE.matcher(phone).matches()) {
            editTextphone.setError("enter a valid phone");
            editTextphone.requestFocus();
            return;
        }

        Call<ResponseBody> call = RetrofitClient
                .getInstance().getApi()
                .createUser(name, password, email, phone, city);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                String s = null;

                try {
                    if(response.code() == 201) {
                        s = response.body().toString();
                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
                    } else {
                        s = response.errorBody().toString();
                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if(s != null) {
                        JSONObject jsonObject = new JSONObject(s);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signup:
                userSignup();
                break;
            case R.id.viewlogin:
                break;
        }
    }
}
