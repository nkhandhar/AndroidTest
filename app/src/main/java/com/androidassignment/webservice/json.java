package com.androidassignment.webservice;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class json extends JsonRequest<JSONObject> {

    public json(String url, JSONObject jsonObject, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(Method.POST,url,(jsonObject == null) ? null : jsonObject.toString(),listener,errorListener);
        setRetryPolicy(new DefaultRetryPolicy(6000,1,1));
        Log.d("Request",url + ((jsonObject == null) ? null : jsonObject.toString()));
    }
    public json(String url,Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(Method.GET,url,null,listener,errorListener);
        setRetryPolicy(new DefaultRetryPolicy(6000,1,1));
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }
}
