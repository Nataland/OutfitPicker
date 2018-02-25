package hackthevalley.outfitpicker;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Thao on 2/24/18.
 */

@IgnoreExtraProperties
public class Upload{

    public String name;
    public String url;
    public String tags;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Upload() {
    }

    public Upload(String name, String url, String tags) {
        this.name = name;
        this.url= url;
        this.tags = tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getTags() { return tags; }
}