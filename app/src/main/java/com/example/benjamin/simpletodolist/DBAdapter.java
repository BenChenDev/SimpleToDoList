package com.example.benjamin.simpletodolist;

public class DBAdapter {
    private static final String TAG = "DBAdapter";

    public static final String KEY_ROWID = "_id";
    public static final int COL_ROWID = 0;

    public static final String KEY_TASK = "task";
    public static final String KEY_DATETIME = "datetime";
    public static final String KEY_REPEAT = "repeat";

    public static final int COL_TASK = 1;
    public static final int COL_DATETIME = 2;
    public static final int COL_REPEAT = 3;

    public static final String[] ALL_KEYS = new String[]{KEY_ROWID, KEY_TASK, KEY_DATETIME, KEY_REPEAT};

    public static final String DATABASE_NAME = "MyDb";
    public static final String DATABASE_TABLE = "tasks";

    public static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE_SQL =
            "CREATE TABLE " + DATABASE_TABLE
            +"( " +
            +");";
}
