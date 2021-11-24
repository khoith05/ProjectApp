package com.example.android.projectmanagement.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProjectSQL implements Serializable {
    public static final String TABLE_NAME="project";
    public static final String COLUMN_ID ="ID";
    public static final String COLUMN_NAME="Name";
    public static final String COLUMN_DES="Description";
    public static final String COLUMN_START="Date_start";
    public static final String COLUMN_END="Date_end";
    public static final String COLUMN_STATE="state";
    public static final String fail="Thất bại";
    public static final String succes="Thành công";
    public static final String processing="Đang thực hiện";


    public long id;
    public String name;
    public String description;
    public String date_start;
    public String date_end;
    public String state;
    public List<EmployeeSQL> choose;

    public static final String CREATE_TABLE=
            "CREATE TABLE "+ TABLE_NAME+" ("+
                    COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    COLUMN_NAME +" TEXT,"+
                    COLUMN_DES+" TEXT,"+
                    COLUMN_START+" TEXT,"+
                    COLUMN_END+" TEXT,"+
                    COLUMN_STATE+" TEXT )";
    public ProjectSQL(){
        this.choose=new ArrayList<>();
    }

    public ProjectSQL(long id, String name, String description, String date_start, String date_end, String state) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date_start = date_start;
        this.date_end = date_end;
        this.state = state;
        this.choose=new ArrayList<>();
    }


}
