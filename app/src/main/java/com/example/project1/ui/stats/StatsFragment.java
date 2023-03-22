package com.example.project1.ui.stats;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.project1.R;
import android.widget.Button;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.example.project1.databinding.FragmentStatsBinding;

public class StatsFragment extends Fragment {
    private FragmentStatsBinding binding;
    private View root;
    private Button updateStats;
    private Button viewStats;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public static StatsFragment newInstance(String param1, String param2) {
        StatsFragment fragment = new StatsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStatsBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        updateStats = root.findViewById(R.id.update_stats);
        viewStats = root.findViewById(R.id.view_stats);
        updateStats.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getActivity().getApplicationContext(), com.example.project1.updateStats.class);
                startActivity(intent);
            }
        });
        viewStats.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getActivity().getApplicationContext(), com.example.project1.viewStats.class);
                startActivity(intent);
            }
        });
        return root;
    }
}