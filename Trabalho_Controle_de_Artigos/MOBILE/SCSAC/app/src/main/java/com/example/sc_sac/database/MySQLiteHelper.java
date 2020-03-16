package com.example.sc_sac.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION_CURRENT = 1;
    private static final String NOME_BANCO = "sac.database";

    public MySQLiteHelper(Context context) {
        super(context, NOME_BANCO, null, DATABASE_VERSION_CURRENT);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        ArtigoTable.onCreate(db);
        UsuarioTable.onCreate(db);
        ArtigoAutorTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (!isTableExists(db, ArtigoTable.TABLE_ARTIGO)) {
            ArtigoTable.onCreate(db);
            UsuarioTable.onCreate(db);
            ArtigoAutorTable.onCreate(db);
        } else {
            ArtigoTable.onUpgrade(db, oldVersion, newVersion);
            UsuarioTable.onUpgrade(db, oldVersion, newVersion);
            ArtigoAutorTable.onUpgrade(db, oldVersion, newVersion);
        }
    }

    public boolean isTableExists(SQLiteDatabase database, String tableName) {
        boolean isExist = false;
        Cursor cursor = database.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                isExist = true;
            }
            cursor.close();
        }
        return isExist;
    }
}
