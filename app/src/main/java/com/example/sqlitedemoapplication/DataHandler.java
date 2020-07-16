package com.example.sqlitedemoapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHandler extends SQLiteOpenHelper {

    // các biến mô tả cơ sở dữ liệu
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "StudentsDB.db";
    public static final String TABLE_NAME = "Students";
    public static final String COLUMN_ID = "StudentID";
    public static final String COLUMN_NAME = "StudentName";

    //phương thức khởi tạo
    public DataHandler(Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //chuỗi lệnh truy vấn tạo bảng Students
        String CREATE_STUDENTS_TABLE = "CREATE TABLE " +
                TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME + " TEXT )";

        //thực thi truy vấn
        db.execSQL(CREATE_STUDENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        //Xóa bảng nếu tồn tại
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        //Tạo bảng mới
        onCreate(db);
    }

    public String loadDataHandler() {
        String result = "";
        //chuỗi truy vấn SELECT
        String query = "SELECT* FROM " + TABLE_NAME;
        //sẵn sàng thực thi các truy vấn
        SQLiteDatabase db = this.getWritableDatabase();
        //thực thi truy vấn bằng phương thức rawQuery()
        //kết quả trả về lưu trong đối tượng Cursor
        Cursor cursor = db.rawQuery(query, null);
        //duyệt qua dữ liệu từ đối tượng Cursor
        while (((Cursor) cursor).moveToNext()) {
            //nhận giá trị cột thứ nhất (StudentID)
            int result_0 = cursor.getInt(0);
            //nhận giá trị cột thứ hai (StudentName)
            String result_1 = cursor.getString(1);
            //hiển thị mỗi hàng trong một chuỗi
            result += String.valueOf(result_0) + " " + result_1 +
                    System.getProperty("line.separator");
        }
        //đóng đối tượng Cursor
        cursor.close();
        //đóng đối tượng SQLiteDatabase
        db.close();
        return result;
    }

    public void addDataHandler(Student student) {
        //tạo đối tượng ContentValues
        ContentValues values = new ContentValues();
        //thêm giá trị các cột đến đối tượng ContentValues
        values.put(COLUMN_ID, student.getStudentID());
        values.put(COLUMN_NAME, student.getStudentName());
        SQLiteDatabase db = this.getWritableDatabase();
        //chèn dữ liệu đến bảng
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
}