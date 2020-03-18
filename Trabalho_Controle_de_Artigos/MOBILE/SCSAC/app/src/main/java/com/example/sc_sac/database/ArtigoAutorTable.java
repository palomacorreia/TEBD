package com.example.sc_sac.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sc_sac.bean.ArtigoAutor;

public class ArtigoAutorTable extends ArtigoAutor implements DatabaseBean{

    protected static final String TABLE_ARTIGO_AUTOR = "artigo_autor";
    protected static final String COLUMN_ARTIGO_AUTOR_ID = "artigo_autor_id";
    protected static final String COLUMN_ARTIGO_AUTOR_USU_ID= "artigo_autor_usu_id";
    protected static final String COLUMN_ARTIGO_AUTOR_ART_ID = "artigo_autor_art_id";
    protected static final String COLUMN_EMAIL_AUTOR = "artigo_email_autor";


    public ArtigoAutorTable(Cursor cursor) {
        if(cursor != null){
            loadFromCursor(cursor);
        }
    }

    public ArtigoAutorTable() {}

    protected void loadFromCursor(Cursor cursor){
        setArtigoAutorId(cursor.getInt(cursor.getColumnIndex(COLUMN_ARTIGO_AUTOR_ID)));
        setArtigoId(cursor.getInt(cursor.getColumnIndex(COLUMN_ARTIGO_AUTOR_ART_ID)));
        setUsuarioId(cursor.getInt(cursor.getColumnIndex(COLUMN_ARTIGO_AUTOR_USU_ID)));
        setEmail_autor(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_AUTOR)));
    }

    public static void onCreate(SQLiteDatabase database){

        database.execSQL("CREATE TABLE " + TABLE_ARTIGO_AUTOR + "(" +
                COLUMN_ARTIGO_AUTOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ARTIGO_AUTOR_ART_ID + " INTEGER NULL, " +
                COLUMN_ARTIGO_AUTOR_USU_ID + " INTEGER NULL," +
                COLUMN_EMAIL_AUTOR + " TEXT NULL " +
                ");"
        );
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        try {
            for(int i = oldVersion; i <= newVersion; i++) {
                switch (i) {
                    case 0:
                        /* Versão inicial do banco */
                        break;
                    default:
                        if(i < newVersion) {
                            continue;
                        } else {
                            throw new IllegalStateException("onUpgrade() with unknown oldVersion" + oldVersion);
                        }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isFieldExist(SQLiteDatabase database, String tableName, String fieldName){
        boolean isExist = false;
        Cursor res = database.rawQuery("PRAGMA table_info(" + tableName + ")", null);
        if (res.moveToFirst()) {
            do {
                int value = res.getColumnIndex("name");
                if (value != -1 && res.getString(value).equals(fieldName)){
                    isExist = true;
                }
            } while (res.moveToNext());
        }
        return isExist;
    }

    protected ContentValues addDataContentValues() {
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ARTIGO_AUTOR_ID, getArtigoAutorId());
        contentValues.put(COLUMN_ARTIGO_AUTOR_ART_ID,getArtigoId());
        contentValues.put(COLUMN_ARTIGO_AUTOR_USU_ID,getUsuarioId());
        contentValues.put(COLUMN_EMAIL_AUTOR, getEmail_autor());

        return contentValues;
    }
    @Override
    public boolean save(SQLiteDatabase database) {
        ContentValues values = this.addDataContentValues();
        long saveId;
        saveId = database.insert(TABLE_ARTIGO_AUTOR, null, values);
        if (saveId != -1){
            setArtigoAutorId((int) saveId);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(SQLiteDatabase database) {
        if (getArtigoId() != null){
            return database.delete(TABLE_ARTIGO_AUTOR,TABLE_ARTIGO_AUTOR + " = ?", new String[]{ getArtigoAutorId().toString()} ) != 0;
        }
        return false;
    }

    @Override
    public boolean update(SQLiteDatabase database) {
        return false;
    }

    protected boolean isNew() {
        return this.getArtigoId() == null;
    }

    public static void deleteArtigoAutor(SQLiteDatabase database){
        database.delete(TABLE_ARTIGO_AUTOR,COLUMN_ARTIGO_AUTOR_ID + " > ?",	new	String[]{"0"});
        database.close();
    }
}
