package com.self.projectmanager.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteHelper extends SQLiteOpenHelper {

	public SqliteHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	public SqliteHelper(Context context, String name, CursorFactory factory, int version,
			DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS t_project("
				+ "	project_id integer primary key autoincrement,"
				+ "	project_name varchar(100),"
				+ "	project_starttime long,"
				+ "	project_endtime long,"
				+ "	comment varchar(1000),"
				+ "	status varchar(2))");

		db.execSQL("CREATE TABLE IF NOT EXISTS t_member("
				+ "	member_id integer primary key autoincrement,"
				+ "	member_name varchar(50),"
				+ "	member_name_pinyin varchar(10),"
				+ "	comment varchar(1000),"
				+ "	status varchar(2))");

		db.execSQL("CREATE TABLE IF NOT EXISTS t_project_member("
				+ "	member_id integer,"
				+ "	project_id integer,"
				+ "	in_date long,"
				+ "	out_date long,"
				+ "	comment varchar(1000),"
				+ "	status varchar(2))");

		db.execSQL("CREATE TABLE IF NOT EXISTS t_sign_in_record("
				+ "	sign_in_id integer primary key autoincrement,"
				+ "	member_id integer,"
				+ "	project_id integer,"
				+ "	sign_in_date long,"
				+ "	amount decimal(10, 1),"
				+ "	comment varchar(1000),"
				+ "	status varchar(2))");

		db.execSQL("CREATE TABLE IF NOT EXISTS t_advance_record("
				+ "	advance_id integer primary key autoincrement,"
				+ "	member_id integer,"
				+ "	project_id integer,"
				+ "	advance_date long,"
				+ "	balance decimal(10, 2),"
				+ "	comment varchar(1000),"
				+ "	status varchar(2))");

		db.execSQL("CREATE INDEX IF NOT EXISTS idx_advance_project_id ON t_advance_record (project_id, advance_date, status)");

		db.execSQL("CREATE INDEX IF NOT EXISTS idx_sign_in_date ON t_sign_in_record (sign_in_date, status)");

		db.execSQL("CREATE INDEX IF NOT EXISTS idx_sign_in_project_id ON t_sign_in_record (project_id, sign_in_date, status)");

		db.execSQL("CREATE INDEX IF NOT EXISTS idx_member_name ON t_member (member_name, status)");

		db.execSQL("CREATE INDEX IF NOT EXISTS idx_advance_mebmer_id ON t_advance_record (member_id, advance_date, status)");

		db.execSQL("CREATE INDEX IF NOT EXISTS idx_project_time ON t_project (project_starttime, project_endtime, status)");

		db.execSQL("CREATE INDEX IF NOT EXISTS idx_advance_date ON t_advance_record (advance_date, status)");

		db.execSQL("CREATE INDEX IF NOT EXISTS idx_member_name_pinyin ON t_member (member_name_pinyin, status)");

		db.execSQL("CREATE INDEX IF NOT EXISTS idx_project_member_project_id ON t_project_member (project_id, status)");

		db.execSQL("CREATE INDEX IF NOT EXISTS idx_sign_in_member_id ON t_sign_in_record (member_id, sign_in_date, status)");

		db.execSQL("CREATE INDEX IF NOT EXISTS idx_project_name ON t_project (project_name, status)");

		db.execSQL("CREATE INDEX IF NOT EXISTS idx_project_member_member_id ON t_project_member (member_id, status)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
