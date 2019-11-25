package com.example.nivelamento_tres.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nivelamento_tres.bean.Disciplina;

import java.util.LinkedList;
import java.util.List;

public class DisciplinaDAO extends SQLiteOpenHelper {

    private static	final String DATABASE =	"BancoDisciplinas.database";
    private static	final int VERSAO = 1;

    public DisciplinaDAO( Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String ddl	= "CREATE TABLE	Disciplina (id	INTEGER	PRIMARY	KEY," +	" disciplina TEXT UNIQUE NOT NULL);";
        sqLiteDatabase.execSQL(ddl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int velha, int nova) {
        String ddl	="DROP TABLE IF	EXISTS	Disciplina";
        sqLiteDatabase.execSQL(ddl);
        onCreate(sqLiteDatabase);
    }

    public void salvar(Disciplina disciplina){
        ContentValues values = new ContentValues();
        values.put("disciplina",disciplina.getDisciplina());

        getWritableDatabase().insert("Disciplina",null,values);
    }

    public List getLista(){
        List<Disciplina> disciplinas = new LinkedList<Disciplina>();

        String query = "SELECT * FROM Disciplina order by disciplina";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        Disciplina disciplina = null;
        if(cursor.moveToFirst()){
            do {
                disciplina = new Disciplina();
                disciplina.setId(Long.parseLong(cursor.getString(0)));
                disciplina.setDisciplina(cursor.getString(1));
                disciplinas.add(disciplina);
            }while (cursor.moveToNext());
        }

        cursor.close();
        return disciplinas;
    }

    public void	deletar(Disciplina disciplinaValue)	{
        String[] args =	{disciplinaValue.getId().toString()};
        getWritableDatabase().delete("Disciplina","id=?",	args);
    }

    public void	alterar(Disciplina	disciplina)	{
        ContentValues values = new ContentValues();
        values.put("disciplina",disciplina.getDisciplina());
        getWritableDatabase().update("Disciplina",values, "id=?",	new	String[]{disciplina.getId().toString()});
    }

    public void	dropAll(){
        getWritableDatabase().delete("Disciplina",null,	null);
    }
}
