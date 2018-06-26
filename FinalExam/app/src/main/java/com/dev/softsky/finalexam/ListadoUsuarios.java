package com.dev.softsky.finalexam;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListadoUsuarios extends AppCompatActivity {

    ListView listUsuarios;
    BD_SQLite bd = new BD_SQLite(this, "bdUsuarios", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_usuarios);

        listUsuarios = (ListView)findViewById(R.id.listUsuarios);

        Cursor cursor;
        bd.abrir();
        cursor = bd.listarUsuarios();

        final ArrayList<String> lista = new ArrayList<String>();
        String registro = "";

        do{
            registro = "\n" +
                    "Código: " + cursor.getString(0) + "\n" +
                    "Nombre: " + cursor.getString(1) + "\n" +
                    "Apellido: " + cursor.getString(2) + "\n" +
                    "Edad: " + cursor.getString(3) + "\n";
            lista.add(registro);
        }
        while (cursor.moveToNext());

        ArrayAdapter<String> adap = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        listUsuarios.setAdapter(adap);
    }
}
