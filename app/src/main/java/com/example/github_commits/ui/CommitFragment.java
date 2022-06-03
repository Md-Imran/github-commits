package com.example.github_commits.ui;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.github_commits.R;
import com.example.github_commits.adapter.CommitAdapter;
import com.example.github_commits.databinding.CommitFragmentBinding;
import com.example.github_commits.domain.CommitResponse;
import com.example.github_commits.utils.NetworkUtils;
import com.example.github_commits.viewmodels.CommitViewModel;


public class CommitFragment extends Fragment implements CommitAdapter.ItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private CommitFragmentBinding mBinding;
    private CommitViewModel mViewModel;
    private CommitAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.commit_fragment, container, false);
        mViewModel = new ViewModelProvider(this).get(CommitViewModel.class);

        initView();
        fetchCommit();
        initCallBack();

        return mBinding.getRoot();
    }

    private void initView() {
        mBinding.swipeRefresh.setOnRefreshListener(this);
        mBinding.swipeRefresh.setRefreshing(true);
        mAdapter = new CommitAdapter(getActivity(), this);
        mBinding.recyclerViewCommit.setAdapter(mAdapter);


    }

    private void fetchCommit() {
        if (!NetworkUtils.isNetworkAvailable(getContext())) {
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            mBinding.swipeRefresh.setRefreshing(false);
            return;
        }
        mViewModel.getCommits(10);
    }

    private void initCallBack() {
        if (getActivity() == null) {
            return;
        }
        if (getActivity() != null) {
            mViewModel.commitLiveDate.observe(getActivity(), CommitResponse -> {
                if (CommitResponse != null && !CommitResponse.isEmpty()) {
                    if (mAdapter != null) {
                        mAdapter.clear();
                        mBinding.swipeRefresh.setRefreshing(false);
                        for (int i = 0; i < CommitResponse.size(); i++) {
                            CommitResponse item = CommitResponse.get(i);
                            String authorName = item.getCommit().getAuthor().getName();
                            if (authorName.contains("g") || authorName.contains("x")) {
                                // don't add to adapter
                            } else {
                                mAdapter.addItem(item);
                            }
                        }

                    }
                }
            });
        }

    }

    @Override
    public void onGetItem(CommitResponse item) {

    }

    @Override
    public void onRefresh() {
        fetchCommit();

    }
}
