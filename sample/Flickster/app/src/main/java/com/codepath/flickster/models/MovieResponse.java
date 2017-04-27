
package com.codepath.flickster.models;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieResponse implements Parcelable {

    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("results")
    @Expose
    private List<Movie> results = null;
    @SerializedName("dates")
    @Expose
    private Dates dates;
    @SerializedName("total_pages")
    @Expose
    private int totalPages;
    @SerializedName("total_results")
    @Expose
    private int totalResults;
    public final static Parcelable.Creator<MovieResponse> CREATOR = new Creator<MovieResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public MovieResponse createFromParcel(Parcel in) {
            MovieResponse instance = new MovieResponse();
            instance.page = ((int) in.readValue((int.class.getClassLoader())));
            in.readList(instance.results, (Movie.class.getClassLoader()));
            instance.dates = ((Dates) in.readValue((Dates.class.getClassLoader())));
            instance.totalPages = ((int) in.readValue((int.class.getClassLoader())));
            instance.totalResults = ((int) in.readValue((int.class.getClassLoader())));
            return instance;
        }

        public MovieResponse[] newArray(int size) {
            return (new MovieResponse[size]);
        }

    };

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> movies) {
        this.results = movies;
    }

    public Dates getDates() {
        return dates;
    }

    public void setDates(Dates dates) {
        this.dates = dates;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(page);
        dest.writeList(results);
        dest.writeValue(dates);
        dest.writeValue(totalPages);
        dest.writeValue(totalResults);
    }

    public int describeContents() {
        return 0;
    }

}
