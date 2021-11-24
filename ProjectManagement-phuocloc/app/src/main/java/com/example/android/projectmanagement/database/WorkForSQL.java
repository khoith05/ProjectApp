package com.example.android.projectmanagement.database;

public class WorkForSQL {
    public static final String TABLE_NAME="workfor";
    public static final String COLUMN_EMPLOYEEID="empoyeeid";
    public static final String COLUMN_PROJECTID="projectid";

    public long employeeId;
    public long projectId;

    public WorkForSQL(long employeeId, long projectId) {
        this.employeeId = employeeId;
        this.projectId = projectId;
    }

    public static final String CREATE_TABLE=
            "CREATE TABLE "+TABLE_NAME+ "("+
                    COLUMN_EMPLOYEEID+" INTEGER, "+
                    COLUMN_PROJECTID +" INTEGER,"+
                    "FOREIGN KEY("+COLUMN_PROJECTID+") REFERENCES "+
                    ProjectSQL.TABLE_NAME+"("+ProjectSQL.COLUMN_ID+"),"+
                    "FOREIGN KEY("+COLUMN_EMPLOYEEID+") REFERENCES "+
                    EmployeeSQL.TABLE_NAME+"("+EmployeeSQL.COLUMN_ID+") );";
}
