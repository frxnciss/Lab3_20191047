package com.example.pomodoropucp.workers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pomodoropucp.R;
import com.example.pomodoropucp.dto.Login;
import com.example.pomodoropucp.services.LoginService;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    //Verificar la conexion
    public boolean internetConnection(){
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        boolean conexion = activeNetworkInfo != null && activeNetworkInfo.isConnected();

        return conexion;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Obtener de la vista
        EditText etUsername = findViewById(R.id.etUsername);
        String username = etUsername.getText().toString().trim();

        EditText etPassword = findViewById(R.id.etPassword);
        String password = etPassword.getText().toString().trim();


        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateUser(username, password);
            }
        });
    }
    private void validateUser(String username, String password){
        LoginService loginService = RetrofitClient.getRetrofitInstance().create(LoginService.class);
        Call<Login> call = loginService.verifiedUser(username, password);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Login user = response.body();
                    Intent intent = new Intent(MainActivity.this, PomodoroActivity.class);
                    intent.putExtra("username", user.getUsername());
                    intent.putExtra("lastname", user.getLastname());
                    intent.putExtra("email", user.getEmail());
                    intent.putExtra("gender", user.getGender());
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error en la conexión. Inténtalo de nuevo.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}