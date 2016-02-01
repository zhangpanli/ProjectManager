package com.self.projectmanager.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.self.projectmanager.R;
import com.self.projectmanager.fragment.AdvanceRecordListFragment;
import com.self.projectmanager.fragment.MemberDetailFragment;
import com.self.projectmanager.fragment.ProjectMainFragment;
import com.self.projectmanager.fragment.SignInRecordListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 攀礼 on 2016/1/9.
 */
public class MemberDetailActivity extends FragmentActivity {

    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            return;
        }

        setContentView(R.layout.member_detail_main);
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
                        ((TextView) findViewById(R.id.btn_tab_sign_in)).setTextColor(Color.RED);
                        break;
                    case 3:
                        ((TextView) findViewById(R.id.btn_tab_advance)).setTextColor(Color.RED);
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
        ((TextView) findViewById(R.id.btn_tab_sign_in)).setTextColor(defaultColor);
        ((TextView) findViewById(R.id.btn_tab_advance)).setTextColor(defaultColor);
    }

    private int defaultColor;

    private void initView() {
        MemberDetailFragment tab01 = new MemberDetailFragment();
        ProjectMainFragment tab02 = new ProjectMainFragment();
        SignInRecordListFragment tab03 = new SignInRecordListFragment();
        AdvanceRecordListFragment tab04 = new AdvanceRecordListFragment();
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
            case R.id.btn_tab_sign_in:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.btn_tab_advance:
                mViewPager.setCurrentItem(3);
                break;
        }
    }

    public void close(View view) {
        finish();
    }

}
