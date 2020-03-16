package com.example.sc_sac.database;

import android.database.sqlite.SQLiteDatabase;

public interface DatabaseBean {
    public boolean save(SQLiteDatabase database);
    public boolean delete(SQLiteDatabase database);
    public boolean update(SQLiteDatabase database);
}
