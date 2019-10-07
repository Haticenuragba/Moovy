package com.example.android.project_part1.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.project_part1.MovieDetailActivity;
import com.example.android.project_part1.PeopleDetailActivity;
import com.example.android.project_part1.R;
import com.example.android.project_part1.model.Movie;
import com.example.android.project_part1.model.People;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder> {

    private Context mContext;
    private List<People> castList;
    public PeopleAdapter(Context mContext, List<People> castList ){
        this.mContext = mContext;
        this.castList = castList;
    }
    public PeopleAdapter.PeopleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        Context        context                         = viewGroup.getContext();
        int            layoutIdForListItem             = R.layout.movie_cast_cell;
        LayoutInflater inflater                        = LayoutInflater.from(context);
        boolean        shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        PeopleViewHolder viewHolder = new PeopleViewHolder(view);

        return viewHolder;
    }

    public void onBindViewHolder(final PeopleAdapter.PeopleViewHolder viewHolder, int i){
        viewHolder.title.setText(castList.get(i).getName());
        Picasso.get().load(castList.get(i).getProfile_path()).into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }

    public class PeopleViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView image;
        public PeopleViewHolder(View v){
            super(v);
            title = (TextView) v.findViewById(R.id.nameOfPeople);
            image = (ImageView) v.findViewById(R.id.ivOfPeople);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION){
                        People clickedItem = castList.get(position);
                        Intent intent = new Intent(mContext, PeopleDetailActivity.class);
                        intent.putExtra("PEOPLE_ID", clickedItem.getId());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }
}
