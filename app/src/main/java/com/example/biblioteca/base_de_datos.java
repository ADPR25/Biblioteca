package com.example.biblioteca;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONObject;

public class base_de_datos  extends SQLiteOpenHelper {


    public base_de_datos(@Nullable Context context, @Nullable String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String /* strSQL = "CREATE TABLE IF NOT EXISTS estado_prestamo (" +
                "id_estado_prestamo INTEGER PRIMARY KEY," +
                "estado VARCHAR(20))";
        sqLiteDatabase.execSQL(strSQL);

        strSQL = "CREATE TABLE IF NOT EXISTS tipo_equipo (" +
                "id_tipo INTEGER PRIMARY KEY," +
                "tipo VARCHAR(20))";
        sqLiteDatabase.execSQL(strSQL);

        strSQL = "CREATE TABLE IF NOT EXISTS estado_equipo (" +
                "id_estado INTEGER PRIMARY KEY AUTOINCREMENT," +
                "estado VARCHAR(20))";
        sqLiteDatabase.execSQL(strSQL);

        strSQL = "CREATE TABLE IF NOT EXISTS rol (" +
                "id_rol INTEGER PRIMARY KEY AUTOINCREMENT," +
                "descripcion VARCHAR(20)" +
                ")";
        sqLiteDatabase.execSQL(strSQL);

*/

                strSQL = "CREATE TABLE IF NOT EXISTS equipos (" +
                "id_equipos INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tipo_equipo varchar(20)," +
                "serial VARCHAR(20)," +
                "descripcion VARCHAR(20)," +
                "codigo varchar(20)," +
                "estado varchar(29)" +
                ");";
        sqLiteDatabase.execSQL(strSQL);

        strSQL = "CREATE TABLE IF NOT EXISTS usuarios (" +
                "cedula INTEGER PRIMARY KEY," +
                "nombre VARCHAR(50)," +
                "apellido VARCHAR(50)," +
                "telefono integer(50)," +
                "email VARCHAR(80)," +
                "rol varchar(50)," +
                "contraseña varchar(50)" +
                ")";
        sqLiteDatabase.execSQL(strSQL);

        strSQL = "CREATE TABLE IF NOT EXISTS prestamo (" +
                "id_prestamo INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tipo_equipo varchar(50)," +
                "cedula VARCHAR(50)," +
                "cantidad varchar(50)," +
                "fecha_prestamo VARCHAR(50)," +
                "hora_prestamo varcha(50)," +
                "fecha_devolucion VARCHAR(50)," +
                "hora_devolucion varchar(50)," +
                "FOREIGN KEY (cedula) REFERENCES usuarios(cedula)" +
                ")";
        sqLiteDatabase.execSQL(strSQL);
        /*


        strSQL = "CREATE TABLE IF NOT EXISTS detalle_prestamo (" +
                "id_detalle_prestamo INTEGER PRIMARY KEY AUTOINCREMENT," +
                "FOREIGN KEY (prestamo) REFERENCES prestamo(id_prestamo)," +
                "FOREIGN KEY (equipos) REFERENCES equipos(id_equipos)" +
                ")";
        sqLiteDatabase.execSQL(strSQL);

        strSQL = "CREATE TABLE IF NOT EXISTS novedades (" +
                "id_novedades INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "descripcion VARCHAR(20),"+
                "fecha_nomedad VARCHAR(20),"+
                "tipo_novedad VARCHAR(20),"+
                "FOREIGN KEY (prestamo) REFERENCES prestamo(id_prestamo)" +
                ")";
        sqLiteDatabase.execSQL(strSQL);
*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Implement the upgrade logic here if needed
        // This method will be triggered when you change the DATABASE_VERSION.
        // You can drop tables and recreate them with updated schema.
    }

    public void Escribir (String strSQL) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL(strSQL);
        sqLiteDatabase.close();
    }


    public void Actualizar (String strSQL) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL(strSQL);
        sqLiteDatabase.close();
    }

    public void Eliminar(String strSQL) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL(strSQL);
        sqLiteDatabase.close();
    }

    @SuppressLint("Range")
    public JSONArray getJSON(String strSql, String[] columnas) {
        JSONArray jsonArray = new JSONArray();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(strSql, null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            JSONObject jsonObject = new JSONObject();
            for (int j = 0; j < columnas.length; j++) {
                try {
                    jsonObject.put(columnas[j], cursor.getString(cursor.getColumnIndex(columnas[j])));
                } catch (Exception e) {

                }
            }
            jsonArray.put(jsonObject);
            cursor.moveToNext();
        }
        db.close();
        return jsonArray;
    }
}


