package com.example.smartcard.Adapters.Parent.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.smartcard.Adapters.Parent.Adpater.TabAdapter;
import com.example.smartcard.R;
import com.google.android.material.tabs.TabLayout;

public class BalanceFragment extends Fragment {
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.balance_fragment, container, false);
        viewPager = view.findViewById(R.id.viewPagerBalance);
        tabLayout = view.findViewById(R.id.tabLayoutBalance);
        adapter = new TabAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new Withdrawal(), "WITHDRAWAL");
        adapter.addFragment(new Deposits(), "DEPOSITS");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;

    }
}