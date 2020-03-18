package com.example.sc_sac.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sc_sac.bean.Usuario;

public class UsuarioTable extends Usuario implements DatabaseBean{

    protected static final String TABLE_USUARIO = "usuario";
    protected static final String COLUMN_USUARIO_ID = "usuario_id";
    protected static final String COLUMN_USUARIO_NOME = "usuario_nome";
    protected static final String COLUMN_USUARIO_ENDERECO = "usuario_endereco";
    protected static final String COLUMN_USUARIO_EMAIL = "usuario_email";
    protected static final String COLUMN_USUARIO_LOCAL_TRABALHO = "usuario_local_trabalho";
    protected static final String COLUMN_USUARIO_IS_REVISOR = "usuario_is_revisor";
    protected static final String COLUMN_USUARIO_IS_AUTOR = "usuario_is_autor";

    public UsuarioTable(Cursor cursor) {
        if(cursor != null){
            loadFromCursor(cursor);
        }
    }

    public UsuarioTable() {}

    protected void loadFromCursor(Cursor cursor){
        setUsuarioId(cursor.getInt(cursor.getColumnIndex(COLUMN_USUARIO_ID)));
        setUsuarioNome(cursor.getString(cursor.getColumnIndex(COLUMN_USUARIO_NOME)));
        setUsuarioEndereco(cursor.getString(cursor.getColumnIndex(COLUMN_USUARIO_ENDERECO)));
        setUsuarioEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USUARIO_EMAIL)));
        setLocalTrabalho(cursor.getString(cursor.getColumnIndex(COLUMN_USUARIO_LOCAL_TRABALHO)));
        if(cursor.getInt(cursor.getColumnIndex(COLUMN_USUARIO_IS_REVISOR)) == 0){
            setRevisor(false);
        }else{
            setRevisor(true);
        }
        if(cursor.getInt(cursor.getColumnIndex(COLUMN_USUARIO_IS_AUTOR)) == 0){
            setAutor(false);
        }else{
            setAutor(true);
        }
    }

    public static void onCreate(SQLiteDatabase database){

        database.execSQL("CREATE TABLE " + TABLE_USUARIO + "(" +
                COLUMN_USUARIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USUARIO_NOME + " TEXT NULL, " +
                COLUMN_USUARIO_ENDERECO + " TEXT NULL, " +
                COLUMN_USUARIO_EMAIL + " TEXT NULL, " +
                COLUMN_USUARIO_LOCAL_TRABALHO + " TEXT NULL, " +
                COLUMN_USUARIO_IS_REVISOR + " BOOLEAN NULL, " +
                COLUMN_USUARIO_IS_AUTOR + " BOOLEAN NULL " +
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

        contentValues.put(COLUMN_USUARIO_ID, getUsuarioId());
        contentValues.put(COLUMN_USUARIO_NOME,getUsuarioNome());
        contentValues.put(COLUMN_USUARIO_ENDERECO,getUsuarioEndereco());
        contentValues.put(COLUMN_USUARIO_EMAIL,getUsuarioEmail());
        contentValues.put(COLUMN_USUARIO_LOCAL_TRABALHO,getLocalTrabalho());
        contentValues.put(COLUMN_USUARIO_IS_REVISOR,getRevisor());
        contentValues.put(COLUMN_USUARIO_IS_AUTOR,getAutor());

        return contentValues;
    }

    @Override
    public boolean save(SQLiteDatabase database) {
        ContentValues values = this.addDataContentValues();
        long saveId;
        saveId = database.insert(TABLE_USUARIO, null, values);
        if (saveId != -1){
            setUsuarioId((int) saveId);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(SQLiteDatabase database) {
        if (getUsuarioId() != null){
            return database.delete(TABLE_USUARIO,COLUMN_USUARIO_ID + " = ?", new String[]{ getUsuarioId().toString()} ) != 0;
        }
        return false;
    }

    @Override
    public boolean update(SQLiteDatabase database) {
        return false;
    }

    protected boolean isNew() {
        return this.getUsuarioId() == null;
    }

    public static String getUsuByArtigo(SQLiteDatabase database, Integer artigoId) {
        String nomesUsuario = "";

        Cursor cursor = database.rawQuery("SELECT " + COLUMN_USUARIO_NOME + " FROM " + TABLE_USUARIO + " u " +
                        " INNER JOIN "+ ArtigoAutorTable.TABLE_ARTIGO_AUTOR + " a " +
                        " ON u."+ COLUMN_USUARIO_ID + " = a." + ArtigoAutorTable.COLUMN_ARTIGO_AUTOR_USU_ID+
                        " INNER JOIN " + ArtigoTable.TABLE_ARTIGO+ " t " +
                        " ON t."+ ArtigoTable.COLUMN_ARTIGO_ID + " = a."+ ArtigoAutorTable.COLUMN_ARTIGO_AUTOR_ART_ID +
                        " WHERE t." + ArtigoTable.COLUMN_ARTIGO_ID + " = " + artigoId + " ; ",null);
        while (cursor.moveToNext()) {
            String nomes = cursor.getString(0);
            if(!cursor.isLast()){
                nomesUsuario = nomesUsuario.concat(nomes+", ");
            }else{
                nomesUsuario = nomesUsuario.concat(nomes);
            }
        }

        return nomesUsuario;
    }

    public static void deleteUsuario(SQLiteDatabase database){
        database.delete(TABLE_USUARIO,COLUMN_USUARIO_ID + " > ?",	new	String[]{"0"});
        database.close();
    }
}

