package com.people.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.people.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.OkHttpClient;


public class OkHttpHandler extends AsyncTask {
    private Activity baseActivity;
    private OnTaskCompleted listener;
    private HashMap<String, String> postDataParams;
    private String TAG, dup_url = "";
    private boolean visible = true;
    private OkHttpClient client = new OkHttpClient();
    private Context mContext;
    public static boolean isWebserviceRunning = false;
    PreferencesManager preferencesManager;

    public OkHttpHandler(Context context, OnTaskCompleted listener, HashMap<String, String> postDataParams, String TAG) {
        this.listener = listener;
        this.TAG = TAG;
        this.mContext = context;
        this.postDataParams = postDataParams;
        preferencesManager = PreferencesManager.getInstance(mContext);
    }

    public OkHttpHandler(OnTaskCompleted listener, String TAG, Activity baseActivity) {
        this.listener = listener;
        this.TAG = TAG;
        this.baseActivity = baseActivity;
    }

    private static String decodeResponse(InputStream is) {
        String result = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            is.close();
            result = sb.toString();

        } catch (Exception EXCEPTION) {
            EXCEPTION.printStackTrace();
        }

        return result;
    }

    public void setProgressFlag(boolean visible) {
        this.visible = visible;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }

    private String uploadData(String requestURL, HashMap<String, String> postDataParams, String TAG) {
        InputStream is = null;
        URL url;
        String response = "";

        try {

            url = new URL(requestURL);
            Log.v(TAG + ":API", url.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);

            if (postDataParams != null) {
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));
                writer.flush();
                writer.close();
                os.close();
            } else {
                conn.setRequestMethod("GET");
            }
            String line;
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            }
            Log.v("Response:" + TAG, response);
        } catch (SocketTimeoutException s) {
            showAlert();
            isWebserviceRunning = false;
            s.printStackTrace();
            return response;


        } catch (Exception e) {
            showAlert();
            isWebserviceRunning = false;
            e.printStackTrace();
            return response;
        }
        Log.v(TAG, "Response: " + response);
        return response;
    }

    @Override
    protected String doInBackground(Object[] objects) {
        dup_url = objects[0].toString();
        return uploadData(objects[0].toString(), postDataParams, TAG);
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        try {
            listener.onTaskCompleted(o.toString(), TAG);
            cancel(true);
        } catch (Exception e) {
            cancel(true);
            Log.e("ReadJSONFeedTask", e.getLocalizedMessage() + "");
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    private void showAlert() {

        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {

                final Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                LayoutInflater lf = (LayoutInflater) (mContext)
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogview = lf.inflate(R.layout.retry_dialog, null);
                TextView title = (TextView) dialogview.findViewById(R.id.title);
                title.setText("Note");
                TextView body = (TextView) dialogview
                        .findViewById(R.id.dialogBody);
                body.setText("Please check your network connection");
                dialog.setContentView(dialogview);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;

                dialog.getWindow().setAttributes(lp);
                dialog.show();
                TextView cancel = (TextView) dialogview
                        .findViewById(R.id.dialogCancel);
                cancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        try {
                            listener.onTaskCompleted("", TAG);
                            isWebserviceRunning = false;
                            cancel(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        dialog.dismiss();

                    }
                });

                TextView retry = (TextView) dialogview
                        .findViewById(R.id.dialogRetry);
                retry.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        new OkHttpHandler(mContext, listener, postDataParams, TAG).execute(dup_url);
                        dialog.dismiss();
                    }
                });

            }

        });
    }


}
