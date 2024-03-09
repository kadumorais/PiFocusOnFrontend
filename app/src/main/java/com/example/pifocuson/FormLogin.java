package com.example.pifocuson;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;

public class FormLogin extends AppCompatActivity {

    @SuppressLint("NewApi")
    public boolean validateLogin(String email, String password) {
        return !email.isEmpty() && !password.isEmpty();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText emailEditText = findViewById(R.id.espaco_email);
        EditText passwordEditText = findViewById(R.id.espaco_senha);

        Button loginButton = findViewById(R.id.botao_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Exemplo de como você pode imprimir os valores para testar
                Log.d("FormLogin", "Email: " + email);
                Log.d("FormLogin", "Senha: " + password);

                // Redirecionamento de tela


                // Erro no login
            }
        });

        Button registerButton = findViewById(R.id.botao_cadastro);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormLogin.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}