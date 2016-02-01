package com.self.projectmanager.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.self.projectmanager.R;
import com.self.projectmanager.db.DatabaseUtils;
import com.self.projectmanager.db.SqliteHelper;
import com.self.projectmanager.model.SignInRecord;
import com.self.projectmanager.utils.CollectionUtils;
import com.self.projectmanager.utils.DateUtils;
import com.self.projectmanager.utils.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SignInActivity extends Activity {

    private ArrayList<Integer> projectIds;
    private ArrayList<Integer> memberIds;

    private EditText et_project;
    private EditText et_member;
    private EditText et_amount;
    private EditText et_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            setContentView(R.layout.sign_in_main);
        }

        et_project = (EditText) findViewById(R.id.txt_project_name);
        et_member = (EditText) findViewById(R.id.txt_member_name);
        et_amount = (EditText) findViewById(R.id.txt_amount);
        et_date = (EditText) findViewById(R.id.txt_date);

        et_amount.setText("1");
        et_date.setText(DateUtils.format(new Date()));
    }

    public void add(View view) {
        String amountStr = et_amount.getText().toString();
        String dateStr = et_date.getText().toString();
        String comment = ((EditText) findViewById(R.id.txt_comment)).getText().toString();

        if (projectIds == null || projectIds.size() == 0) {
            Toast.makeText(this, "请选择一个工程", Toast.LENGTH_LONG).show();
            return;
        }

        if (memberIds == null || memberIds.size() == 0) {
            Toast.makeText(this, "请选择要签到人员", Toast.LENGTH_LONG).show();
            return;
        }

        if (StringUtils.isEmpty(amountStr)) {
            Toast.makeText(this, "签到天数不能为空", Toast.LENGTH_LONG).show();
            return;
        }

        if (StringUtils.isEmpty(dateStr)) {
            Toast.makeText(this, "签到日期不能为空", Toast.LENGTH_LONG).show();
            return;
        }

        BigDecimal amount = new BigDecimal(amountStr);
        Date date = DateUtils.parse(dateStr);

        SQLiteDatabase db = new SqliteHelper(this, "database.db", null, 1).getWritableDatabase();

        for (Integer projectId : projectIds) {
            for (Integer memberId : memberIds) {
                SignInRecord record = new SignInRecord();
                record.setProjectId(projectId);
                record.setMemberId(memberId);
                record.setSignInDate(date);
                record.setAmount(amount);
                record.setComment(comment);
                record.setStatus("01");

                String sql = DatabaseUtils.getInsertSQL(record);
                db.execSQL(sql);
            }
        }
    }

    public void close(View view) {
        finish();
    }

    public void selectProject(View view) {
        Intent intent = new Intent(this, ProjectSelectorActivity.class);

        Bundle b = new Bundle();
        b.putIntegerArrayList("ids", projectIds);
        intent.putExtras(b);

        startActivityForResult(intent, 1);
    }

    public void selectMember(View view) {
        Intent intent = new Intent(this, MemberSelectorActivity.class);

        Bundle b = new Bundle();
        b.putIntegerArrayList("ids", memberIds);
        intent.putExtras(b);

        startActivityForResult(intent, 2);
    }

    public void chooseDate(View v) {
        String t = et_date.getText().toString();

        Date date;
        if (StringUtils.isEmpty(t)) {
            date = new Date();
        } else {
            date = DateUtils.parse(t);
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        DatePickerDialog diag = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                et_date.setText(DateUtils.format(new Date(view.getCalendarView().getDate())));
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        diag.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("intent", String.valueOf(data));

        if (data == null) {
            return;
        }

        Bundle b = data.getExtras();

        switch (requestCode) {
            case 1:
                List<String> projectNames = b.getStringArrayList("names");

                String projects = CollectionUtils.join(projectNames);
                et_project.setText(projects);

                projectIds = b.getIntegerArrayList("ids");
                break;

            case 2:
                List<String> memberNames = b.getStringArrayList("names");

                String members = CollectionUtils.join(memberNames);
                et_member.setText(members);

                memberIds = b.getIntegerArrayList("ids");
                break;
        }
    }

}
