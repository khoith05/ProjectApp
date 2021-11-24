package com.example.android.projectmanagement.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME= "project_db";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(EmployeeSQL.CREATE_TABLE);
        sqLiteDatabase.execSQL(UserSQL.CREATE_TABLE);
        sqLiteDatabase.execSQL(ProjectSQL.CREATE_TABLE);
        sqLiteDatabase.execSQL(TaskSQL.CREATE_TABLE);
        sqLiteDatabase.execSQL(BelongToSQL.CREATE_TABLE);
        sqLiteDatabase.execSQL(WorkForSQL.CREATE_TABLE);
        insertUser(UserSQL.id,sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+EmployeeSQL.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+UserSQL.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ProjectSQL.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TaskSQL.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+BelongToSQL.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+WorkForSQL.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    public long insertEmployee(String name,String phone,String email,String job,String address,String salary,byte[] img){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EmployeeSQL.COLUMN_NAME,name);
        values.put(EmployeeSQL.COLUMN_PHONE,phone);
        values.put(EmployeeSQL.COLUMN_EMAIL,email);
        values.put(EmployeeSQL.COLUMN_JOB,job);
        values.put(EmployeeSQL.COLUMN_ADDRESS,address);
        values.put(EmployeeSQL.COLUMN_SALARY,salary);
        values.put(EmployeeSQL.COLUMN_IMG,img);

        long id = sqLiteDatabase.insert(EmployeeSQL.TABLE_NAME,null,values);
        sqLiteDatabase.close();
        return id;

    }

    public long insertProject(String name,String des,String start,String end,String state){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ProjectSQL.COLUMN_NAME,name);
        values.put(ProjectSQL.COLUMN_DES,des);
        values.put(ProjectSQL.COLUMN_START,start);
        values.put(ProjectSQL.COLUMN_END,end);
        values.put(ProjectSQL.COLUMN_STATE,state);

        long id = sqLiteDatabase.insert(ProjectSQL.TABLE_NAME,null,values);
        sqLiteDatabase.close();
        return id;

    }

    public long insertUser(Long id, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(UserSQL.COLUMN_ID, id);
        values.put(UserSQL.COLUMN_NAME, "");
        values.put(UserSQL.COLUMN_PHONE, "");
        values.put(UserSQL.COLUMN_EMAIL, "");
        values.put(UserSQL.COLUMN_ADDRESS, "");
        values.put(UserSQL.COLUMN_IMG, "");
        long newRowId = db.insert(UserSQL.TABLE_NAME, null, values);
        return id;
    }

    public EmployeeSQL getEmpoloyee(long id){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(EmployeeSQL.TABLE_NAME,
                new String[]{EmployeeSQL.COLUMN_ID,EmployeeSQL.COLUMN_NAME,EmployeeSQL.COLUMN_PHONE, EmployeeSQL.COLUMN_EMAIL,
                        EmployeeSQL.COLUMN_JOB,EmployeeSQL.COLUMN_ADDRESS,EmployeeSQL.COLUMN_SALARY,EmployeeSQL.COLUMN_IMG},EmployeeSQL.COLUMN_ID+"=?",
                new String[]{String.valueOf(id)},null,null,null,null);
        if (cursor!=null){
            cursor.moveToFirst();
        }
        EmployeeSQL employeeSQL= new EmployeeSQL(cursor.getLong(cursor.getColumnIndex(EmployeeSQL.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(EmployeeSQL.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(EmployeeSQL.COLUMN_PHONE)),
                cursor.getString(cursor.getColumnIndex(EmployeeSQL.COLUMN_EMAIL)),
                cursor.getString(cursor.getColumnIndex(EmployeeSQL.COLUMN_JOB)),
                cursor.getString(cursor.getColumnIndex(EmployeeSQL.COLUMN_ADDRESS)),
                cursor.getString(cursor.getColumnIndex(EmployeeSQL.COLUMN_SALARY)),
                cursor.getBlob(cursor.getColumnIndex(EmployeeSQL.COLUMN_IMG)));
        cursor.close();
        return employeeSQL;
    }

    public ProjectSQL getProject(long id){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(ProjectSQL.TABLE_NAME,
                new String[]{ProjectSQL.COLUMN_ID,ProjectSQL.COLUMN_NAME,ProjectSQL.COLUMN_DES, ProjectSQL.COLUMN_START,
                        ProjectSQL.COLUMN_END,ProjectSQL.COLUMN_STATE},ProjectSQL.COLUMN_ID+"=?",
                new String[]{String.valueOf(id)},null,null,null,null);
        if (cursor!=null){
            cursor.moveToFirst();
        }
        ProjectSQL projectSQL= new ProjectSQL(cursor.getLong(cursor.getColumnIndex(ProjectSQL.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(ProjectSQL.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(ProjectSQL.COLUMN_DES)),
                cursor.getString(cursor.getColumnIndex(ProjectSQL.COLUMN_START)),
                cursor.getString(cursor.getColumnIndex(ProjectSQL.COLUMN_END)),
                cursor.getString(cursor.getColumnIndex(ProjectSQL.COLUMN_STATE)));
        cursor.close();
        return projectSQL;
    }
    public int updateUser(UserSQL userSQL){
        ContentValues values=new ContentValues();
        values.put(UserSQL.COLUMN_NAME,userSQL.name);
        values.put(UserSQL.COLUMN_PHONE,userSQL.phone);
        values.put(UserSQL.COLUMN_EMAIL,userSQL.email);
        values.put(UserSQL.COLUMN_ADDRESS,userSQL.address);
        values.put(UserSQL.COLUMN_IMG,userSQL.img);

        String selection=UserSQL.COLUMN_ID +" LIKE ?";
        String[] SelectionArgs= {String.valueOf(userSQL.id)};

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int count =sqLiteDatabase.update(UserSQL.TABLE_NAME,values,selection,SelectionArgs);
        sqLiteDatabase.close();
        return count;


    }

    public UserSQL getUser(){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                UserSQL.COLUMN_ID,
                UserSQL.COLUMN_NAME,
                UserSQL.COLUMN_PHONE,
                UserSQL.COLUMN_EMAIL,
                UserSQL.COLUMN_ADDRESS,
                UserSQL.COLUMN_IMG
        };
        String selection = UserSQL.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(UserSQL.id)};
        Cursor cursor = db.query(
                UserSQL.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null
        );

        List<UserSQL> list_user = new ArrayList<>();
        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(UserSQL.COLUMN_ID));
            String itemName = cursor.getString(
                    cursor.getColumnIndexOrThrow(UserSQL.COLUMN_NAME));
            String itemPhone = cursor.getString(
                    cursor.getColumnIndexOrThrow(UserSQL.COLUMN_PHONE));
            String itemEmail = cursor.getString(
                    cursor.getColumnIndexOrThrow(UserSQL.COLUMN_EMAIL));
            String itemAddress = cursor.getString(
                    cursor.getColumnIndexOrThrow(UserSQL.COLUMN_ADDRESS));
            byte[] itemImage = cursor.getBlob(
                    cursor.getColumnIndexOrThrow(UserSQL.COLUMN_IMG));
            list_user.add(new UserSQL(itemId, itemName, itemPhone, itemEmail, itemAddress, itemImage));
        }
        cursor.close();
        return list_user.get(0);
    }

    public List<EmployeeSQL> getAlLEmployee(){
        List<EmployeeSQL> employeeSQLList=new ArrayList<>();
        String selectQuery = "SELECT * FROM "+ EmployeeSQL.TABLE_NAME+ " ORDER BY "+EmployeeSQL.COLUMN_ID +" ASC;";
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do{
                EmployeeSQL employeeSQL= new EmployeeSQL(cursor.getLong(cursor.getColumnIndex(EmployeeSQL.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(EmployeeSQL.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(EmployeeSQL.COLUMN_PHONE)),
                        cursor.getString(cursor.getColumnIndex(EmployeeSQL.COLUMN_EMAIL)),
                        cursor.getString(cursor.getColumnIndex(EmployeeSQL.COLUMN_JOB)),
                        cursor.getString(cursor.getColumnIndex(EmployeeSQL.COLUMN_ADDRESS)),
                        cursor.getString(cursor.getColumnIndex(EmployeeSQL.COLUMN_SALARY)),
                        cursor.getBlob(cursor.getColumnIndex(EmployeeSQL.COLUMN_IMG)));
                employeeSQLList.add(employeeSQL);
            }while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return employeeSQLList;
    }

    public List<ProjectSQL> getAlLProject(){
        List<ProjectSQL> projectSQLList=new ArrayList<>();
        String selectQuery = "SELECT * FROM "+ ProjectSQL.TABLE_NAME+ " ORDER BY "+ProjectSQL.COLUMN_ID +" ASC;";
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do{
                ProjectSQL projectSQL= new ProjectSQL(cursor.getLong(cursor.getColumnIndex(ProjectSQL.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(ProjectSQL.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(ProjectSQL.COLUMN_DES)),
                        cursor.getString(cursor.getColumnIndex(ProjectSQL.COLUMN_START)),
                        cursor.getString(cursor.getColumnIndex(ProjectSQL.COLUMN_END)),
                        cursor.getString(cursor.getColumnIndex(ProjectSQL.COLUMN_STATE)));
                projectSQLList.add(projectSQL);
            }while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return projectSQLList;
    }


    public int updateEmployee(EmployeeSQL employeeSQL){
        ContentValues values=new ContentValues();
        values.put(EmployeeSQL.COLUMN_NAME,employeeSQL.name);
        values.put(EmployeeSQL.COLUMN_PHONE,employeeSQL.phone);
        values.put(EmployeeSQL.COLUMN_EMAIL,employeeSQL.email);
        values.put(EmployeeSQL.COLUMN_ADDRESS,employeeSQL.address);
        values.put(EmployeeSQL.COLUMN_JOB,employeeSQL.job);
        values.put(EmployeeSQL.COLUMN_SALARY,employeeSQL.salary);
        values.put(EmployeeSQL.COLUMN_IMG,employeeSQL.img);

        String selection=EmployeeSQL.COLUMN_ID +" LIKE ?";
        String[] SelectionArgs= {String.valueOf(employeeSQL.id)};

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int count =sqLiteDatabase.update(EmployeeSQL.TABLE_NAME,values,selection,SelectionArgs);
        sqLiteDatabase.close();
        Log.d("rundebug",String.valueOf(count));
        return count;


    }
    public int deleteEmployee(long id){
        String selection = EmployeeSQL.COLUMN_ID +" LIKE ?";
        String[] SelectionArgs = {String.valueOf(id)};
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        int count = sqLiteDatabase.delete(EmployeeSQL.TABLE_NAME,selection,SelectionArgs);
        sqLiteDatabase.close();
        return count;
    }


    public int updateProject(ProjectSQL employeeSQL){
        ContentValues values=new ContentValues();
        values.put(ProjectSQL.COLUMN_NAME,employeeSQL.name);
        values.put(ProjectSQL.COLUMN_DES,employeeSQL.description);
        values.put(ProjectSQL.COLUMN_START,employeeSQL.date_start);
        values.put(ProjectSQL.COLUMN_END,employeeSQL.date_end);
        values.put(ProjectSQL.COLUMN_STATE,employeeSQL.state);

        String selection=ProjectSQL.COLUMN_ID +" LIKE ?";
        String[] SelectionArgs= {String.valueOf(employeeSQL.id)};

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int count =sqLiteDatabase.update(ProjectSQL.TABLE_NAME,values,selection,SelectionArgs);
        sqLiteDatabase.close();
        Log.d("rundebug",String.valueOf(count));
        return count;


    }
    public int deleteProject(long id){
        String selection = ProjectSQL.COLUMN_ID +" LIKE ?";
        String[] SelectionArgs = {String.valueOf(id)};
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        int count = sqLiteDatabase.delete(ProjectSQL.TABLE_NAME,selection,SelectionArgs);
        sqLiteDatabase.close();
        return count;
    }

    public List<TaskSQL> getAllTask(long projectid){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                TaskSQL.COLUMN_ID,
                TaskSQL.COLUMN_NAME,
                TaskSQL.COLUMN_START,
                TaskSQL.COLUMN_END,
                TaskSQL.COLUMN_DESCRIPTION,
                TaskSQL.COLUMN_STATE,
                TaskSQL.COLUMN_PROJECTID
        };

        String selection = TaskSQL.COLUMN_PROJECTID + " = ?";
        String[] selectionArgs = { String.valueOf(projectid) };

        String sortOrder =
                TaskSQL.COLUMN_ID + " ASC";

        Cursor cursor = db.query(
                TaskSQL.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
        List<TaskSQL> taskSQLList=new ArrayList<>();
        while(cursor.moveToNext()) {
            long id = cursor.getLong(
                    cursor.getColumnIndexOrThrow(TaskSQL.COLUMN_ID));
            String name=cursor.getString(cursor.getColumnIndexOrThrow(TaskSQL.COLUMN_NAME));
            String start=cursor.getString(cursor.getColumnIndexOrThrow(TaskSQL.COLUMN_START));
            String end=cursor.getString(cursor.getColumnIndexOrThrow(TaskSQL.COLUMN_END));
            String des=cursor.getString(cursor.getColumnIndexOrThrow(TaskSQL.COLUMN_DESCRIPTION));
            String state=cursor.getString(cursor.getColumnIndexOrThrow(TaskSQL.COLUMN_STATE));
            Long prjectid= cursor.getLong(cursor.getColumnIndexOrThrow(TaskSQL.COLUMN_PROJECTID));
            taskSQLList.add(new TaskSQL(id,name,start,end,des,state,prjectid));
        }
        cursor.close();
        for(int i=0;i<taskSQLList.size();i++){
            getAllEmployeeBeLongToTask(taskSQLList.get(i).id);
            taskSQLList.get(i).choose=getAllEmployeeBeLongToTask(taskSQLList.get(i).id);
        }
        return taskSQLList;
    }

    public TaskSQL getTask(long id){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                TaskSQL.COLUMN_ID,
                TaskSQL.COLUMN_NAME,
                TaskSQL.COLUMN_START,
                TaskSQL.COLUMN_END,
                TaskSQL.COLUMN_DESCRIPTION,
                TaskSQL.COLUMN_STATE,
                TaskSQL.COLUMN_PROJECTID
        };

        String selection = TaskSQL.COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };


        Cursor cursor = db.query(
                TaskSQL.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        List<TaskSQL> taskSQLList=new ArrayList<>();
        while(cursor.moveToNext()) {
            long itemid = cursor.getLong(
                    cursor.getColumnIndexOrThrow(TaskSQL.COLUMN_ID));
            String name=cursor.getString(cursor.getColumnIndexOrThrow(TaskSQL.COLUMN_NAME));
            String start=cursor.getString(cursor.getColumnIndexOrThrow(TaskSQL.COLUMN_START));
            String end=cursor.getString(cursor.getColumnIndexOrThrow(TaskSQL.COLUMN_END));
            String des=cursor.getString(cursor.getColumnIndexOrThrow(TaskSQL.COLUMN_DESCRIPTION));
            String state=cursor.getString(cursor.getColumnIndexOrThrow(TaskSQL.COLUMN_STATE));
            Long prjectid= cursor.getLong(cursor.getColumnIndexOrThrow(TaskSQL.COLUMN_PROJECTID));
            taskSQLList.add(new TaskSQL(itemid,name,start,end,des,state,prjectid));
        }
        cursor.close();
        TaskSQL taskSQL=taskSQLList.get(0);
        getAllEmployeeBeLongToTask(taskSQL.id);
        taskSQL.choose=getAllEmployeeBeLongToTask(taskSQL.id);
        return taskSQL;
    }

    public int updateTask(TaskSQL taskSQL){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TaskSQL.COLUMN_NAME, taskSQL.name);
        values.put(TaskSQL.COLUMN_START, taskSQL.start);
        values.put(TaskSQL.COLUMN_END, taskSQL.end);
        values.put(TaskSQL.COLUMN_DESCRIPTION, taskSQL.desciption);
        values.put(TaskSQL.COLUMN_STATE, taskSQL.state);
        values.put(TaskSQL.COLUMN_PROJECTID, taskSQL.projectid);

        String selection = TaskSQL.COLUMN_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(taskSQL.id)};

        int count = db.update(
                TaskSQL.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        db.close();
        updateBelongTask(taskSQL.id,taskSQL.choose);
        return count;
    }

    public int insertTask(String name,String start,String end,String des,String state,long projectid,List<EmployeeSQL> choose){
        SQLiteDatabase db =this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TaskSQL.COLUMN_NAME, name);
        values.put(TaskSQL.COLUMN_START, start);
        values.put(TaskSQL.COLUMN_END, end);
        values.put(TaskSQL.COLUMN_DESCRIPTION, des);
        values.put(TaskSQL.COLUMN_STATE, state);
        values.put(TaskSQL.COLUMN_PROJECTID, projectid);

        long newRowId = db.insert(TaskSQL.TABLE_NAME, null, values);

        db.close();
        insertBelongtask(newRowId,choose);
        return (int)newRowId;
    }
    public int deleteTask(long id){
        SQLiteDatabase db =this.getWritableDatabase();
        String selection = TaskSQL.COLUMN_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };
        int deletedRows = db.delete(TaskSQL.TABLE_NAME, selection, selectionArgs);
        db.close();
        deleteBelongTask(id);
        return deletedRows;
    }
    public int deleteBelongTask(long id){
        SQLiteDatabase db =this.getWritableDatabase();
        String selection=BelongToSQL.COLUMN_TASKID + " LIKE ? ";
        String[] selectionArgs = { String.valueOf(id) };
        int deletedRows=db.delete(BelongToSQL.TABLE_NAME,selection,selectionArgs);
        db.close();
        return deletedRows;

    }
    public void insertBelongtask(long id, List<EmployeeSQL> choose){
        SQLiteDatabase db =this.getWritableDatabase();
        if(choose!=null &&choose.size()>0){
            for (int i =0; i<choose.size();i++){
                ContentValues values1= new ContentValues();
                values1.put(BelongToSQL.COLUMN_EMPLOYEEID,choose.get(i).id);
                values1.put(BelongToSQL.COLUMN_TASKID,id);
                db.insert(BelongToSQL.TABLE_NAME,null,values1);
            }
        }
        db.close();
    }
    public void updateBelongTask(long id, List<EmployeeSQL> choose){
        deleteBelongTask(id);
        insertBelongtask(id,choose);
    }
    public long[] getAllBelongTask(long id){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                BelongToSQL.COLUMN_EMPLOYEEID,
                BelongToSQL.COLUMN_TASKID
        };

        String selection = BelongToSQL.COLUMN_TASKID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        String sortOrder =
                BelongToSQL.COLUMN_EMPLOYEEID + " ASC";

        Cursor cursor = db.query(
                BelongToSQL.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
        long[] idlist=new long[cursor.getCount()];
        int i=0;
        while(cursor.moveToNext()) {
            idlist[i]= cursor.getLong(
                    cursor.getColumnIndexOrThrow(BelongToSQL.COLUMN_EMPLOYEEID));
            i++;
        }
        cursor.close();
        return idlist;
    }
    public List<EmployeeSQL> getAllEmployeeBeLongToTask(long id){
        long[] idlist=getAllBelongTask(id);
        List<EmployeeSQL> employeeSQLList=new ArrayList<>();
        for(int i=0;i<idlist.length;i++){
            employeeSQLList.add(getEmpoloyee(idlist[i]));
        }
        return employeeSQLList;
    }

    

}
