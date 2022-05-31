package com.example.github_commits.network;

import com.example.github_commits.domain.Example;
import com.example.github_commits.domain.UserProfile;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {



   // @Headers({AppConfig.ACCEPT_APPLICATION_JSON, AppConfig.CONTENT_TYPE_APPLICATION_JSON})
    @GET("repos/flutter/flutter/commits")
    Call<List<Example>> getCommits();

    @GET("repos/flutter/flutter/commits")
    Call<UserProfile> getUserProfile();
}
