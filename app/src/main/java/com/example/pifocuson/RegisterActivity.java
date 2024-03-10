package com.example.pifocuson;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Obtendo referências para os elementos da interface do usuário (UI)
        EditText emailEditText = findViewById(R.id.Email_cadastro);
        EditText usernameEditText = findViewById(R.id.username);
        EditText senhaEditText = findViewById(R.id.senha);
        EditText confirmarSenhaEditText = findViewById(R.id.confirmar);

        CheckBox receberNovidadesCheckBox = findViewById(R.id.caixa_receber);
        CheckBox termosCheckBox = findViewById(R.id.caixa_termos);

        Button cadastrarButton = findViewById(R.id.cadastrar);

        // Configurando um ouvinte de clique para o botão "Cadastrar"
        cadastrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtendo os valores inseridos nos campos de texto e caixas de seleção
                String email = emailEditText.getText().toString();
                String username = usernameEditText.getText().toString();
                String senha = senhaEditText.getText().toString();
                String confirmarSenha = confirmarSenhaEditText.getText().toString();

                boolean novidadesChecked = receberNovidadesCheckBox.isChecked();
                boolean termosChecked = termosCheckBox.isChecked();

                // Verificar se os campos estão preenchidos corretamente
                if (validateRegister(email, username, senha, confirmarSenha, novidadesChecked, termosChecked)) {
                    // Se estiverem corretos, pode prosseguir com o cadastro
                    new RegisterUserTask().execute(email, username, senha);
                } else {
                    // Caso contrário, mostre uma mensagem de erro ou trate os campos inválidos
                    Log.d("RegisterActivity", "Erro: Campos inválidos");
                    // Implemente aqui o código para lidar com campos inválidos
                }
            }
        });

        Button voltarButton = findViewById(R.id.voltar);
        voltarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar a activity de login
                Intent intent = new Intent(RegisterActivity.this, FormLogin.class);
                startActivity(intent);
                finish(); // Fechar a activity atual
            }
        });
    }

    // Método para validar os campos de cadastro
    private boolean validateRegister(String email, String username, String senha, String confirmarSenha, boolean novidadesChecked, boolean termosChecked) {
        // Verificar se todos os campos obrigatórios estão preenchidos corretamente
        if (email.isEmpty() || username.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty() || !novidadesChecked || !termosChecked) {
            // Se algum campo obrigatório estiver vazio, retorne falso
            return false;
        }

        // Verificar se o email está em um formato válido
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // Se o email não estiver em um formato válido, retorne falso
            return false;
        }

        // Verificar se as senhas coincidem
        if (!senha.equals(confirmarSenha)) {
            // Se as senhas não coincidirem, retorne falso
            return false;
        }

        // Se todas as verificações passarem, os campos estão preenchidos corretamente
        return true;
    }

    // Classe interna para executar a tarefa de registro do usuário em segundo plano
    private class RegisterUserTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            String email = params[0];
            String username = params[1];
            String senha = params[2];

            // Construir o objeto JSON com os dados do novo usuário
            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("email", email);
                jsonBody.put("username", username);
                jsonBody.put("senha", senha);
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }

            // Criar a conexão HTTP e enviar a solicitação de registro
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL("http://10.0.2.2:8080/registro");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setChunkedStreamingMode(0);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");

                // Escrever o corpo da solicitação
                BufferedOutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                out.write(jsonBody.toString().getBytes());
                out.flush();

                // Verificar o código de resposta HTTP
                int statusCode = urlConnection.getResponseCode();
                if (statusCode == HttpURLConnection.HTTP_OK) {
                    // Registro bem-sucedido
                    return true;
                } else {
                    // Erro ao registrar
                    Log.e("RegisterActivity", "Erro ao cadastrar usuário no servidor: " + statusCode);
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                // Se a solicitação de cadastro for bem-sucedida, você pode prosseguir com o lógica de redirecionamento de tela ou outras ações necessárias
                Intent intent = new Intent(RegisterActivity.this, FormLogin.class);
                startActivity(intent);
                finish(); // Fecha a atividade de cadastro
            } else {
                // Se ocorrer um erro na solicitação de cadastro, mostrar mensagem de erro ou lidar com a situação adequadamente
                Log.e("RegisterActivity", "Erro ao cadastrar usuário no servidor");
                // Implemente aqui o código para lidar com o erro na solicitação de cadastro
            }
        }
    }
}
