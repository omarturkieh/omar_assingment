package com.example.myassignment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.example.myassignment.model.Material;
import com.example.myassignment.model.Owner;
import com.example.myassignment.model.Qutlay;

import java.util.ArrayList;


public class Database extends SQLiteOpenHelper {
    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private static Database INSTANCE = null;

    public static Database getInstace(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new Database(context, "DataDB.sqlite", null, 1);
        }
        return INSTANCE;
    }

    public void queryData(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void insertQutlay(Qutlay qutlay) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO qutlay VALUES (?,?,?,?,?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindDouble(1, qutlay.getMaterial_id());
        statement.bindDouble(2, qutlay.getOwner_id());
        statement.bindDouble(3, qutlay.getPrice());
        statement.bindString(4, qutlay.getDate());
        statement.bindString(5, qutlay.getDescription());

        statement.executeInsert();
    }

    public void updateQutlay(Qutlay qutlay) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "UPDATE qutlay SET price = ?, date = ?, description = ? WHERE material_id = ? AND owner_id = ?";
        SQLiteStatement statement = database.compileStatement(sql);


        statement.bindDouble(1, qutlay.getPrice());
        statement.bindString(2, qutlay.getDate());
        statement.bindString(3, qutlay.getDescription());
        statement.bindDouble(4, qutlay.getMaterial_id());
        statement.bindDouble(5, qutlay.getOwner_id());

        statement.execute();
        database.close();
    }

    public void deleteQutlay(Qutlay qutlay) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM qutlay WHERE material_id = ? AND owner_id = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(0, qutlay.getMaterial_id());
        statement.bindDouble(1, qutlay.getOwner_id());

        statement.execute();
        database.close();
    }

    public ArrayList<Qutlay> getQutlays() {
        String sql = "select * from qutlay";
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Qutlay> qutlays = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int materialId = Integer.parseInt(cursor.getString(0));
                int ownerId = Integer.parseInt(cursor.getString(1));
                int price = Integer.parseInt(cursor.getString(2));
                String date = cursor.getString(3);
                String desc = cursor.getString(4);
                qutlays.add(new Qutlay(materialId, ownerId, price, date, desc));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return qutlays;
    }

    public String getOwnerName(int id) {
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM owner WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double) id);

        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        String name = cursor.getString(1);
        return name;
    }

    public String getMaterialName(int id) {
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM material WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double) id);

        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        String name = cursor.getString(1);
        return name;
    }

    public void insertMaterial(Material material) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO material VALUES (null,?,?,?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, material.getName());
        statement.bindString(2, material.getDescription());
        statement.bindDouble(3, material.getService());

        statement.executeInsert();
    }

    public void updateMaterial(Material material) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "UPDATE material SET name = ?, description = ?, isService = ? WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1, material.getName());
        statement.bindString(2, material.getDescription());
        statement.bindDouble(3, material.getService());
        statement.bindDouble(4, (double) material.getId());

        statement.execute();
        database.close();
    }

    public void deleteMaterial(int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM material WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double) id);

        statement.execute();
        database.close();
    }

    public void deleteQutlay(int materialId, int ownerId) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM qutlay WHERE material_id = ? AND owner_id = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double) materialId);
        statement.bindDouble(2, (double) ownerId);

        statement.execute();
        database.close();
    }

    public ArrayList<Material> getMaterials() {
        String sql = "select * from material";
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Material> materials = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String desc = cursor.getString(2);
                int service = Integer.parseInt(cursor.getString(3));
                materials.add(new Material(id, name, desc, service));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return materials;
    }

    public void insertOwner(Owner owner) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO owner VALUES (null,?,?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, owner.getName());
        statement.bindString(2, owner.getDescription());

        statement.executeInsert();
    }

    public void updateOwner(Owner owner) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "UPDATE owner SET name = ?, description = ? WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1, owner.getName());
        statement.bindString(2, owner.getDescription());
        statement.bindDouble(3, owner.getId());

        statement.execute();
        database.close();
    }

    public void deleteOwner(int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM owner WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double) id);

        statement.execute();
        database.close();
    }


    public ArrayList<Owner> getOwners() {
        String sql = "select * from owner";
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Owner> owners = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String desc = cursor.getString(2);
                owners.add(new Owner(id, name, desc));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return owners;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS material (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, description VARCHAR, isService INTEGER)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS qutlay (material_id INTEGER, owner_id INTEGER, price INTEGER, date VARCHAR, description VARCHAR, PRIMARY KEY (material_id, owner_id))");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS owner (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, description VARCHAR)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Cursor getOneRecord(int id) {
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM DATA WHERE id = " + id;
        return database.rawQuery(sql, null);
    }


}
