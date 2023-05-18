
package com.example.utecparcial4.Helper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.utecparcial4.Models.Cliente;
import com.example.utecparcial4.Models.Vehiculo;

import java.util.ArrayList;
import java.util.List;

public class ClientesRepository extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "clientes.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CLIENTES = "MD_Clientes";
    private static final String COLUMN_CLIENTE_ID = "Id_Cliente";
    private static final String COLUMN_NOMBRE = "sNombreCliente";
    private static final String COLUMN_APELLIDOS = "sApellidosCliente";
    private static final String COLUMN_DIRECCIONES = "sDireccionesCliente";
    private static final String COLUMN_CIUDAD = "sCiudadCliente";

    private static final String TABLE_VEHICULOS = "MD_Vehiculos";
    private static final String COLUMN_VEHICULO_ID = "IdVehiculo";
    private static final String COLUMN_MARCA = "sMarca";
    private static final String COLUMN_MODELO = "sModelo";

    private static final String TABLE_CLIENTE_VEHICULO = "MD_ClienteVehiculo";
    private static final String COLUMN_CLIENTE_VEHICULO_ID = "Id_ClienteVehiculo";
    private static final String COLUMN_CLIENTE_ID_FK = "Id_Cliente";
    private static final String COLUMN_VEHICULO_ID_FK = "Id_Vehiculo";
    private static final String COLUMN_MATRICULA = "sMatricula";
    private static final String COLUMN_KILOMETROS = "iKilometros";

    public ClientesRepository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableClientesQuery = "CREATE TABLE " + TABLE_CLIENTES + "(" +
                COLUMN_CLIENTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NOMBRE + " TEXT," +
                COLUMN_APELLIDOS + " TEXT," +
                COLUMN_DIRECCIONES + " TEXT," +
                COLUMN_CIUDAD + " TEXT" +
                ")";
        db.execSQL(createTableClientesQuery);

        String createTableVehiculosQuery = "CREATE TABLE " + TABLE_VEHICULOS + "(" +
                COLUMN_VEHICULO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_MARCA + " TEXT," +
                COLUMN_MODELO + " TEXT" +
                ")";
        db.execSQL(createTableVehiculosQuery);

        String createTableClienteVehiculoQuery = "CREATE TABLE " + TABLE_CLIENTE_VEHICULO + "(" +
                COLUMN_CLIENTE_VEHICULO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_CLIENTE_ID_FK + " INTEGER," +
                COLUMN_VEHICULO_ID_FK + " INTEGER," +
                COLUMN_MATRICULA + " TEXT," +
                COLUMN_KILOMETROS + " INTEGER," +
                "FOREIGN KEY (" + COLUMN_CLIENTE_ID_FK + ") REFERENCES " + TABLE_CLIENTES + "(" + COLUMN_CLIENTE_ID + ")," +
                "FOREIGN KEY (" + COLUMN_VEHICULO_ID_FK + ") REFERENCES " + TABLE_VEHICULOS + "(" + COLUMN_VEHICULO_ID + ")" +
                ")";
        db.execSQL(createTableClienteVehiculoQuery);

        // Agregar 5 vehículos por defecto al crear la base de datos
        agregarVehiculosDefault(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // En caso de cambios en la estructura de la base de datos, puedes manejar la migración aquí
        // Por simplicidad, simplemente eliminamos las tablas existentes y las volvemos a crear
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTE_VEHICULO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VEHICULOS);
        onCreate(db);
    }

    public void crearCliente(Cliente cliente) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, cliente.getNombre());
        values.put(COLUMN_APELLIDOS, cliente.getApellidos());
        values.put(COLUMN_DIRECCIONES, cliente.getDirecciones());
        values.put(COLUMN_CIUDAD, cliente.getCiudad());
        db.insert(TABLE_CLIENTES, null, values);
        db.close();
    }

    public Cliente obtenerClientePorId(int idCliente) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COLUMN_CLIENTE_ID, COLUMN_NOMBRE, COLUMN_APELLIDOS, COLUMN_DIRECCIONES, COLUMN_CIUDAD};
        String selection = COLUMN_CLIENTE_ID + " = ?";
        String[] selectionArgs = {String.valueOf(idCliente)};
        Cursor cursor = db.query(TABLE_CLIENTES, columns, selection, selectionArgs, null, null, null);
        Cliente cliente = null;
        if (cursor.moveToFirst()) {
            String nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE));
            String apellidos = cursor.getString(cursor.getColumnIndex(COLUMN_APELLIDOS));
            String direcciones = cursor.getString(cursor.getColumnIndex(COLUMN_DIRECCIONES));
            String ciudad = cursor.getString(cursor.getColumnIndex(COLUMN_CIUDAD));
            cliente = new Cliente(idCliente, nombre, apellidos, direcciones, ciudad);
        }
        cursor.close();
        db.close();
        return cliente;
    }

    public List<Cliente> obtenerListaClientes() {
        List<Cliente> listaClientes = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COLUMN_CLIENTE_ID, COLUMN_NOMBRE, COLUMN_APELLIDOS, COLUMN_DIRECCIONES, COLUMN_CIUDAD};
        Cursor cursor = db.query(TABLE_CLIENTES, columns, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int idCliente = cursor.getInt(cursor.getColumnIndex(COLUMN_CLIENTE_ID));
                String nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE));
                String apellidos = cursor.getString(cursor.getColumnIndex(COLUMN_APELLIDOS));
                String direcciones = cursor.getString(cursor.getColumnIndex(COLUMN_DIRECCIONES));
                String ciudad = cursor.getString(cursor.getColumnIndex(COLUMN_CIUDAD));

                Cliente cliente = new Cliente(idCliente, nombre, apellidos, direcciones, ciudad);
                listaClientes.add(cliente);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return listaClientes;
    }

    public void crearClienteVehiculo(int idCliente, int idVehiculo, String matricula, int kilometros) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CLIENTE_ID_FK, idCliente);
        values.put(COLUMN_VEHICULO_ID_FK, idVehiculo);
        values.put(COLUMN_MATRICULA, matricula);
        values.put(COLUMN_KILOMETROS, kilometros);
        db.insert(TABLE_CLIENTE_VEHICULO, null, values);
        db.close();
    }

    public List<Vehiculo> obtenerVehiculosCliente(int idCliente) {
        List<Vehiculo> listaVehiculos = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {TABLE_VEHICULOS + "." + COLUMN_VEHICULO_ID, COLUMN_MARCA, COLUMN_MODELO};
        String selection = TABLE_CLIENTE_VEHICULO + "." + COLUMN_CLIENTE_ID_FK + " = ?";
        String[] selectionArgs = {String.valueOf(idCliente)};
        String joinQuery = TABLE_CLIENTE_VEHICULO + " INNER JOIN " + TABLE_VEHICULOS + " ON " +
                TABLE_CLIENTE_VEHICULO + "." + COLUMN_VEHICULO_ID_FK + " = " + TABLE_VEHICULOS + "." + COLUMN_VEHICULO_ID;
        Cursor cursor = db.query(joinQuery, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int idVehiculo = cursor.getInt(cursor.getColumnIndex(COLUMN_VEHICULO_ID));
                String marca = cursor.getString(cursor.getColumnIndex(COLUMN_MARCA));
                String modelo = cursor.getString(cursor.getColumnIndex(COLUMN_MODELO));

                Vehiculo vehiculo = new Vehiculo(idVehiculo, marca, modelo);
                listaVehiculos.add(vehiculo);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return listaVehiculos;
    }

    public List<Vehiculo> obtenerCatalogoVehiculos() {
        List<Vehiculo> catalogoVehiculos = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COLUMN_VEHICULO_ID, COLUMN_MARCA, COLUMN_MODELO};
        Cursor cursor = db.query(TABLE_VEHICULOS, columns, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int idVehiculo = cursor.getInt(cursor.getColumnIndex(COLUMN_VEHICULO_ID));
                String marca = cursor.getString(cursor.getColumnIndex(COLUMN_MARCA));
                String modelo = cursor.getString(cursor.getColumnIndex(COLUMN_MODELO));

                Vehiculo vehiculo = new Vehiculo(idVehiculo, marca, modelo);
                catalogoVehiculos.add(vehiculo);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return catalogoVehiculos;
    }

    private void agregarVehiculosDefault(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        for (int i = 0; i < 5; i++) {
            values.put(COLUMN_MARCA, "Marca " + i);
            values.put(COLUMN_MODELO, "Modelo " + i);
            db.insert(TABLE_VEHICULOS, null, values);
            values.clear();
        }
    }
}