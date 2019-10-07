package com.example.android.project_part1.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.project_part1.FullReviewActivity;
import com.example.android.project_part1.PeopleDetailActivity;
import com.example.android.project_part1.R;
import com.example.android.project_part1.model.People;
import com.example.android.project_part1.model.Review;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private Context mContext;
    private List<Review> reviewList;
    public ReviewAdapter(Context mContext, List<Review> reviewList ){
        this.mContext = mContext;
        this.reviewList = reviewList;
    }
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        Context        context                         = viewGroup.getContext();
        int            layoutIdForListItem             = R.layout.review_cell;
        LayoutInflater inflater                        = LayoutInflater.from(context);
        boolean        shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        ReviewViewHolder viewHolder = new ReviewViewHolder(view);

        return viewHolder;
    }

    public void onBindViewHolder(final ReviewAdapter.ReviewViewHolder viewHolder, int i){
        viewHolder.author.setText(reviewList.get(i).getAuthor());
        viewHolder.content.setText(reviewList.get(i).getContent());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder{
        TextView author;
        TextView content;
        TextView readMore;
        ImageView rightArrow;
        public ReviewViewHolder(View v){
            super(v);
            author = (TextView) v.findViewById(R.id.nameOfReviewer);
            content = (TextView) v.findViewById(R.id.shortReview);
            readMore = (TextView) v.findViewById(R.id.readMore);
            rightArrow = (ImageView) v.findViewById(R.id.rightArrow);

            rightArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Review clickedItem = reviewList.get(position);
                        Intent intent = new Intent(mContext, FullReviewActivity.class);
                        intent.putExtra("AUTHOR", clickedItem.getAuthor());
                        intent.putExtra("CONTENT", clickedItem.getContent());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                }

            });

            readMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            Review clickedItem = reviewList.get(position);
                            Intent intent = new Intent(mContext, FullReviewActivity.class);
                            intent.putExtra("AUTHOR", clickedItem.getAuthor());
                            intent.putExtra("CONTENT", clickedItem.getContent());
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(intent);
                        }
                    }

            });
        }
    }
}
