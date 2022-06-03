package com.example.github_commits.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.github_commits.R;
import com.example.github_commits.domain.UserProfileResponse;
import com.example.github_commits.utils.NetworkUtils;
import com.example.github_commits.viewmodels.CommitViewModel;
import com.example.github_commits.viewmodels.UserProfileViewModel;

public class UserProfileFragment extends Fragment {

    private UserProfileViewModel mViewModel;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.user_fragment, container, false);
        mViewModel = new ViewModelProvider(this).get(UserProfileViewModel.class);
        fetchUserProfileData();
        initCallBack();
        return view;
    }

    private void fetchUserProfileData() {
        if (!NetworkUtils.isNetworkAvailable(getContext())) {
            Toast.makeText(getContext(), "No Internet", Toast.LENGTH_SHORT).show();
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

                }
            });
        }

    }
}
