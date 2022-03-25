package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
private EditText entrada;
private Button enviar;
private TextView saida;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        entrada = (EditText) findViewById(R.id.entrada);
        enviar = (Button) findViewById(R.id.enviar);
        saida = (TextView) findViewById(R.id.saida);
        enviar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.enviar){
            String url = "https://swapi.dev/api/people/"+entrada.getText().toString().trim();
            tarefa t = new tarefa();
            t.execute(url);
        }
    }
    class tarefa extends AsyncTask<String,Void,String>{
        @Override
        protected void onPostExecute(String p){
            super.onPostExecute(p);
            JSONObject j = null;
            try {
                j = new JSONObject(p.toString());
                saida.setText(j.getString("name"));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        @Override
        protected String doInBackground(String... strings) {
            StringBuffer str = new StringBuffer();
            try {
                URL url2 = new URL(strings[0]);
                HttpsURLConnection conexao = (HttpsURLConnection) url2.openConnection();
                InputStream input = conexao.getInputStream();
                InputStreamReader reader = new InputStreamReader(input);
                BufferedReader leitura = new BufferedReader(reader);
                String s;
                if ((s=leitura.readLine())!=null){
                    str.append(s);
                }
                System.out.println(str.toString());
            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            return str.toString();
        }
    }
}