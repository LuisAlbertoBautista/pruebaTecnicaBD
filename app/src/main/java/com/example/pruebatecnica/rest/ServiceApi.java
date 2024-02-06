package com.example.pruebatecnica.rest;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AlertDialog;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.pruebatecnica.R;

public class ServiceApi {


    private Context context;
    private Boolean showLoading = true;
    private ConnectionModel connectionModel;

    private AlertDialog dialog;

    private ResponseHttp delegate;


    private static final String TAG = "consola";

    public interface ResponseHttp {
        void processFinish(int code, JSONObject data) throws Exception;
        void onFail() throws JSONException;
    }



    // SWIPE DETALLE
    public ServiceApi(Context context, Boolean showLoading){
        this.context = context;
        this.showLoading = showLoading;

        connectionModel = SingleTon.setSingleton();
        delegate = (ResponseHttp) context;

        if (showLoading)
            showLoading();
    }



    private void showLoading(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        final View customLayout = inflater.inflate(R.layout.dialog_loading, null);
        builder.setView(customLayout);
        dialog = builder.create();

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }



    public void startConnection(final String url){
        if (showLoading)
            dialog.show();

        final Callback<ResponseBody> callback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (showLoading){
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
                String string = null;
                JSONObject data = null;
                int code = response.code();
                try {
                    if (response.body() != null){
                        string = response.body().string();
                        JSONObject obj_data = new JSONObject(string);
                        data = obj_data;
                    }else{
                        String error_message = response.errorBody().string();
                            data = new JSONObject(error_message);
                    }

                    delegate.processFinish(code,data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (showLoading){
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
                try {
                    delegate.onFail();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        connectionModel.simpleGet(url).enqueue(callback);
    }



}
