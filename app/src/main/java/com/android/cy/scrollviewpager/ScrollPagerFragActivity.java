package com.android.cy.scrollviewpager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhxh on 2018/1/29.
 */

public class ScrollPagerFragActivity extends FragmentActivity implements View.OnClickListener {
    CYScrollView scrollView;
    RadioGroup rgTabTopLayout;
    RadioButton btn_tab1_top, btn_tab2_top, btn_tab3_top;
    RadioButton btn_tab1_bottom, btn_tab2_bottom, btn_tab3_bottom;

    View topLayout;

    CYViewPager cyViewPager;
    List<Fragment> fragmentList;
    MyAdapter myAdapter;
    private LayoutInflater mInflater;

    int tabHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_tab);
        mInflater = LayoutInflater.from(this);
        initView();
        tabHeight = dip2px(this, 180);//注意你的snapbar以上部分的高度值，将其转换为px（最好设置为固定值，如果非固定，则要动态计算高度）
    }

    private void initView() {
        scrollView = (CYScrollView) findViewById(R.id.scrollView);

        rgTabTopLayout = (RadioGroup) findViewById(R.id.rgTabTopLayout);
        btn_tab1_top = (RadioButton) findViewById(R.id.btn_tab1_top);
        btn_tab2_top = (RadioButton) findViewById(R.id.btn_tab2_top);
        btn_tab3_top = (RadioButton) findViewById(R.id.btn_tab3_top);

        btn_tab1_bottom = (RadioButton) findViewById(R.id.btn_tab1);
        btn_tab2_bottom = (RadioButton) findViewById(R.id.btn_tab2);
        btn_tab3_bottom = (RadioButton) findViewById(R.id.btn_tab3);

        topLayout = findViewById(R.id.topLayout);

        btn_tab1_bottom.setOnClickListener(this);
        btn_tab1_top.setOnClickListener(this);
        btn_tab2_bottom.setOnClickListener(this);
        btn_tab2_top.setOnClickListener(this);
        btn_tab3_bottom.setOnClickListener(this);
        btn_tab3_top.setOnClickListener(this);

        cyViewPager = (CYViewPager) findViewById(R.id.vp);

        scrollView.setScrollViewListener(new CYScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(CYScrollView scrollView, int x, int y, int oldx, int oldy) {
                if (y >= tabHeight) {
                    topLayout.setVisibility(View.VISIBLE);
                } else {
                    topLayout.setVisibility(View.GONE);
                }
            }
        });

        initViewPaper();

    }

    /**
     * 初始化viewPaper
     */
    private void initViewPaper() {
        fragmentList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ItemFragment itemFragment = ItemFragment.newInstance(i + 1);
            fragmentList.add(itemFragment);
        }
        myAdapter = new MyAdapter(getSupportFragmentManager(), fragmentList);
        cyViewPager.setAdapter(myAdapter);
        cyViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        btn_tab1_bottom.setChecked(true);
                        btn_tab1_top.setChecked(true);

                        btn_tab2_bottom.setChecked(false);
                        btn_tab2_top.setChecked(false);

                        btn_tab3_bottom.setChecked(false);
                        btn_tab3_top.setChecked(false);
                        break;
                    case 1:
                        btn_tab1_bottom.setChecked(false);
                        btn_tab1_top.setChecked(false);

                        btn_tab2_bottom.setChecked(true);
                        btn_tab2_top.setChecked(true);

                        btn_tab3_bottom.setChecked(false);
                        btn_tab3_top.setChecked(false);
                        break;
                    case 2:
                        btn_tab1_bottom.setChecked(false);
                        btn_tab1_top.setChecked(false);

                        btn_tab2_bottom.setChecked(false);
                        btn_tab2_top.setChecked(false);

                        btn_tab3_bottom.setChecked(true);
                        btn_tab3_top.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private View getView(int i) {
        View view = mInflater.inflate(R.layout.item_scroll_pager, null);
        //填充数据//
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText("我是标题" + (i + 1));
        return view;
    }

    private class MyAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragments;

        public MyAdapter(FragmentManager fm, List<Fragment> mFragments) {
            super(fm);
            this.mFragments = mFragments;

        }

        @Override
        public int getCount() {
            return mFragments.size();
        }


        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_tab1:
            case R.id.btn_tab1_top:
                cyViewPager.setCurrentItem(0);
                break;
            case R.id.btn_tab2:
            case R.id.btn_tab2_top:
                cyViewPager.setCurrentItem(1);
                break;
            case R.id.btn_tab3:
            case R.id.btn_tab3_top:
                cyViewPager.setCurrentItem(2);
                break;
        }
    }


    /**
     * 将dp转px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
