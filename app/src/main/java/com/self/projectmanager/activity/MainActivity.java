package com.self.projectmanager.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.self.projectmanager.R;
import com.self.projectmanager.dao.SimpleDao;
import com.self.projectmanager.db.DatabaseUtils;
import com.self.projectmanager.fragment.MainTab01;
import com.self.projectmanager.fragment.MainTab04;
import com.self.projectmanager.fragment.MemberMainFragment;
import com.self.projectmanager.fragment.ProjectMainFragment;
import com.self.projectmanager.model.Member;
import com.self.projectmanager.model.Project;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseUtils.setContext(getApplicationContext());

        Log.d("members", String.valueOf(SimpleDao.listMember(new Member())));

        if (savedInstanceState != null) {
            return;
        }

        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);


        initView();


        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return mFragments.get(arg0);
            }
        };

        mViewPager.setAdapter(mAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            private int currentIndex;

            @Override
            public void onPageSelected(int position) {
                resetTabBtn();
                switch (position) {
                    case 0:
                        ((TextView) findViewById(R.id.btn_tab_main)).setTextColor(Color.RED);
                        break;
                    case 1:
                        ((TextView) findViewById(R.id.btn_tab_project)).setTextColor(Color.RED);
                        break;
                    case 2:
                        ((TextView) findViewById(R.id.btn_tab_member)).setTextColor(Color.RED);
                        break;
                    case 3:
                        ((TextView) findViewById(R.id.btn_tab_setting)).setTextColor(Color.RED);
                        break;
                }

                currentIndex = position;
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    protected void resetTabBtn() {
        ((TextView) findViewById(R.id.btn_tab_main)).setTextColor(defaultColor);
        ((TextView) findViewById(R.id.btn_tab_project)).setTextColor(defaultColor);
        ((TextView) findViewById(R.id.btn_tab_member)).setTextColor(defaultColor);
        ((TextView) findViewById(R.id.btn_tab_setting)).setTextColor(defaultColor);
    }

    private int defaultColor;

    private void initView() {
        MainTab01 tab01 = new MainTab01();
        ProjectMainFragment tab02 = new ProjectMainFragment();
        MemberMainFragment tab03 = new MemberMainFragment();
        MainTab04 tab04 = new MainTab04();
        mFragments.add(tab01);
        mFragments.add(tab02);
        mFragments.add(tab03);
        mFragments.add(tab04);

        defaultColor = ((TextView) findViewById(R.id.btn_tab_main)).getCurrentTextColor();
        ((TextView) findViewById(R.id.btn_tab_main)).setTextColor(Color.RED);
    }

    public void clickTitle(View view) {
        switch (view.getId()) {
            case R.id.btn_tab_main:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.btn_tab_project:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.btn_tab_member:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.btn_tab_setting:
                mViewPager.setCurrentItem(3);
                break;
        }
    }

    public void clickViewProject(View view) {
        Intent intent = new Intent(this, ProjectAddActivity.class);
        startActivity(intent);
    }

    public void clickViewMember(View view) {
        Intent intent = new Intent(this, MemberAddActivity.class);
        startActivity(intent);
    }

    public void signIn(View view) {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    public void advance(View view) {
        Intent intent = new Intent(this, AdvanceActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("intent", String.valueOf(data));

        if (data == null) {
            return;
        }

        Bundle b = data.getExtras();

        List<String> names = b.getStringArrayList("names");

        Log.d("names", String.valueOf(names));

        Toast.makeText(this, names.toString(), Toast.LENGTH_SHORT);
    }
}
