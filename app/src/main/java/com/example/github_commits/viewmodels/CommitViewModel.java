package com.example.github_commits.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.github_commits.domain.CommitResponse;
import com.example.github_commits.helper.TagHelper;
import com.example.github_commits.network.ApiInterface;
import com.example.github_commits.network.RetrofitClientInstance;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommitViewModel extends ViewModel {


    public MutableLiveData<List<CommitResponse>> commitLiveDate = new MutableLiveData<>();

    public void getCommits(int count) {
        ApiInterface service = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
        // HashMap<String, String> map = new HashMap<>();
        //map.put("Accept", "application/json");

        Call<List<CommitResponse>> call = service.getCommits(count);
        call.enqueue(new Callback<List<CommitResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<CommitResponse>> call, @NonNull Response<List<CommitResponse>> response) {
                if (response.isSuccessful()) {
                    List<CommitResponse> commitResponses = response.body();
                    commitLiveDate.postValue(commitResponses);
                } else if (response.code() == 400) {
                    Log.e(TagHelper.COMMIT_TAG, "Bad Request");
                    commitLiveDate.postValue(null);
                } else if (response.code() == 404) {
                    Log.e(TagHelper.COMMIT_TAG, "Resource not found");
                    commitLiveDate.postValue(null);
                } else if (response.code() == 409) {
                    Log.e(TagHelper.COMMIT_TAG, "Conflict");
                    commitLiveDate.postValue(null);
                } else if (response.code() == 500) {
                    Log.e(TagHelper.COMMIT_TAG, "Internal Error");
                    commitLiveDate.postValue(null);
                }


            }

            @Override
            public void onFailure(@NonNull Call<List<CommitResponse>> call, @NonNull Throwable t) {
                Log.e(TagHelper.COMMIT_TAG, "commit retrieve error: " + t.getMessage());
                commitLiveDate.postValue(null);
            }
        });
    }
}
