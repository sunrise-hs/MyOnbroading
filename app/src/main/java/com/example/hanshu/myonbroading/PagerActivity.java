package com.example.hanshu.myonbroading;

import android.animation.ArgbEvaluator;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class PagerActivity extends AppCompatActivity {
    CoordinatorLayout co;
    ViewPager mViewPager;
    Button mSkip, mFinish;
    ImageButton mNext;
    ImageView left, main, right;
    ImageView imageView[];
    int mPage = 0;
    public static final String TAG = "aaa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_layout);

        PagerFragmentAdapter pfs = new PagerFragmentAdapter(getSupportFragmentManager());
        co = (CoordinatorLayout) findViewById(R.id.main_content);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mSkip = (Button) findViewById(R.id.skip);
        mFinish = (Button) findViewById(R.id.finish);
        mNext = (ImageButton) findViewById(R.id.next);
        left= (ImageView) findViewById(R.id.leftqq);
        main= (ImageView) findViewById(R.id.mainqq);
        right= (ImageView) findViewById(R.id.rightqq);
        imageView = new ImageView[]{left, main, right};

        final int color1 = ContextCompat.getColor(this, R.color.cyan);
        final int color2 = ContextCompat.getColor(this, R.color.orange);
        final int color3 = ContextCompat.getColor(this, R.color.green);

        final int[] colorList = new int[]{color1, color2, color3};
        final ArgbEvaluator evaluator = new ArgbEvaluator();
        mViewPager.setAdapter(pfs);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int colorUpdate = (Integer) evaluator.evaluate(positionOffset, colorList[position],
                        colorList[position == 2 ? position : position + 1]);
                //最后设置渐变背景色给布局
                mViewPager.setBackgroundColor(colorUpdate);
            }

            @Override
            public void onPageSelected(int position) {
                Log.i(TAG, "onPageSelected: %%%%%%%%%%%%%%%%%%%%"+position);
                mPage=position;
                updateItem(mPage);
                switch (position) {
                    case 0:
                        mViewPager.setBackgroundColor(color1);
                        break;
                    case 1:
                        mViewPager.setBackgroundColor(color2);
                        break;
                    case 2:
                        mViewPager.setBackgroundColor(color3);
                        break;
                }
                mNext.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
                mFinish.setVisibility(position == 2 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPage += 1;
                mViewPager.setCurrentItem(mPage, true);
            }
        });
        mFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    void updateItem(int position) {
        imageView[position].setBackgroundResource(R.drawable.indicator_selected);

        for (int i = 0; i < imageView.length; i++) {
            if (position != i) {

                imageView[i].setBackgroundResource(R.drawable.indicator_unselected);
            }
        }
//        for (int i = 0; i < imageView.length; i++) {
//            imageView[i].setBackgroundResource(
//                    i == position ? R.drawable.indicator_selected : R.drawable.indicator_unselected
//            );
//        }
    }
    public static class PagerFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";
        ImageView img;

        int[] bgs = new int[]{R.drawable.ic_flight_24dp, R.drawable.ic_mail_24dp, R.drawable.ic_explore_24dp};

        public static PagerFragment newStance(int position) {
            Log.i(TAG, "newStance:&&&&&&&&&&&&&&&&&&&&&&&&&& " + position);
            PagerFragment pf = new PagerFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(ARG_SECTION_NUMBER, position);
            pf.setArguments(bundle);
            return pf;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_layout, container, false);
            TextView tv = (TextView) view.findViewById(R.id.tv_top);
            tv.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            ImageView img = (ImageView) view.findViewById(R.id.picture);
            Log.i(TAG, "onCreateView: @@@@@@@@@@@@@@@" + getArguments().getInt(ARG_SECTION_NUMBER));
            img.setBackgroundResource(bgs[getArguments().getInt(ARG_SECTION_NUMBER)]);
            return view;
        }
    }

    public static class PagerFragmentAdapter extends FragmentPagerAdapter {
        public PagerFragmentAdapter(FragmentManager fg) {
            super(fg);
        }

        @Override
        public Fragment getItem(int position) {
            Log.i(TAG, "getItem: **********************" + position);
            return PagerFragment.newStance(position);
        }


        @Override
        public int getCount() {
            return 3;
        }

    }


}
