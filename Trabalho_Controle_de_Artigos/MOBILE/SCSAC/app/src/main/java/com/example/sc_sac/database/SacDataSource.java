package com.example.sc_sac.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.sc_sac.bean.Artigo;
import com.example.sc_sac.bean.ArtigoAutor;
import com.example.sc_sac.bean.Usuario;

import java.util.List;

public class SacDataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private int openCount = 0;

    public SacDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void openDatabase() {
        if(openCount <= 0) {
            synchronized (SacDataSource.class) {
                try {
                    database = dbHelper.getWritableDatabase();
                    openCount = 1;
                } catch (SQLiteException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            openCount++;
        }
    }

    public void closeDatabase() {
        if(openCount > 1) {
            openCount--;
        } else {
            openCount = 0;
            synchronized (SacDataSource.class) {
                if (database != null && database.isOpen()) {
                    database.close();
                }
            }
        }
    }

    public Artigo newArtigo() {
        return new ArtigoTable(null);
    }

    public ArtigoAutor newArtigoAutor() {
        return new ArtigoAutorTable(null);
    }

    public Usuario newUsuario() {
        return new UsuarioTable(null);
    }

    public boolean save(DatabaseBean bean) {
        openDatabase();
        try {
            return bean.save(database);
        } finally {
            closeDatabase();
        }
    }

    public List<Artigo> getArtigo(){
        openDatabase();
        try{
            return ArtigoTable.getArtigoAll(database);
        }finally {
            closeDatabase();
        }
    }

    public String getUsuByArtigo(Integer artigoId){
        openDatabase();
        try {
            return UsuarioTable.getUsuByArtigo(database,artigoId);
        }finally {
            closeDatabase();
        }
    }

    public void deleteUsuario(){
        openDatabase();
        try {
            UsuarioTable.deleteUsuario(database);
        }finally {
            closeDatabase();
        }
    }

    public void deleteArtigo(){
        openDatabase();
        try {
            ArtigoTable.deleteArtigo(database);
        }finally {
            closeDatabase();
        }
    }

    public void deleteArtigoAutor(){
        openDatabase();
        try {
            ArtigoAutorTable.deleteArtigoAutor(database);
        }finally {
            closeDatabase();
        }
    }

    public Artigo getArtigoById(Integer artigoId){
        openDatabase();
        try {
            return  ArtigoTable.getArtigoById(database,artigoId);
        }finally {
            closeDatabase();
        }
    }
}
