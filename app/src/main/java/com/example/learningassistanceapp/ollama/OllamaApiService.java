package com.example.learningassistanceapp.ollama;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OllamaApiService {

    @POST("api/generate")
    Call<OllamaResponse> generateResponse(@Body OllamaRequest request);
}