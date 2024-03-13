package com.example.pifocuson;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailEditText, usernameEditText, passwordEditText, confirmPasswordEditText;
    private CheckBox novidadesCheckBox, termosCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailEditText = findViewById(R.id.Email_cadastro);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.senha);
        confirmPasswordEditText = findViewById(R.id.confirmar);
        novidadesCheckBox = findViewById(R.id.caixa_receber);
        termosCheckBox = findViewById(R.id.caixa_termos);

        Button cadastrarButton = findViewById(R.id.cadastrar);
        Button voltarButton = findViewById(R.id.voltar);

        cadastrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar();
            }
        });

        voltarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, FormLogin.class);
                startActivity(intent);
            }
        });
    }

    private void cadastrar() {
        String email = emailEditText.getText().toString().trim();
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        if (!isValidEmail(email)) {
            Toast.makeText(this, "E-mail incorreto", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidUsername(username)) {
            Toast.makeText(this, "Digite somente palavras no campo username", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidPassword(password)) {
            Toast.makeText(this, "Formato incorreto de senha", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "As senhas não correspondem", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!novidadesCheckBox.isChecked() || !termosCheckBox.isChecked()) {
            Toast.makeText(this, "Preenchimento obrigatório das caixas de seleção", Toast.LENGTH_SHORT).show();
            return;
        }

        // Se todas as verificações passarem, mostrar uma mensagem de sucesso
        Toast.makeText(this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show();

        // Limpar os campos após o cadastro ser realizado com sucesso
        clearFields();
    }

    private void clearFields() {
        emailEditText.setText("");
        usernameEditText.setText("");
        passwordEditText.setText("");
        confirmPasswordEditText.setText("");
        novidadesCheckBox.setChecked(false);
        termosCheckBox.setChecked(false);
    }

    private boolean isValidEmail(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private boolean isValidUsername(String username) {
        return (!TextUtils.isEmpty(username) && username.length() <= 25 && username.matches("[a-zA-Z]+"));
    }

    private boolean isValidPassword(String password) {
        return (!TextUtils.isEmpty(password) && password.length() >= 8 && password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).*$"));
    }
}
