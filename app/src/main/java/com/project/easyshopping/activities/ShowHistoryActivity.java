package com.project.easyshopping.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.easyshopping.R;
import com.project.easyshopping.data.model.CustomHistoryAdapter;
import com.project.easyshopping.data.model.HistoryRowItem;
import com.project.easyshopping.dto.SearchHistoryDTO;

import java.util.ArrayList;
import java.util.Date;

public class ShowHistoryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    private static String TAG = "ShowHistoryActivity";
    ArrayList<HistoryRowItem> historyRowItems;
    CustomHistoryAdapter customHistoryAdapter;
    Toolbar toolbar;
    ProgressDialog loadingProgressDialog;
    TextView  noHistoryFound;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        toolbar = findViewById(R.id.toolbar);
        noHistoryFound = findViewById(R.id.no_history_found);
        noHistoryFound.setVisibility(View.INVISIBLE);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        listView = findViewById(R.id.history_list);
        listView.setOnItemClickListener(ShowHistoryActivity.this);
        historyRowItems = new ArrayList<>();
        loadingProgressDialog = new ProgressDialog(this);
        loadingProgressDialog.setTitle("Fetching History");
        loadingProgressDialog.setMessage("Please wait...");
        loadingProgressDialog.setCanceledOnTouchOutside(true);
        loadingProgressDialog.show();
        fetchUserHistory();
    }

    private void fetchUserHistory() {
        // Get a reference to our posts
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("users_search_history");
        // Attach a listener to read the data at our posts reference
        databaseReference.child(FirebaseAuth.getInstance().getUid()).orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount() > 0){
                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        HistoryRowItem historyRowItem = dataSnapshot1.getValue(HistoryRowItem.class);
                        historyRowItem.setImage(R.drawable.easyshoplogoasset);
                        historyRowItems.add(historyRowItem);
                    }
                    customHistoryAdapter = new CustomHistoryAdapter(ShowHistoryActivity.this , historyRowItems);
                    listView.setAdapter(customHistoryAdapter);
                    loadingProgressDialog.dismiss();
                }else {
                    noHistoryFound.setVisibility(View.VISIBLE);
                    loadingProgressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());
            }
        });
    }
    private void setSupportActionBar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,SearchActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SearchHistoryDTO dto = new SearchHistoryDTO(new Date() ,historyRowItems.get(position).getTitle(), historyRowItems.get(position).getLink());
        saveSearchHistory(FirebaseAuth.getInstance().getUid() , dto);
        Intent intent = new Intent(ShowHistoryActivity.this, WebViewActivity.class);
        intent.putExtra("title", historyRowItems.get(position).getTitle());
        intent.putExtra("url", historyRowItems.get(position).getLink());
        startActivity(intent);
    }

    private static void saveSearchHistory(String userId, SearchHistoryDTO dto) {
        if(dto != null){
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            reference.child("users_search_history").child(userId).push().setValue(dto).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, e.getLocalizedMessage());
                }
            });
        }
    }
}
