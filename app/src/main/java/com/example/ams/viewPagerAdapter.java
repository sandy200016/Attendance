package com.example.ams;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class viewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragmentlist = new ArrayList<>();
    private final List<String> fragmentlisttitle = new ArrayList<>();


    public viewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentlist.get(position);
    }

    @Override
    public int getCount() {
        return fragmentlisttitle.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentlisttitle.get(position);
    }

    public void AddFragment(Fragment frag, String Title) {
        fragmentlist.add(frag);
        fragmentlisttitle.add(Title);
    }

}
