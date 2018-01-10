package com.android.cy.scrollviewpager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ScrollPagerActivity extends FragmentActivity implements View.OnClickListener {

    CYScrollView scrollView;
    RadioGroup rg_snapbar_top, rg_snapbar;
    RadioButton btn_tab1_top, btn_tab2_top, btn_tab3_top, btn_tab1, btn_tab2, btn_tab3;

    CYViewPager vp;
    List<View> views;
    MyAdapter myAdapter;
    private LayoutInflater mInflater;

    int snapbar_y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_tab);
        mInflater = LayoutInflater.from(this);
        initView();
        snapbar_y = dip2px(this, 180);//注意你的snapbar以上部分的高度值，将其转换为px（最好设置为固定值，如果非固定，则要动态计算高度）
    }

    private void initView() {
        scrollView = (CYScrollView) findViewById(R.id.scrollView);

        rg_snapbar_top = (RadioGroup) findViewById(R.id.rg_snapbar_top);
        btn_tab1_top = (RadioButton) findViewById(R.id.btn_tab1_top);
        btn_tab2_top = (RadioButton) findViewById(R.id.btn_tab2_top);
        btn_tab3_top = (RadioButton) findViewById(R.id.btn_tab3_top);

        rg_snapbar = (RadioGroup) findViewById(R.id.rg_snapbar);
        btn_tab1 = (RadioButton) findViewById(R.id.btn_tab1);
        btn_tab2 = (RadioButton) findViewById(R.id.btn_tab2);
        btn_tab3 = (RadioButton) findViewById(R.id.btn_tab3);

        btn_tab1.setOnClickListener(this);
        btn_tab1_top.setOnClickListener(this);
        btn_tab2.setOnClickListener(this);
        btn_tab2_top.setOnClickListener(this);
        btn_tab3.setOnClickListener(this);
        btn_tab3_top.setOnClickListener(this);

        vp = (CYViewPager) findViewById(R.id.vp);

        scrollView.setScrollViewListener(new CYScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(CYScrollView scrollView, int x, int y, int oldx, int oldy) {
                if (y >= snapbar_y) {
                    rg_snapbar_top.setVisibility(View.VISIBLE);
                } else {
                    rg_snapbar_top.setVisibility(View.GONE);
                }
            }
        });

        initViewPaper();

    }

    /**
     * 初始化viewpaper
     */
    private void initViewPaper() {
        views = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            View view = mInflater.inflate(R.layout.item_tab, null);
            LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll);
            for (int j = 0; j < 20; j++) {
                ll.addView(getView(i));
            }
            views.add(view);
        }
        myAdapter = new MyAdapter();
        vp.setAdapter(myAdapter);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        btn_tab1.setChecked(true);
                        btn_tab1_top.setChecked(true);
                        break;
                    case 1:
                        btn_tab2.setChecked(true);
                        btn_tab2_top.setChecked(true);
                        break;
                    case 2:
                        btn_tab3.setChecked(true);
                        btn_tab3_top.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     * 填充viewpager的itmeview
     *
     * @return
     */
    private View getView(int i) {
        View view = mInflater.inflate(R.layout.item_scroll_pager, null);
        //填充数据//
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText("标题" + (i + 1));
        return view;
    }

    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(views.get(arg1));
            return views.get(arg1);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_tab1:
            case R.id.btn_tab1_top:
                vp.setCurrentItem(0);
                break;
            case R.id.btn_tab2:
            case R.id.btn_tab2_top:
                vp.setCurrentItem(1);
                break;
            case R.id.btn_tab3:
            case R.id.btn_tab3_top:
                vp.setCurrentItem(2);
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
