package fauzi.hilmy.submissionkeduakatalogfilmuiux.data;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Detail implements Parcelable {
    private int vote_count;
    private int runtime;
    private String tagline;
    private int vote;

    public Detail(JSONObject object) {
        try {
            int vote_count = object.getInt("vote_count");
            int vote = object.getInt("vote_average");
            int runtime = object.getInt("runtime");
            String tagline = object.getString("tagline");

            this.vote_count = vote_count;
            this.vote = vote;
            this.runtime = runtime;
            this.tagline = tagline;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Detail() {

    }

    public int getVote() {
        return vote;
    }

    public int getVote_count() {
        return vote_count;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getTagline() {
        return tagline;
    }

    protected Detail(Parcel in) {
        vote_count = in.readInt();
        runtime = in.readInt();
        tagline = in.readString();
        vote = in.readInt();
    }

    public static final Creator<Detail> CREATOR = new Creator<Detail>() {
        @Override
        public Detail createFromParcel(Parcel in) {
            return new Detail(in);
        }

        @Override
        public Detail[] newArray(int size) {
            return new Detail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(vote_count);
        dest.writeInt(runtime);
        dest.writeString(tagline);
        dest.writeInt(vote);
    }
}
