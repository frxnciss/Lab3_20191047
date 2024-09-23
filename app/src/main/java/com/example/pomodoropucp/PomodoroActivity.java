package com.example.pomodoropucp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.pomodoropucp.viewModels.PomodoroViewModel;

public class PomodoroActivity extends AppCompatActivity {
    private String username;
    private String name;
    private String lastname;
    private String email;
    private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);

        TextView firstlastname = findViewById(R.id.user_name);
        TextView email_text = findViewById(R.id.user_email);
        ImageView gendericon = findViewById(R.id.user_icon);
        ImageButton logoutbutton = findViewById(R.id.logout_button);

        username = savedInstanceState.getString("username");
        name = savedInstanceState.getString("name");
        lastname = savedInstanceState.getString("lastname");
        email = savedInstanceState.getString("email");
        gender = savedInstanceState.getString("gender");

        firstlastname.setText(name + " " + lastname);
        email_text.setText(email);
        if ("male".equals(gender)) {
            gendericon.setImageResource(R.drawable.ic_male);
        } else if ("female".equals(gender)) {
            gendericon.setImageResource(R.drawable.ic_female);
        }

        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PomodoroActivity.this, MainActivity.class);
                startActivity(intent);
                finish();}
            }
        );
        ViewModel contador = new ViewModelProvider(this).get(PomodoroViewModel.class);
        ImageButton startButton = findViewById(R.id.start_restart_button);
        startButton.setOnClickListener(view -> {
            startPomodoroCountdow();
            startButton.setImageResource(R.drawable.ic_restart);
        });

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Encontrando en Internet, para guardar sin importar si rota la pantalla
        outState.putString("username", username);
        outState.putString("name", name);
        outState.putString("lastname", lastname);
        outState.putString("email", email);
        outState.putString("gender", gender);
    }

    private void startPomodoroCountdow(){
        long duration = 25 * 60 * 1000; // 25 minutos en milisegundos
        startTimer(duration, this::showBreakDialog);
    }


    private void startBreakCountdown(){
        long duration = 5 * 60 * 1000; // 5 minutos en milisegundos
        startTimer(duration, this::showStartDialog);
    }

    //Apoyo de AI para definir los metodos utilizados, no fue necesario cambiar ningun valor ya que estos cumplen
    // con lo propuesto en el laboratorio
    private void startTimer(long duration, Runnable onFinish) {
        TextView timerTextView = findViewById(R.id.timer);

        CountDownTimer countDownTimer = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = (millisUntilFinished / 60000) % 60;
                long seconds = (millisUntilFinished / 1000) % 60;
                timerTextView.setText(String.format("%02d:%02d", minutes, seconds));
            }

            @Override
            public void onFinish() {
                onFinish.run();
            }
        }.start();
    }

    private void showBreakDialog() {
        new AlertDialog.Builder(this)
                .setTitle("¡Felicidades!")
                .setMessage("Empezó el tiempo de descanso.")
                .setPositiveButton("Entendido", (dialog, which) -> startBreakCountdown())
                .show();
    }

    private void showStartDialog() {
        new AlertDialog.Builder(this)
                .setTitle("¡Atención!")
                .setMessage("Terminó el tiempo de descanso. Dale al botón de reinicio para empezar otro ciclo.")
                .setPositiveButton("Entendido", (dialog, which) -> startPomodoroCountdow())
                .show();
    }
}
