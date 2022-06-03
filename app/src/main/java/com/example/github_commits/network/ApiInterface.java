package com.example.github_commits.network;

import com.example.github_commits.domain.CommitResponse;
import com.example.github_commits.domain.UserProfileResponse;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {



   // @Headers({AppConfig.ACCEPT_APPLICATION_JSON, AppConfig.CONTENT_TYPE_APPLICATION_JSON})
    @GET("repos/flutter/flutter/commits")
    Call<List<CommitResponse>> getCommits();

    @GET("users/saeedsh92")
    Call<UserProfileResponse> getUserProfile();
}
