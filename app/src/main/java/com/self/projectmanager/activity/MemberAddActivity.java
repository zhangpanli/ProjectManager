package com.self.projectmanager.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.self.projectmanager.R;
import com.self.projectmanager.db.DatabaseUtils;
import com.self.projectmanager.db.SqliteHelper;
import com.self.projectmanager.model.Member;

/**
 * Created by 攀礼 on 2016/1/9.
 */
public class MemberAddActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            setContentView(R.layout.member_add);
        }
    }

    public void add(View view) {
        String name = ((EditText) findViewById(R.id.txt_member_name)).getText().toString();
        String pinyin = ((EditText) findViewById(R.id.txt_member_name_pinyin)).getText().toString();
        String comment = ((EditText) findViewById(R.id.txt_member_comment)).getText().toString();

        Member m = new Member();
        m.setMemberName(name);
        m.setMemberNamePinyin(pinyin);
        m.setComment(comment);
        m.setStatus("01");

        String sql = DatabaseUtils.getInsertSQL(m);

        SqliteHelper helper = new SqliteHelper(this, "database.db", null, 1);
        helper.getWritableDatabase().execSQL(sql);

        Log.d("b", sql);
    }

    public void close(View view) {
        finish();
    }

}
