package com.example.android.projectmanagement.database;

import java.io.Serializable;
import java.util.List;

public class EmployeeSQL implements Serializable {
    public static final String TABLE_NAME="employee";
    public static final String COLUMN_ID ="ID";
    public static final String COLUMN_NAME="Name";
    public static final String COLUMN_PHONE="Phone";
    public static final String COLUMN_EMAIL="Email";
    public static final String COLUMN_JOB="Job";
    public static final String COLUMN_ADDRESS="Address";
    public static final String COLUMN_SALARY="Salary";
    public static final String COLUMN_IMG="img";

    public long id;
    public String name;
    public String phone;
    public String email;
    public String job;
    public String address;
    public String salary;
    public byte[] img;
    public List<ProjectSQL> worked;
    public List<ProjectSQL> working;

    public static final String CREATE_TABLE=
            "CREATE TABLE "+ TABLE_NAME+" ("+
                COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COLUMN_NAME +" TEXT,"+
                COLUMN_PHONE+" TEXT,"+
                COLUMN_EMAIL+" TEXT,"+
                COLUMN_JOB+" TEXT,"+
                COLUMN_ADDRESS+" TEXT,"+
                COLUMN_SALARY+" TEXT,"+
                COLUMN_IMG+" BLOB )";
    public EmployeeSQL(){
    }
    public EmployeeSQL(long id,String name,String phone,String email,String job,String address,String salary,byte[] img){
        this.id=id;
        this.name=name;
        this.phone=phone;
        this.email=email;
        this.job=job;
        this.address=address;
        this.salary=salary;
        this.img=img;
    }
    public long getID(){
        return this.id;

    }
    public List<ProjectSQL> getWorked() {
        return worked;
    }

    public List<ProjectSQL> getWorking() {
        return working;
    }

    public void setWorked(List<ProjectSQL> worked) {
        this.worked = worked;
    }

    public void setWorking(List<ProjectSQL> working) {
        this.working = working;
    }

}
