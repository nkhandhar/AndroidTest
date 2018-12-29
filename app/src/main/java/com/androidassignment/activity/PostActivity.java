package com.androidassignment.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.androidassignment.R;
import com.androidassignment.adapter.PostAdapter;
import com.androidassignment.model.PostData;
import com.androidassignment.utils.ConnectionDetector;
import com.androidassignment.utils.ProgDialog;
import com.androidassignment.webservice.API;
import com.androidassignment.webservice.AppController;
import com.androidassignment.webservice.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PostActivity extends AppCompatActivity implements PostAdapter.retrive {

    Toolbar toolbar;
    TextView selectedNum;
    RecyclerView postRecycler;

    ConnectionDetector connectionDetector;
    ProgDialog progDialog;
    int page = 1, count = 0, scrollToPos = 0;

    ArrayList<PostData> postDataArrayList;

    SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    SimpleDateFormat output = new SimpleDateFormat("dd MMM yyyy hh:mm a");
    String totalpage;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        connectionDetector = new ConnectionDetector(this);
        progDialog = new ProgDialog(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        selectedNum = toolbar.findViewById(R.id.selectedNum);

        postRecycler = findViewById(R.id.postRecycler);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshlay);
        postDataArrayList = new ArrayList<>();
        getPosts();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                count = 0;
                page = 1;
                postDataArrayList = new ArrayList<>();
                getPosts();
                selectedNum.setText("");
            }
        });
    }

    private void getPosts() {

        if (connectionDetector.isNetworkAvailable()) {
            if (page == 1)
                progDialog.ProgressDialogStart();

            json request = new json(API.MAIN + String.valueOf(page), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (page == 1) {
                        if (swipeRefreshLayout.isRefreshing())
                            swipeRefreshLayout.setRefreshing(false);
                        progDialog.ProgressDialogStop();
                    }


                    try {
                        if (response != null && response.length() != 0) {

                            if (response.has("hits")) {
                                JSONArray hits = response.getJSONArray("hits");
                                totalpage = response.getString("nbPages");
                                if (hits != null && hits.length() != 0) {
                                    for (int i = 0; i < hits.length(); i++) {
                                        JSONObject postObject = hits.getJSONObject(i);

                                        String created_at = postObject.getString("created_at");
                                        String title = postObject.getString("title");

                                        Date d = input.parse(created_at);

                                        postDataArrayList.add(new PostData(output.format(d), title, false));
                                    }
                                }

                                postRecycler.setAdapter(new PostAdapter(PostActivity.this, postDataArrayList));
                                postRecycler.setLayoutManager(new LinearLayoutManager(PostActivity.this));
                                if (page > 1) {
                                    postRecycler.scrollToPosition(scrollToPos);
                                }
                            } else {
                                Toast.makeText(PostActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } else
                            Toast.makeText(PostActivity.this, "Please try again...", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (page == 1) {
                        if (swipeRefreshLayout.isRefreshing())
                            swipeRefreshLayout.setRefreshing(false);
                        progDialog.ProgressDialogStop();
                    }
                    Toast.makeText(PostActivity.this, "Please try again...", Toast.LENGTH_SHORT).show();
                }
            });
            AppController.getInstance().addToRequestQueue(request);

        } else
            connectionDetector.error_Dialog();

    }

    @Override
    public void getData() {
        page++;
        scrollToPos = postDataArrayList.size()-1;
        if (page < Integer.parseInt(totalpage))
            getPosts();
        else
            Toast.makeText(this, "No more data available...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getSelected(boolean b) {
        if (b) {
            count++;
            selectedNum.setText(String.valueOf(count));
        } else {
            count--;
            if (count != 0) {
                selectedNum.setText(String.valueOf(count));
            } else
                selectedNum.setText("");
        }
    }
}