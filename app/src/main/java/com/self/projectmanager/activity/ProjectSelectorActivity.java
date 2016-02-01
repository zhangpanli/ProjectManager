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
import com.self.projectmanager.model.Project;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 攀礼 on 2016/1/10.
 */
public class ProjectSelectorActivity extends Activity {

    private ListAdapter<Project> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.member_selector);

        List<Project> list = SimpleDao.listProject(new Project()); /*new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Project p = new Project();
            p.setProjectId(i);
            p.setProjectName("张攀礼");
            p.setProjectStarttime(new Date());

            list.add(p);
        }*/

        List<Integer> ids = null;
        Bundle b = getIntent().getExtras();
        if (b != null) {
            ids = b.getIntegerArrayList("ids");
        }

        adapter = new ListAdapter<>(this, R.layout.lv_project_item, list,
                ids,
                new String[]{"projectName", "projectStarttime"},
                new int[]{R.id.item_project_name, R.id.item_project_starttime},
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
            Project p = adapter.getItem(i);

            if (adapter.isChecked(p.getProjectId())) {
                ids.add(p.getProjectId());
                names.add(p.getProjectName());
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