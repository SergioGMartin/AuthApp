package com.example.authapp.bean;

public class Salary {
    private int mat;
    private String nom;
    private float sal;

    public int getMat() {
        return mat;
    }

    public void setMat(int mat) {
        this.mat = mat;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public float getSal() {
        return sal;
    }

    public void setSal(float sal) {
        this.sal = sal;
    }

    @Override
    public String toString() {
        return "Salary{" +
                "mat=" + mat +
                ", nom='" + nom + '\'' +
                ", sal=" + sal +
                '}';
    }
}
