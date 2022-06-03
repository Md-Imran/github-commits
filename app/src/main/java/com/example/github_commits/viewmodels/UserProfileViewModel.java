package com.example.github_commits.viewmodels;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.github_commits.domain.UserProfileResponse;
import com.example.github_commits.helper.TagHelper;
import com.example.github_commits.network.ApiInterface;
import com.example.github_commits.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileViewModel extends ViewModel {
   public MutableLiveData<UserProfileResponse> userProfileLiveData = new MutableLiveData<>();

    public void getUserProfile() {
        ApiInterface service = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
        // HashMap<String, String> map = new HashMap<>();
        //map.put("Accept", "application/json");

        Call<UserProfileResponse> call = service.getUserProfile();
        call.enqueue(new Callback<UserProfileResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserProfileResponse> call, @NonNull Response<UserProfileResponse> response) {
                if (response.isSuccessful()) {
                    UserProfileResponse userProfileResponse = response.body();
                    userProfileLiveData.postValue(userProfileResponse);
                } else if (response.code() == 404) {
                    Log.e(TagHelper.USER_PROFILE_TAG, "Resource not found");
                    userProfileLiveData.postValue(null);
                }
                else {
                    Log.e(TagHelper.USER_PROFILE_TAG, "No data found");
                    userProfileLiveData.postValue(null);
                }

            }

            @Override
            public void onFailure(@NonNull Call<UserProfileResponse> call, @NonNull Throwable t) {
                Log.e(TagHelper.USER_PROFILE_TAG, "user data retrieve error: " + t.getMessage());
                userProfileLiveData.postValue(null);
            }
        });
    }

}
