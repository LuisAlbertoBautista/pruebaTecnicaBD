package com.example.pruebatecnica.ui.catalogo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "my_data_espository.db";
    private static final int DATABASE_VERSION = 1;

    // Nombre de la tabla y columnas
    public static final String TABLE_ABASTECIMIENTO = "abastecimiento";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TIPO_ABASTECIMIENTO = "tipo_abastecimiento";
    public static final String COLUMN_USUARIO_CREACION = "usuario_creacion";
    public static final String COLUMN_FECHA_CREACION = "fecha_creacion";

    public static final String TABLE_OPCIONES_DE_USUARIO = "opciones";
    public static final String COLUMN_IMAGE_ABASTECIMIENTO = "image";
    public static final String COLUMN_OPCION = "opcion";
    public static final String COLUMN_NOMBRE = "nombre";

    // Sentencia SQL para crear la tabla
    private static final String CREATE_TABLE_ABASTECIMIENTO =
            "CREATE TABLE " + TABLE_ABASTECIMIENTO + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_TIPO_ABASTECIMIENTO + " TEXT," +
                    COLUMN_USUARIO_CREACION + " TEXT," +
                    COLUMN_FECHA_CREACION + " TEXT" +
                    ")";

    private static final String CREATE_TABLE_OPCIONES_DE_USUARIO =
            "CREATE TABLE " + TABLE_OPCIONES_DE_USUARIO + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_TIPO_ABASTECIMIENTO + " TEXT," +
                    COLUMN_OPCION + " TEXT," +
                    COLUMN_IMAGE_ABASTECIMIENTO + " TEXT," +
                    COLUMN_NOMBRE + " TEXT," +
                    COLUMN_FECHA_CREACION + " TEXT" +
                    ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crea la tabla al crear la base de datos por primera vez
        db.execSQL(CREATE_TABLE_ABASTECIMIENTO);
        db.execSQL(CREATE_TABLE_OPCIONES_DE_USUARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Maneja actualizaciones de la base de datos si es necesario
        // En este ejemplo, simplemente eliminamos la tabla y la volvemos a crear
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ABASTECIMIENTO);
        onCreate(db);
    }
}
