package com.example.fragmentbasic;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

//ListView 처럼 실제 보여질 모델은 어댑터가 관리한다!!!
public class MyPageAdapter extends FragmentStateAdapter {

    Fragment[] fragments=new Fragment[3];

    public MyPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);

        fragments[0] = new HomeFragment();
        fragments[1] = new BoardFragment();
        fragments[2] = new BlogFragment();
    }

    public Fragment createFragment(int position) {
        return fragments[position];
    }

    //총 페이지 수
    public int getItemCount() {
        return fragments.length;
    }
}
