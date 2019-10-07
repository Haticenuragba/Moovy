package com.example.android.project_part1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FullReviewActivity extends AppCompatActivity {
    private TextView author;
    private TextView content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_review);
        initializeViews();
    }

    public void initializeViews(){
        author = findViewById(R.id.nameOfReviewerFull);
        content = findViewById(R.id.fullReview);

        author.setText(getIntent().getStringExtra("AUTHOR"));
        content.setText(getIntent().getStringExtra("CONTENT"));
    }

}
