package fauzi.hilmy.submissionkeduakatalogfilmuiux.helper;

import android.content.Context;

import fauzi.hilmy.submissionkeduakatalogfilmuiux.R;

public class Genre {
    public static String getGenres(String data, Context context) {
        return String.valueOf(data)
                .replace("28", context.getString(R.string.action))
                .replace("27", context.getString(R.string.horor))
                .replace("12", context.getString(R.string.adventure))
                .replace("16", context.getString(R.string.animation))
                .replace("35", context.getString(R.string.comedy))
                .replace("80", context.getString(R.string.crime))
                .replace("99", context.getString(R.string.documentary))
                .replace("18", context.getString(R.string.drama))
                .replace("14", context.getString(R.string.fantasy))
                .replace("10402", context.getString(R.string.music))
                .replace("9648", context.getString(R.string.misteri))
                .replace("10749", context.getString(R.string.romance))
                .replace("878", context.getString(R.string.scifi))
                .replace("10770", context.getString(R.string.tvmovie))
                .replace("10752", context.getString(R.string.war))
                .replace("37", context.getString(R.string.barat))
                .replace("10751", context.getString(R.string.family))
                .replace("53", context.getString(R.string.thriller))
                .replace("36", context.getString(R.string.sejarah))
                .replace("[", "")
                .replace("]", "")
                .replace(",", ", ");
    }
}
