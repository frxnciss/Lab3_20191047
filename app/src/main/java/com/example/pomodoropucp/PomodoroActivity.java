package com.example.pomodoropucp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

}
