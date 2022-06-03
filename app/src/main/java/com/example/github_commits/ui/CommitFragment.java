package com.example.github_commits.ui;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.github_commits.R;
import com.example.github_commits.domain.CommitResponse;
import com.example.github_commits.utils.NetworkUtils;
import com.example.github_commits.viewmodels.CommitViewModel;


public class CommitFragment extends Fragment {

    private View view;
    private CommitViewModel mViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.commit_fragment, container, false);

        mViewModel = new ViewModelProvider(this).get(CommitViewModel.class);


        fetchCommit();
        initCallBack();

        return view;
    }

    private void fetchCommit() {
        if (!NetworkUtils.isNetworkAvailable(getContext())) {
            Toast.makeText(getContext(), "No Internet", Toast.LENGTH_SHORT).show();
            return;
        }
        mViewModel.getCommits();
    }

    private void initCallBack() {
        if (getActivity() == null) {
            return;
        }
        if (getActivity() != null) {
            mViewModel.commitLiveDate.observe(getActivity(), CommitResponse -> {
                if (CommitResponse != null && !CommitResponse.isEmpty()) {

                }
            });
        }

    }
}
