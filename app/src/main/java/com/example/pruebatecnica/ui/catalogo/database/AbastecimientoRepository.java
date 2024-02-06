package com.example.pruebatecnica.ui.catalogo.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.pruebatecnica.ui.catalogo.database.DatabaseHelper;
import com.example.pruebatecnica.ui.catalogo.models.Abastecimiento;

import java.util.ArrayList;
import java.util.List;

public class AbastecimientoRepository {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public AbastecimientoRepository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        try {
            database = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            dbHelper.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long insertAbastecimiento(Abastecimiento abastecimiento) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TIPO_ABASTECIMIENTO, abastecimiento.getTipoAbastecimiento());
        values.put(DatabaseHelper.COLUMN_USUARIO_CREACION, abastecimiento.getUsuarioCreacion());
        values.put(DatabaseHelper.COLUMN_FECHA_CREACION, abastecimiento.getFechaCreacion());

        try {
            return database.insert(DatabaseHelper.TABLE_ABASTECIMIENTO, null, values);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Manejar el error de inserción
        }
    }
    public long insertAbastecimientos(List<Abastecimiento> abastecimientos) {
        long result = -1; // Inicializar el resultado como error por defecto

        try {
            database.beginTransaction(); // Iniciar la transacción

            for (Abastecimiento abastecimiento : abastecimientos) {
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.COLUMN_TIPO_ABASTECIMIENTO, abastecimiento.getTipoAbastecimiento());
                values.put(DatabaseHelper.COLUMN_USUARIO_CREACION, abastecimiento.getUsuarioCreacion());
                values.put(DatabaseHelper.COLUMN_FECHA_CREACION, abastecimiento.getFechaCreacion());

                // Insertar el abastecimiento en la base de datos
                long rowId = database.insert(DatabaseHelper.TABLE_ABASTECIMIENTO, null, values);

                // Verificar si la inserción fue exitosa
                if (rowId != -1) {
                    result = rowId; // Actualizar el resultado con el ID del último abastecimiento insertado
                } else {
                    // Si hubo un error, marca la transacción como fallida y sal del bucle
                    database.endTransaction();
                    return -1;
                }
            }

            database.setTransactionSuccessful(); // Marcar la transacción como exitosa
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.endTransaction(); // Finalizar la transacción
        }

        return result;
    }

    public void actualizarBaseDeDatos(List<Abastecimiento> abastecimientos) {
        try {
            // Parsear el JSON a una lista de objetos Abastecimiento
            List<Abastecimiento> listaAbastecimientos = abastecimientos;

            // Iniciar la transacción para la actualización en bloque
            database.beginTransaction();

            for (Abastecimiento abastecimiento : listaAbastecimientos) {
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.COLUMN_TIPO_ABASTECIMIENTO, abastecimiento.getTipoAbastecimiento());
                values.put(DatabaseHelper.COLUMN_USUARIO_CREACION, abastecimiento.getUsuarioCreacion());
                values.put(DatabaseHelper.COLUMN_FECHA_CREACION, abastecimiento.getFechaCreacion());

                // Actualizar el abastecimiento en la base de datos
                database.update(
                        DatabaseHelper.TABLE_ABASTECIMIENTO,
                        values,
                        DatabaseHelper.COLUMN_ID + " = ?",
                        new String[]{String.valueOf(abastecimiento.getIdAbastecimiento())}
                );
            }

            // Establecer la transacción como exitosa y finalizarla
            database.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Finalizar la transacción
            database.endTransaction();
        }
    }

    @SuppressLint("Range")
    public List<Abastecimiento> getAllAbastecimientos() {
        List<Abastecimiento> abastecimientos = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = database.query(DatabaseHelper.TABLE_ABASTECIMIENTO, null, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Abastecimiento abastecimiento = new Abastecimiento();
                    abastecimiento.setIdAbastecimiento(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)));
                    abastecimiento.setTipoAbastecimiento(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TIPO_ABASTECIMIENTO)));
                    abastecimiento.setUsuarioCreacion(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USUARIO_CREACION)));
                    abastecimiento.setFechaCreacion(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FECHA_CREACION)));
                    abastecimientos.add(abastecimiento);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return abastecimientos;
    }

    @SuppressLint("Range")
    public Abastecimiento getAbastecimientoById(int id) {
        Cursor cursor = null;

        try {
            cursor = database.query(DatabaseHelper.TABLE_ABASTECIMIENTO, null, DatabaseHelper.COLUMN_ID + "=?",
                    new String[]{String.valueOf(id)}, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                Abastecimiento abastecimiento = new Abastecimiento();
                abastecimiento.setIdAbastecimiento(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)));
                abastecimiento.setTipoAbastecimiento(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TIPO_ABASTECIMIENTO)));
                abastecimiento.setUsuarioCreacion(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USUARIO_CREACION)));
                abastecimiento.setFechaCreacion(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FECHA_CREACION)));
                return abastecimiento;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return null; // Si no se encuentra el Abastecimiento con el ID especificado
    }

    public int updateAbastecimiento(Abastecimiento abastecimiento) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TIPO_ABASTECIMIENTO, abastecimiento.getTipoAbastecimiento());
        values.put(DatabaseHelper.COLUMN_USUARIO_CREACION, abastecimiento.getUsuarioCreacion());
        values.put(DatabaseHelper.COLUMN_FECHA_CREACION, abastecimiento.getFechaCreacion());

        try {
            return database.update(DatabaseHelper.TABLE_ABASTECIMIENTO, values, DatabaseHelper.COLUMN_ID + "=?",
                    new String[]{String.valueOf(abastecimiento.getIdAbastecimiento())});
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Manejar el error de actualización
        }
    }

    public void deleteAbastecimiento(int id) {
        try {
            database.delete(DatabaseHelper.TABLE_ABASTECIMIENTO, DatabaseHelper.COLUMN_ID + "=?",
                    new String[]{String.valueOf(id)});
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar el error de eliminación
        }
    }
}
