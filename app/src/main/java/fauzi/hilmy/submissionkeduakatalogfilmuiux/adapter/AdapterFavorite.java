package fauzi.hilmy.submissionkeduakatalogfilmuiux.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fauzi.hilmy.submissionkeduakatalogfilmuiux.activity.DetailActivity;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.R;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.data.Favorite;

public class AdapterFavorite extends RecyclerView.Adapter<AdapterFavorite.MyViewHolder> {

    private Context context;
    private Cursor favorite;

    public AdapterFavorite(Context context) {
        this.context = context;
    }

    public void setFavorite(Cursor favorite) {
        this.favorite = favorite;
    }

    @Override
    public AdapterFavorite.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_movie, null, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(layoutParams);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterFavorite.MyViewHolder holder, final int position) {
        final Favorite fav = getItem(position);
        holder.txtNama.setText(fav.getMovieName());
        holder.txtRating.setText(String.valueOf(fav.getRating()));
        holder.txtDesc.setText(fav.getMovieDescription());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(fav.getMovieDate());
            SimpleDateFormat newDateFormat = new SimpleDateFormat("EEEE, dd MMM yyyy");
            String date_release = newDateFormat.format(date);
            holder.txtDate.setText(date_release);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/original" + fav.getMoviePoster())
                .placeholder(R.drawable.img)
                .into(holder.imgPoster);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra(DetailActivity.EXTRA_NAME, fav.getMovieName());
                i.putExtra(DetailActivity.EXTRA_ID, fav.getId());
                i.putExtra(DetailActivity.EXTRA_DATE, fav.getMovieDate());
                i.putExtra(DetailActivity.EXTRA_DESC, fav.getMovieDescription());
                i.putExtra(DetailActivity.EXTRA_POSTER, fav.getMoviePoster());
                i.putExtra(DetailActivity.EXTRA_RATING, String.valueOf(fav.getRating()));
                i.putExtra(DetailActivity.EXTRA_POSTER_BACK, fav.getBackgroundPoster());

//                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (favorite == null) return 0;
        return favorite.getCount();
    }

    private Favorite getItem(int position) {
        if (!favorite.moveToPosition(position)) {
            throw new IllegalStateException("Position Invalid");
        }
        return new Favorite(favorite);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
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
}
