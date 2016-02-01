package com.self.projectmanager.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.self.projectmanager.R;
import com.self.projectmanager.db.DatabaseUtils;
import com.self.projectmanager.db.SqliteHelper;
import com.self.projectmanager.model.Project;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 攀礼 on 2016/1/9.
 */
public class ProjectAddActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            setContentView(R.layout.project_add);

            ((EditText) findViewById(R.id.txt_project_starttime)).setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        }
    }

    public void add(View view) {
        String name = ((EditText) findViewById(R.id.txt_project_name)).getText().toString();

        String t = ((EditText) findViewById(R.id.txt_project_starttime)).getText().toString();
        Date starttimne = null;
        try {
            starttimne = new SimpleDateFormat("yyyy-MM-dd").parse(t);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String comment = ((EditText) findViewById(R.id.txt_project_comment)).getText().toString();

        Project p = new Project();
        p.setProjectName(name);
        p.setProjectStarttime(starttimne);
        p.setComment(comment);
        p.setStatus("01");

        String sql = DatabaseUtils.getInsertSQL(p);

        SqliteHelper helper = new SqliteHelper(this, "database.db", null, 1);
        helper.getWritableDatabase().execSQL(sql);

        Log.d("b", sql);
    }

    public void close(View view) {
        finish();
    }

    public void chooseDate(View v) {
        String t = ((EditText) findViewById(R.id.txt_project_starttime)).getText().toString();
        Date starttimne = null;
        try {
            starttimne = new SimpleDateFormat("yyyy-MM-dd").parse(t);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(starttimne);

        DatePickerDialog diag = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                ((EditText) findViewById(R.id.txt_project_starttime)).setText(new SimpleDateFormat("yyyy-MM-dd").format(view.getCalendarView().getDate()));
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        diag.show();
    }

}
