package com.dev.softsky.finalexam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button btnListar, btnGrabar;
    BD_SQLite bd = new BD_SQLite(this, "bdUsuarios", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnListar = (Button)findViewById(R.id.btnListar);
        btnGrabar = (Button)findViewById(R.id.btnGrabar);

        btnGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread tr = new Thread(){
                    final String resul = consumirJSON();
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                grabarDatos(resul);
                            }
                        });
                    }
                };
                tr.start();

                Toast.makeText(getApplicationContext(), "Datos grabados!", Toast.LENGTH_LONG).show();
            }
        });

        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bd.abrir();
                Intent i = new Intent(getApplicationContext(), ListadoUsuarios.class);
                startActivity(i);
                bd.cerrar();
            }
        });

    }

    public String consumirJSON(){
        URL url = null;
        String linea = "";
        int respuesta = 0;
        StringBuilder resul = null;

        try{
            //cabiar IP de acuerdo al comando ipconfig(CMD)
            url = new URL("http://192.168.43.221/serviciosAndroid/listarUsuarios.php");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            respuesta = connection.getResponseCode();

            resul = new StringBuilder();

            if (respuesta == HttpURLConnection.HTTP_OK){
                InputStream in = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                while ((linea = reader.readLine()) != null){
                    resul.append(linea);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return resul.toString();

    }

    public void grabarDatos(String resul){

        bd.abrir();

        try{
            JSONArray json = new JSONArray(resul);

            for (int i = 0; i < json.length(); i++){
                bd.registrarUsuario(json.getJSONObject(i).getString("id"),
                        json.getJSONObject(i).getString("nombre"),
                        json.getJSONObject(i).getString("apellido"),
                        json.getJSONObject(i).getInt("edad"));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            bd.cerrar();
        }
    }
}
