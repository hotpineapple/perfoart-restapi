package com.jeonsee.jeonseerestapi.service;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidParameterException;

@Service
public class AuthService {
    private static final OkHttpClient CLIENT = new OkHttpClient();
    @Value("${auth_server_uri}")
    String auth_server_uri;

    public String getEmail(String token) throws IOException {

        String email = CLIENT.newCall(new Request.Builder().get()
                .url(auth_server_uri)
                .addHeader("Authorization", "Bearer " + token)
                .build()).execute().body().string();
        if(email == null) throw new InvalidParameterException();
        return email;

    }
}
