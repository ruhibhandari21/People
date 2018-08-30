package com.people;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class ApiCalls implements Response.ErrorListener, Response.Listener<JSONObject> {

    ApiCallback apiCallback;
    String TAG = "";

    public interface ApiCallback{
        void onResponse(JSONObject response, String TAG);
        void onError(VolleyError error, String TAG);
    }

    public ApiCalls(ApiCallback callback){
        apiCallback = callback;
    }

    public void initiateRequest(int reqMethod, String url, JSONObject jsonObject, String tag){
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(reqMethod,
                url, jsonObject, this, this);
        TAG = tag;
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        apiCallback.onError(error, TAG);
    }

    @Override
    public void onResponse(JSONObject response) {
        apiCallback.onResponse(response, TAG);
    }

}
