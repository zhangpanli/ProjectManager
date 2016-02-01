package com.self.projectmanager.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.self.projectmanager.R;
import com.self.projectmanager.adapter.ListAdapter;
import com.self.projectmanager.dao.SimpleDao;
import com.self.projectmanager.model.Member;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 攀礼 on 2016/1/10.
 */
public class MemberSelectorActivity extends Activity {

    private ListAdapter<Member> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.member_selector);

        List<Member> list = SimpleDao.listMember(new Member()); /*new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Member m = new Member();
            m.setMemberId(i);
            m.setMemberName("张攀礼");
            m.setMemberNamePinyin("ZPL");

            list.add(m);
        }*/

        List<Integer> ids = null;
        Bundle b = getIntent().getExtras();
        if (b != null) {
            ids = b.getIntegerArrayList("ids");
        }

        adapter = new ListAdapter<>(this, R.layout.lv_member_item, list,
                ids,
                new String[]{"memberName", "memberNamePinyin"},
                new int[]{R.id.item_member_name, R.id.item_member_name_pinyin},
                R.id.cb_check);

        ListView lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.check((int) id);
            }
        });
    }

    public void selectAll(View v) {
        adapter.checkAll();
    }

    public void unselect(View v) {
        adapter.uncheck();
    }

    public void deselect(View v) {
        adapter.decheck();
    }

    public void confirm(View v) {
        int count = adapter.getCount();

        ArrayList<Integer> ids = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Member m = adapter.getItem(i);

            if (adapter.isChecked(m.getMemberId())) {
                ids.add(m.getMemberId());
                names.add(m.getMemberName());
            }
        }

        Bundle b = new Bundle();
        b.putIntegerArrayList("ids", ids);
        b.putStringArrayList("names", names);

        Intent intent = new Intent();
        intent.putExtras(b);

        setResult(0, intent);

        finish();
    }

    public void cancel(View v) {
        finish();
    }
}