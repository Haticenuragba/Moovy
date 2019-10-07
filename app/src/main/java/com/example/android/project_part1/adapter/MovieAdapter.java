package com.example.android.project_part1.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.project_part1.MainActivity;
import com.example.android.project_part1.MovieDetailActivity;
import com.example.android.project_part1.R;
import com.example.android.project_part1.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context mContext;
    private List<Movie> movieList;
    private int layoutId;
    public MovieAdapter(Context mContext, List<Movie> movieList, int layoutId ){
        this.mContext = mContext;
        this.movieList = movieList;
        this.layoutId = layoutId;
    }


    public MovieAdapter.MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        Context        context                         = viewGroup.getContext();
        int            layoutIdForListItem             = layoutId;
        LayoutInflater inflater                        = LayoutInflater.from(context);
        boolean        shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        MovieViewHolder viewHolder = new MovieViewHolder(view);

        return viewHolder;
    }

    public void onBindViewHolder(final MovieAdapter.MovieViewHolder viewHolder, int i){
        if(MainActivity.current_original_title){
            viewHolder.title.setText(movieList.get(i).getOriginal_title());
            Picasso.get().load(movieList.get(i).getPoster_path()).into(viewHolder.image);
        }
        else {
            viewHolder.title.setText(movieList.get(i).getTitle());
            Picasso.get().load(movieList.get(i).getPoster_path()).into(viewHolder.image);
        }
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView image;
        public MovieViewHolder(View v){
            super(v);
            title = (TextView) v.findViewById(R.id.tv_item);
            image = (ImageView) v.findViewById(R.id.iv_item);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION){
                        Movie clickedItem = movieList.get(position);
                        Intent intent = new Intent(mContext, MovieDetailActivity.class);
                        intent.putExtra("MOVIE_ID", clickedItem.getId());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }
}

