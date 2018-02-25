package hackthevalley.outfitpicker;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fanychen on 2018-02-24.
 */

public class ClothePhoto implements Parcelable {

    private String mUrl;
    private String mTitle;

    public ClothePhoto(String url, String title) {
        mUrl = url;
        mTitle = title;
    }

    protected ClothePhoto(Parcel in) {
        mUrl = in.readString();
        mTitle = in.readString();
    }

    public static final Creator<ClothePhoto> CREATOR = new Creator<ClothePhoto>() {
        @Override
        public ClothePhoto createFromParcel(Parcel in) {
            return new ClothePhoto(in);
        }

        @Override
        public ClothePhoto[] newArray(int size) {
            return new ClothePhoto[size];
        }
    };

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public static ClothePhoto[] getSpacePhotos() {

        return new ClothePhoto[]{
                new ClothePhoto("http://i.imgur.com/zuG2bGQ.jpg", "Galaxy"),
                new ClothePhoto("http://i.imgur.com/ovr0NAF.jpg", "Space Shuttle"),
                new ClothePhoto("http://i.imgur.com/n6RfJX2.jpg", "Galaxy Orion"),
                new ClothePhoto("http://i.imgur.com/qpr5LR2.jpg", "Earth"),
                new ClothePhoto("http://i.imgur.com/pSHXfu5.jpg", "Astronaut"),
                new ClothePhoto("http://i.imgur.com/3wQcZeY.jpg", "Satellite"),
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mUrl);
        parcel.writeString(mTitle);
    }
}