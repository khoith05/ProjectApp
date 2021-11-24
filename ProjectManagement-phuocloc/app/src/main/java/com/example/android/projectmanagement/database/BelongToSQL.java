package com.example.android.projectmanagement.database;

public class BelongToSQL {
    public static final String TABLE_NAME="belongto";
    public static final String COLUMN_EMPLOYEEID="empoyeeid";
    public static final String COLUMN_TASKID="taskid";

    public long employeeId;

    public BelongToSQL(long employeeId, long taskId) {
        this.employeeId = employeeId;
        this.taskId = taskId;
    }

    public long taskId;

    public static final String CREATE_TABLE=
            "CREATE TABLE "+TABLE_NAME+ "("+
                    COLUMN_EMPLOYEEID+" INTEGER, "+
                    COLUMN_TASKID +" INTEGER,"+
                    "FOREIGN KEY("+COLUMN_TASKID+") REFERENCES "+
                    TaskSQL.TABLE_NAME+"("+TaskSQL.COLUMN_ID+"),"+
                    "FOREIGN KEY("+COLUMN_EMPLOYEEID+") REFERENCES "+
                    EmployeeSQL.TABLE_NAME+"("+EmployeeSQL.COLUMN_ID+") );";

}
