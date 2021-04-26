package com.example.blanjaumkm.login.service;

import androidx.annotation.Nullable;

import com.example.blanjaumkm.login.model.Token;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;

public class CustomAuthenticator implements Authenticator {

    private TokenManager tokenManager;
    private static CustomAuthenticator INSTANCE;

    public CustomAuthenticator(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    static synchronized CustomAuthenticator getInstance(TokenManager tokenManager) {
        if (INSTANCE == null) {
            INSTANCE = new CustomAuthenticator(tokenManager);
        }
        return INSTANCE;
    }

    @Nullable
    @Override
    public Request authenticate(Route route, Response response) throws IOException {

        if (responseCount(response) >= 3) {
            return null;
        }

        Token token = tokenManager.getToken();

        ApiService service = RetrofitBuilder.createService(ApiService.class);
        Call<Token> call = service.refresh(token.getApi_token_refresh() + "a");
        retrofit2.Response<Token> res = call.execute();

        if (res.isSuccessful()) {
            Token newToken = res.body();
            tokenManager.saveToken(newToken);

            return response.request().newBuilder().header("Authorization", "Bearer " + res.body().getToken()).build();
        }else {
            return null;
        }
    }

    private int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }

}
