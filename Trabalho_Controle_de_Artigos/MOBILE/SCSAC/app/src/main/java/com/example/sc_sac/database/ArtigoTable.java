package com.example.sc_sac.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sc_sac.bean.Artigo;

import java.util.ArrayList;
import java.util.List;

public class ArtigoTable extends Artigo implements DatabaseBean{

    protected static final String TABLE_ARTIGO = "artigo";
    protected static final String COLUMN_ARTIGO_ID = "artigo_id";
    protected static final String COLUMN_ARTIGO_NOME = "artigo_nome";
    protected static final String COLUMN_ARTIGO_RESUMO = "artigo_resumo";
    protected static final String COLUMN_ARTIGO_ARQUIVO = "artigo_arquivo";
    protected static final String COLUMN_ARTIGO_QTD_REVISORES = "artigo_qtd_revisores";
    protected static final String COLUMN_ARTIGO_MEDIA = "artigo_media";

    public ArtigoTable(Cursor cursor) {
        if(cursor != null){
            loadFromCursor(cursor);
        }
    }

    public ArtigoTable() {}

    protected void loadFromCursor(Cursor cursor){
        setArtigoId(cursor.getInt(cursor.getColumnIndex(COLUMN_ARTIGO_ID)));
        setArtigoNome(cursor.getString(cursor.getColumnIndex(COLUMN_ARTIGO_NOME)));
        setArtigoResumo(cursor.getString(cursor.getColumnIndex(COLUMN_ARTIGO_RESUMO)));
        setArtigoArquivo(cursor.getString(cursor.getColumnIndex(COLUMN_ARTIGO_ARQUIVO)));
        setArtigoQuantidadeRevisores(cursor.getInt(cursor.getColumnIndex(COLUMN_ARTIGO_QTD_REVISORES)));
        setArtigoMedia(cursor.getFloat(cursor.getColumnIndex(COLUMN_ARTIGO_MEDIA)));
    }

    public static void onCreate(SQLiteDatabase database){

        database.execSQL("CREATE TABLE " + TABLE_ARTIGO + "(" +
                COLUMN_ARTIGO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ARTIGO_NOME + " TEXT NULL, " +
                COLUMN_ARTIGO_RESUMO + " TEXT NULL, " +
                COLUMN_ARTIGO_ARQUIVO + " TEXT NULL, " +
                COLUMN_ARTIGO_QTD_REVISORES + " INTEGER NULL, " +
                COLUMN_ARTIGO_MEDIA + " FLOAT NULL " +
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

        contentValues.put(COLUMN_ARTIGO_ID, getArtigoId());
        contentValues.put(COLUMN_ARTIGO_NOME,getArtigoNome());
        contentValues.put(COLUMN_ARTIGO_RESUMO,getArtigoResumo());
        contentValues.put(COLUMN_ARTIGO_ARQUIVO,getArtigoArquivo());
        contentValues.put(COLUMN_ARTIGO_QTD_REVISORES,getArtigoQuantidadeRevisores());
        contentValues.put(COLUMN_ARTIGO_MEDIA,getArtigoMedia());

        return contentValues;
    }

    @Override
    public boolean save(SQLiteDatabase database) {
        ContentValues values = this.addDataContentValues();
        long saveId;
        saveId = database.insert(TABLE_ARTIGO, null, values);
        if (saveId != -1){
            setArtigoId((int) saveId);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(SQLiteDatabase database) {
        if (getArtigoId() != null){
            return database.delete(TABLE_ARTIGO,COLUMN_ARTIGO_ID + " = ?", new String[]{ getArtigoId().toString()} ) != 0;
        }
        return false;
    }

    @Override
    public boolean update(SQLiteDatabase database) {
        return false;
    }

    public static void onUpdate(SQLiteDatabase database){}

    public static Artigo getArtigo(SQLiteDatabase database){
        Artigo obj_artigo = null;
        Cursor cursor = database.query(TABLE_ARTIGO, null, null, null, null, null, null);
        if(cursor.moveToNext()){
            obj_artigo = new ArtigoTable(cursor);
        }else{
            obj_artigo = new ArtigoTable(null);
        }
        cursor.close();
        return obj_artigo;
    }

    public static List<Artigo> getArtigoAll(SQLiteDatabase database){
        List<Artigo> artigos = new ArrayList<Artigo>();

        Cursor cursor = database.query(TABLE_ARTIGO, null,null, null, null, null, null);
        while (cursor.moveToNext()){
            Artigo artigo = new ArtigoTable(cursor);
            artigos.add(artigo);
        }
        return artigos;
    }

    public static void deleteArtigo(SQLiteDatabase database){
        database.delete(TABLE_ARTIGO,COLUMN_ARTIGO_ID + " > ?",	new	String[]{"0"});
        database.close();
    }

    protected boolean isNew() {
        return this.getArtigoId() == null;
    }

    public static Artigo getArtigoById(SQLiteDatabase database, Integer artigoId){
        Artigo artigo = null;

        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_ARTIGO +
             " WHERE " + COLUMN_ARTIGO_ID + " = " + artigoId + " ; ",null);
        while (cursor.moveToNext()) {
            artigo = new ArtigoTable(cursor);
        }
        return artigo;
    }
}
