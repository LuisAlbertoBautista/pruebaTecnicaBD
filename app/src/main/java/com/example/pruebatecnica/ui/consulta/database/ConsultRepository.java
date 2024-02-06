package com.example.pruebatecnica.ui.consulta.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.pruebatecnica.ui.catalogo.database.DatabaseHelper;
import com.example.pruebatecnica.ui.catalogo.models.Abastecimiento;
import com.example.pruebatecnica.ui.consulta.models.ItemCatalogo;

import java.util.ArrayList;
import java.util.List;

public class ConsultRepository {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public ConsultRepository(Context context) {
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

    public long insertItemCatalogoOpcion(ItemCatalogo itemCatalogo) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ID, itemCatalogo.getId());
        values.put(DatabaseHelper.COLUMN_NOMBRE, itemCatalogo.getNombre());
        values.put(DatabaseHelper.COLUMN_OPCION, itemCatalogo.getOpcionSeleccionada());
        values.put(DatabaseHelper.COLUMN_IMAGE_ABASTECIMIENTO, itemCatalogo.getRutaImagen());

        try {
            return database.insert(DatabaseHelper.TABLE_OPCIONES_DE_USUARIO, null, values);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Manejar el error de inserción
        }
    }
    public long insertOpciones(List<ItemCatalogo> itemCatalogos) {
        long result = -1; // Inicializar el resultado como error por defecto

        try {
            database.beginTransaction(); // Iniciar la transacción

            for (ItemCatalogo itemCatalogo : itemCatalogos) {
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.COLUMN_ID, itemCatalogo.getId());
                values.put(DatabaseHelper.COLUMN_NOMBRE, itemCatalogo.getNombre());
                values.put(DatabaseHelper.COLUMN_OPCION, itemCatalogo.getOpcionSeleccionada());
                values.put(DatabaseHelper.COLUMN_IMAGE_ABASTECIMIENTO, itemCatalogo.getRutaImagen());

                // Insertar el abastecimiento en la base de datos
                long rowId = database.insert(DatabaseHelper.TABLE_OPCIONES_DE_USUARIO, null, values);

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

    public void actualizarBaseDeDatos(List<ItemCatalogo> itemCatalogos) {
        try {
            // Parsear el JSON a una lista de objetos Abastecimiento
            List<ItemCatalogo> listaItemCatalogos = itemCatalogos;

            // Iniciar la transacción para la actualización en bloque
            database.beginTransaction();

            for (ItemCatalogo itemCatalogo : listaItemCatalogos) {
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.COLUMN_ID, itemCatalogo.getId());
                values.put(DatabaseHelper.COLUMN_NOMBRE, itemCatalogo.getNombre());
                values.put(DatabaseHelper.COLUMN_OPCION, itemCatalogo.getOpcionSeleccionada());
                values.put(DatabaseHelper.COLUMN_IMAGE_ABASTECIMIENTO, itemCatalogo.getRutaImagen());

                // Actualizar el abastecimiento en la base de datos
                database.update(
                        DatabaseHelper.TABLE_OPCIONES_DE_USUARIO,
                        values,
                        DatabaseHelper.COLUMN_ID + " = ?",
                        new String[]{String.valueOf(itemCatalogo.getId())}
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
    public List<ItemCatalogo> getAllItemCatalogo() {
        List<ItemCatalogo> itemCatalogos = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = database.query(DatabaseHelper.TABLE_OPCIONES_DE_USUARIO, null, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    ItemCatalogo itemCatalogo = new ItemCatalogo();
                    itemCatalogo.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)));
                    itemCatalogo.setNombre(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NOMBRE)));
                    itemCatalogo.setOpcionSeleccionada(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_OPCION)));
                    itemCatalogo.setRutaImagen(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE_ABASTECIMIENTO)));
                    itemCatalogos.add(itemCatalogo);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return itemCatalogos;
    }

    @SuppressLint("Range")
    public ItemCatalogo getAbastecimientoById(int id) {
        Cursor cursor = null;

        try {
            cursor = database.query(DatabaseHelper.TABLE_OPCIONES_DE_USUARIO, null, DatabaseHelper.COLUMN_ID + "=?",
                    new String[]{String.valueOf(id)}, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                ItemCatalogo itemCatalogo = new ItemCatalogo();
                itemCatalogo.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)));
                itemCatalogo.setNombre(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NOMBRE)));
                itemCatalogo.setOpcionSeleccionada(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_OPCION)));
                itemCatalogo.setRutaImagen(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE_ABASTECIMIENTO)));
                return itemCatalogo;
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

    public int updateItemCatalogo(ItemCatalogo itemCatalogo) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ID, itemCatalogo.getId());
        values.put(DatabaseHelper.COLUMN_NOMBRE, itemCatalogo.getNombre());
        values.put(DatabaseHelper.COLUMN_OPCION, itemCatalogo.getOpcionSeleccionada());
        values.put(DatabaseHelper.COLUMN_IMAGE_ABASTECIMIENTO, itemCatalogo.getRutaImagen());

        try {
            return database.update(DatabaseHelper.TABLE_ABASTECIMIENTO, values, DatabaseHelper.COLUMN_ID + "=?",
                    new String[]{String.valueOf(itemCatalogo.getId())});
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Manejar el error de actualización
        }
    }

    public void deleteItemCatalogo(int id) {
        try {
            database.delete(DatabaseHelper.TABLE_OPCIONES_DE_USUARIO, DatabaseHelper.COLUMN_ID + "=?",
                    new String[]{String.valueOf(id)});
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar el error de eliminación
        }
    }
}
