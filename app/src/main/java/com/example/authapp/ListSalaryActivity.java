package com.example.authapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.authapp.bean.Salary;
import com.example.authapp.dao.DaoSalaryManager;

import java.util.List;

public class ListSalaryActivity extends AppCompatActivity {

    private ListView listSalary;
    private List<Salary> salaries;
    private ArrayAdapter adapter;
    private DaoSalaryManager daoManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_salary);

        daoManager=new DaoSalaryManager(this);
        listSalary=findViewById(R.id.listSalary);
        salaries=daoManager.allSalary();
        adapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,salaries);
        listSalary.setAdapter(adapter);
    }
}