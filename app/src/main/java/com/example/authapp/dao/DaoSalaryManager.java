package com.example.authapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.authapp.bean.Salary;

import java.util.ArrayList;
import java.util.List;

public class DaoSalaryManager extends SQLiteOpenHelper {

    private static final String DB_NAME="salaryDb.db";
    private static final int DB_VERSION=1;
    public DaoSalaryManager(Context context) {super(context, DB_NAME, null, DB_VERSION); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Salary(mat integer primary key, nom text not null, sal float not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Salary");
    }

    //consultas de accion
    public String insertSalary(Salary salary){
        String message="Inserted Success";
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("mat",salary.getMat());
        values.put("nom",salary.getNom());
        values.put("sal",salary.getSal());
        Long result=db.insert("Salary",null,values);
        if(result==-1){
            message="Inserted Field";
        }
        return message;
    }

    public String deleteSalary(int mat){
        String message="Deleted Success";
        SQLiteDatabase db=this.getWritableDatabase();
        int result=db.delete("Salary","mat=?",new String[]{String.valueOf(mat)});
        if(result==-1){
            message="Deleted Field";
        }
        return message;
    }

    public String updateSalary(Salary salary){
        String message="Updated Success";
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("nom",salary.getNom());
        values.put("sal",salary.getSal());
        int result=db.update("Salary",values,"mat=?",new String[]{String.valueOf(salary.getMat())});
        if(result==-1){
            message="Updated Field";
        }
        return message;
    }

    public Salary getSalaryByMat(int mat){
        SQLiteDatabase db=this.getReadableDatabase();
        String sql="select * from salary where mat=?";
        Salary salary=null;
        Cursor cursor=db.rawQuery(sql,new String[]{String.valueOf(mat)});
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            salary=new Salary();
            salary.setMat(mat);
            salary.setNom(cursor.getString(1));
            salary.setSal(cursor.getFloat(2));
        }
        return salary;
    }

    public List<Salary> allSalary(){
        SQLiteDatabase db=this.getReadableDatabase();
        String sql="select * from salary";
        Cursor cursor=db.rawQuery(sql,null);
        List<Salary> salaries=new ArrayList<Salary>();
        cursor.moveToFirst();
        do{
            Salary salary=new Salary();
            salary.setMat(cursor.getInt(0));
            salary.setNom(cursor.getString(1));
            salary.setSal(cursor.getFloat(2));
            salaries.add(salary);
        }while(cursor.moveToNext());
        return salaries;
    }
}












