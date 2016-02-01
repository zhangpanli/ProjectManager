package com.self.projectmanager.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.self.projectmanager.R;
import com.self.projectmanager.db.SqliteHelper;

/**
 * Created by 攀礼 on 2016/1/10.
 */
public class MemberDetailFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.member_detail, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        long member_id = getActivity().getIntent().getLongExtra("member_id", -1);

        Cursor c = new SqliteHelper(getActivity(), "database.db", null, 1).getWritableDatabase()
                .rawQuery("select * from t_member where member_id = ?", new String[]{String.valueOf(member_id)});

        if (c.moveToNext()) {
            ((EditText) getActivity().findViewById(R.id.txt_member_name)).setText(c.getString(c.getColumnIndex("member_name")));
            ((EditText) getActivity().findViewById(R.id.txt_member_name_pinyin)).setText(c.getString(c.getColumnIndex("member_name_pinyin")));
            ((EditText) getActivity().findViewById(R.id.txt_member_comment)).setText(c.getString(c.getColumnIndex("comment")));
        }
    }
}
