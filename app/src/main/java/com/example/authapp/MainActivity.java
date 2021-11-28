package com.example.authapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.authapp.bean.Salary;
import com.example.authapp.dao.DaoSalaryManager;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText mat;
    private EditText nom;
    private EditText sal;
    private Button btnInsert;
    private Button btnSearch;
    private Button btnDelete;
    private Button btnUpdate;
    private Salary salary;
    private DaoSalaryManager daoManager;
    private int vMat=0;
    private String vNom;
    private float vSal=0;
    private Button btnList;
    private Button btnLogout;
    private Intent intent;
    private String login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        daoManager=new DaoSalaryManager(this);
        createView();
        login=intent.getStringExtra("login");
        this.setTitle("Account: "+login);
        getProfile();
        btnInsert.setOnClickListener((evt)-> {
            Toast.makeText(this, btnInsertSalary(), Toast.LENGTH_LONG).show();
        });
        btnDelete.setOnClickListener((evt)-> {
            Toast.makeText(this, btnDeleteSalary(), Toast.LENGTH_LONG).show();
            mat.setText("");
            nom.setText("");
            sal.setText("");
        });
        btnUpdate.setOnClickListener((evt)-> {
            Toast.makeText(this, btnUpdateSalary(), Toast.LENGTH_LONG).show();
        });
        btnSearch.setOnClickListener((evt)-> {
            btnSearchSalary();
        });
        btnList.setOnClickListener((evt)-> {
            Intent intent=new Intent(this,ListSalaryActivity.class);
            startActivity(intent);
        });
        btnLogout.setOnClickListener((evt)-> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this,LoginActivity.class));
        });
    }

    private void createView(){
        mat=findViewById(R.id.mat);
        nom=findViewById(R.id.nom);
        sal=findViewById(R.id.sal);
        btnInsert=findViewById(R.id.btnInsert);
        btnSearch=findViewById(R.id.btnSearch);
        btnDelete=findViewById(R.id.btnDelete);
        btnUpdate=findViewById(R.id.btnUpdate);
        btnList=findViewById(R.id.btnListSalary);
        btnLogout=findViewById(R.id.btnLogout);
        intent=getIntent();
    }

    private void getProfile(){
        if(login.startsWith("admin")){
            btnInsert.setEnabled(true);
            btnDelete.setEnabled(true);
            btnUpdate.setEnabled(true);
        }
    }

    private void getSalary(){
        salary=new Salary();
        if(!mat.getText().toString().equals(""))
            vMat=Integer.parseInt(mat.getText().toString());
        vNom=nom.getText().toString();
        if(!sal.getText().toString().equals(""))
            vSal=Float.parseFloat(sal.getText().toString());

        salary.setMat(vMat);
        salary.setNom(vNom);
        salary.setSal(vSal);
    }

    private String btnUpdateSalary(){
        getSalary();
        return daoManager.updateSalary(salary);
    }

    private String btnInsertSalary(){
        getSalary();
        return daoManager.insertSalary(salary);
    }

    private String btnDeleteSalary(){
        getSalary();
        return daoManager.deleteSalary(vMat);
    }

    private void btnSearchSalary(){
        getSalary();
        Salary salary=daoManager.getSalaryByMat(vMat);
        if(salary!=null){
            nom.setText(salary.getNom());
            sal.setText(salary.getSal()+"");
        } else {
            Toast.makeText(this,"Salary id: "+vMat+" not Exist",Toast.LENGTH_LONG).show();
            mat.setText("");
            nom.setText("");
            sal.setText("");
        }
    }
}