package com.example.github_commits.ui;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.github_commits.R;
import com.example.github_commits.databinding.UserFragmentBinding;
import com.example.github_commits.domain.UserProfileResponse;
import com.example.github_commits.utils.NetworkUtils;
import com.example.github_commits.viewmodels.UserProfileViewModel;

public class UserProfileFragment extends Fragment {


    private UserFragmentBinding mBinding;
    private UserProfileViewModel mViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.user_fragment, container, false);
        mViewModel = new ViewModelProvider(this).get(UserProfileViewModel.class);
        fetchUserProfileData();
        initCallBack();
        return mBinding.getRoot();
    }

    private void fetchUserProfileData() {
        if (!NetworkUtils.isNetworkAvailable(getContext())) {
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            mBinding.progressBar.setVisibility(View.GONE);
            return;
        }
        mViewModel.getUserProfile();
    }

    private void initCallBack() {
        if (getActivity() == null) {
            return;
        }
        if (getActivity() != null) {
            mViewModel.userProfileLiveData.observe(getActivity(), UserProfileResponse -> {
                if (UserProfileResponse != null) {
                    mBinding.progressBar.setVisibility(View.GONE);
                    setData(UserProfileResponse);
                } else {
                    mBinding.progressBar.setVisibility(View.GONE);
                }
            });
        }

    }

    @SuppressLint("SetTextI18n")
    private void setData(UserProfileResponse UserProfileResponse) {

        mBinding.tvUserName.setText(UserProfileResponse.getName());
        mBinding.tvShortName.setText("@" + UserProfileResponse.getLogin());
        mBinding.tvBio.setText("Bio: " + UserProfileResponse.getBio());
        mBinding.tvPublicRepo.setText("Public Repos: " + UserProfileResponse.getPublicRepos().toString());
        mBinding.tvPublicGigs.setText("Public Gigs: " + UserProfileResponse.getPublicGists().toString());
        mBinding.tvPrivateRepos.setText("Private Repo : 5");
        Glide.with(getContext())
                .load(UserProfileResponse.getAvatarUrl())
                .centerCrop()
                .transform(new RoundedCorners(600))
                .into(mBinding.avatar);
    }
}
