package com.dev.softsky.finalexam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BD_SQLite extends SQLiteOpenHelper {
    public BD_SQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE Usuario(_ID text primary key, nombre text, apellido text, edad integer)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public void abrir(){
        this.getWritableDatabase();
    }

    public void cerrar(){
        this.close();
    }

    public void registrarUsuario(String id, String nombre, String apellido, int edad){
        ContentValues valores = new ContentValues();
        valores.put("_ID", id);
        valores.put("nombre", nombre);
        valores.put("apellido", apellido);
        valores.put("edad", edad);

        this.getWritableDatabase().insert("Usuario", null, valores);
    }

    public Cursor listarUsuarios(){
        Cursor cursor = null;
        cursor = this.getReadableDatabase().query("Usuario", new String[]{"_ID", "nombre", "apellido", "edad"}, null, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        return cursor;
    }


}
