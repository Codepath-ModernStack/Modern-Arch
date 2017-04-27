
package com.codepath.flickster.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dates implements Parcelable {

    @SerializedName("maximum")
    @Expose
    private String maximum;
    @SerializedName("minimum")
    @Expose
    private String minimum;
    public final static Parcelable.Creator<Dates> CREATOR = new Creator<Dates>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Dates createFromParcel(Parcel in) {
            Dates instance = new Dates();
            instance.maximum = ((String) in.readValue((String.class.getClassLoader())));
            instance.minimum = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Dates[] newArray(int size) {
            return (new Dates[size]);
        }

    };

    public String getMaximum() {
        return maximum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(maximum);
        dest.writeValue(minimum);
    }

    public int describeContents() {
        return 0;
    }

}
