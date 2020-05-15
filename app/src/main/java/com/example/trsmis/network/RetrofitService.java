package com.example.trsmis.network;

import com.example.trsmis.model.TrsmisReqModel;
import com.example.trsmis.model.TrsmisResModel;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitService {

    /**
     * 요청사항
     */
    @POST("trsmis/list")
    Single<Response<TrsmisResModel>> trsmisCall(@Body TrsmisReqModel reqModel);

}
