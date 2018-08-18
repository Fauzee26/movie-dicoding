package fauzi.hilmy.submissionkeduakatalogfilmuiux.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import fauzi.hilmy.submissionkeduakatalogfilmuiux.DetailActivity;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.data.Movie;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.R;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Movie> movieList;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MovieAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_movie, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MovieAdapter.MyViewHolder holder, int position) {
        final Movie movie = movieList.get(position);

        holder.txtNama.setText(movie.getMovieName());
        holder.txtDesc.setText(movie.getMovieDescription());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(movie.getMovieDate());
            SimpleDateFormat newDateFormat = new SimpleDateFormat("EEEE, dd MMM yyyy");
            String date_release = newDateFormat.format(date);
            holder.txtDate.setText(date_release);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.txtRating.setText(String.valueOf(movie.getRating()) + "/10");
        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/original/" + movie.getMoviePoster())
                .placeholder(R.drawable.img)
                .into(holder.imgPoster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);

                intent.putExtra(DetailActivity.EXTRA_NAME, movie.getMovieName());
                intent.putExtra(DetailActivity.EXTRA_DATE, movie.getMovieDate());
                intent.putExtra(DetailActivity.EXTRA_DESC, movie.getMovieDescription());
                intent.putExtra(DetailActivity.EXTRA_POSTER, movie.getMoviePoster());
                intent.putExtra(DetailActivity.EXTRA_RATING, movie.getRating());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (movieList == null)
            return 0;
        return movieList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtNama, txtRating, txtDesc, txtDate;
        ImageView imgPoster;

        MyViewHolder(View itemView) {
            super(itemView);
            txtNama = itemView.findViewById(R.id.edt_namafilm);
            txtDate = itemView.findViewById(R.id.edt_tglfilm);
            txtDesc = itemView.findViewById(R.id.edt_deskripsifilm);
            txtRating = itemView.findViewById(R.id.edt_ratingfilm);
            imgPoster = itemView.findViewById(R.id.img_poster);
        }
    }

    public void setData(ArrayList<Movie> items) {
        movieList = items;
        notifyDataSetChanged();
    }

    public void setFilter(ArrayList<Movie> filterList) {
        movieList = new ArrayList<>();
        movieList.addAll(filterList);
        notifyDataSetChanged();
    }
}

