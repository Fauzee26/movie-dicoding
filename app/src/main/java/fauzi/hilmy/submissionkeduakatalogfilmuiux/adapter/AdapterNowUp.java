package fauzi.hilmy.submissionkeduakatalogfilmuiux.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fauzi.hilmy.submissionkeduakatalogfilmuiux.DetailActivity;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.R;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.data.Movie;
//import fauzi.hilmy.submissionkeduakatalogfilmuiux.data.Upcoming;

public class AdapterNowUp extends RecyclerView.Adapter<AdapterNowUp.MyViewHolder>{

    private Context context;
    private ArrayList<Movie> upcomings;

    public AdapterNowUp(Context context) {
        this.context = context;
    }

    @Override
    public AdapterNowUp.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_now_upcoming, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterNowUp.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w780/" + upcomings.get(position).getMoviePoster())
                .placeholder(R.drawable.img)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);

                intent.putExtra(DetailActivity.EXTRA_NAME, upcomings.get(position).getMovieName());
                intent.putExtra(DetailActivity.EXTRA_DATE, upcomings.get(position).getMovieDate());
                intent.putExtra(DetailActivity.EXTRA_DESC, upcomings.get(position).getMovieDescription());
                intent.putExtra(DetailActivity.EXTRA_POSTER, upcomings.get(position).getMoviePoster());
                intent.putExtra(DetailActivity.EXTRA_RATING, String.valueOf(upcomings.get(position).getRating()));
                intent.putExtra(DetailActivity.EXTRA_POSTER_BACK, upcomings.get(position).getBackgroundPoster());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (upcomings == null)
            return 0;
        return upcomings.size();    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgposterr);
        }
    }

    public void setData(ArrayList<Movie> items) {
        upcomings = items;
        notifyDataSetChanged();
    }
}
