package com.example.android.projectmanagement.database;

import java.io.Serializable;

public class UserSQL implements Serializable {
    public static final String TABLE_NAME="user";
    public static final String COLUMN_ID ="ID";
    public static final String COLUMN_NAME="Name";
    public static final String COLUMN_PHONE="Phone";
    public static final String COLUMN_EMAIL="Email";
    public static final String COLUMN_ADDRESS="Address";
    public static final String COLUMN_IMG="img";

    public static final long id=1233536545;
    public String name;
    public String phone;
    public String email;
    public String address;
    public byte[] img;

    public static final String CREATE_TABLE=
            "CREATE TABLE "+ TABLE_NAME+" ("+
                    COLUMN_ID +" INTEGER PRIMARY KEY,"+
                    COLUMN_NAME +" TEXT,"+
                    COLUMN_PHONE+" TEXT,"+
                    COLUMN_EMAIL+" TEXT,"+
                    COLUMN_ADDRESS+" TEXT,"+
                    COLUMN_IMG+" BLOB )";
    public UserSQL(){
    }
    public UserSQL(long IdUser,String name,String phone,String email,String address,byte[] img){
        this.name=name;
        this.phone=phone;
        this.email=email;
        this.address=address;
        this.img=img;
    }
    public long getID(){
        return this.id;
    }
}
