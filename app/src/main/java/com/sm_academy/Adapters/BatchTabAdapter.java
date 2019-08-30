package com.sm_academy.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sm_academy.Fragments.CompletedSessionFragment;
import com.sm_academy.Fragments.OngoingBatchFragment;
import com.sm_academy.Fragments.UpcomingBatchFragment;
import com.sm_academy.Fragments.UpcomingSessionFragment;

public class BatchTabAdapter extends FragmentPagerAdapter {

    private final int tabCount;

    public BatchTabAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new UpcomingBatchFragment();
            case 1:
                return new OngoingBatchFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return this.tabCount;
    }
}
