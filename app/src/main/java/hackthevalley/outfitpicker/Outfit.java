package hackthevalley.outfitpicker;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by natalie on 2018-02-24.
 */

public class Outfit {

    @SerializedName("tag")
    @Expose
    private String tag;

    @SerializedName("images")
    @Expose
    private String[] images;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String[] getImages() {
        return images;
    }

    public void setImageUrl(String[] images) {
        this.images = images;
    }
}