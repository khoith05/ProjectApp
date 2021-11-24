package com.example.android.projectmanagement.database;

import android.content.Context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TaskSQL {
    public static final String TABLE_NAME="task";
    public static final String COLUMN_ID="id";
    public static final String COLUMN_NAME="name";
    public static final String COLUMN_START="start";
    public static final String COLUMN_END="end";
    public static final String COLUMN_DESCRIPTION="desciption";
    public static final String COLUMN_STATE="state";
    public static final String COLUMN_PROJECTID="projectid";
    public static final String late="late";
    public static final String processing="processing";
    public static final String complete="complete";
    public static final String notstart="notstart";

    public static final String CREATE_TABLE=
            "CREATE TABLE "+TABLE_NAME+ "("+
                    COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    COLUMN_NAME+" TEXT,"+
                    COLUMN_START+" TEXT,"+
                    COLUMN_END+" TEXT,"+
                    COLUMN_DESCRIPTION+" TEXT,"+
                    COLUMN_STATE+" TEXT,"+
                    COLUMN_PROJECTID+" INTEGER,"+
                    "FOREIGN KEY("+COLUMN_PROJECTID+") REFERENCES "+
                    ProjectSQL.TABLE_NAME+"("+ProjectSQL.COLUMN_ID+") );";

    public long id;
    public String name;
    public String start;
    public String end;
    public String desciption;
    public String state;
    public long projectid;
    public List<EmployeeSQL> choose;

    public TaskSQL(long id, String name, String start, String end, String desciption,String state, long projectid) {
        this.id = id;
        this.name = name;
        this.start = start;
        this.end = end;
        this.desciption = desciption;
        this.state=state;
        this.projectid = projectid;
        this.choose=new ArrayList<>();
    }
    public TaskSQL(){
        this.choose=new ArrayList<>();
    }
    public void setChoose(List<EmployeeSQL> choose) {
        this.choose = choose;
    }


    public List<EmployeeSQL> getChoose() {
        return choose;
    }

    public List<EmployeeSQL> getRemainEmployee(Context context){
        List<EmployeeSQL> employeeSQLList=new LinkedList<>();
        DatabaseHelper db= new DatabaseHelper(context);
        employeeSQLList.addAll(db.getAlLEmployee());
        if (employeeSQLList.isEmpty() || this.choose.isEmpty()){
            return employeeSQLList;
        }
        Iterator itr=employeeSQLList.iterator();
        while (itr.hasNext()){
            EmployeeSQL employeeSQL=(EmployeeSQL) itr.next();
            Iterator itr2=this.choose.iterator();
            while (itr2.hasNext()){
                EmployeeSQL employeeSQL1=(EmployeeSQL) itr2.next();
                if(employeeSQL1.id==employeeSQL.id){
                    itr.remove();
                }
            }
        }
        return employeeSQLList;
    }
}
