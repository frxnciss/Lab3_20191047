package com.example.pomodoropucp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pomodoropucp.dto.Users;
import com.example.pomodoropucp.services.LoginService;

import java.util.Timer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    //Verificar la conexion
    public boolean conection(){
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        boolean conexion = activeNetworkInfo != null && activeNetworkInfo.isConnected();

        return conexion;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String username = ((EditText) findViewById(R.id.etUsername)).getText().toString();
        String password = ((EditText) findViewById(R.id.etPassword)).getText().toString();
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        LoginService loginService = new Retrofit.Builder().baseUrl("https://dummyjson.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(LoginService.class);
        if(conection()){
            loginService.verifiedUser(username, password).enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    if (response.isSuccessful()) {
                        Users user = response.body();
                        Intent intent = new Intent(MainActivity.this, PomodoroActivity.class);
                        Users api = response.body();
                        user.setUsername(api.getUsername());
                        user.setLastname(api.getName());
                        user.setLastname(api.getLastname());
                        user.setEmail(api.getEmail());
                        user.setGender(api.getGender());
                        intent.putExtra("username",user.getUsername());
                        intent.putExtra("name",user.getName());
                        intent.putExtra("lastname",user.getLastname());
                        intent.putExtra("email",user.getEmail());
                        intent.putExtra("gender",user.getGender());
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Datos incorrectos.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {

                }
            });
        }
    }
}