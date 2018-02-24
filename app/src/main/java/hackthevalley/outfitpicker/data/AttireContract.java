package hackthevalley.outfitpicker.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Thao on 2/24/18.
 */

public class AttireContract {
    public static final String CONTENT_AUTHORITY = "hackthevalley.outfitpicker";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_ATTIRES = "attires";

    public static class AttireEntry implements BaseColumns {
        /** The content URI to access the data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ATTIRES);


        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ATTIRES;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ATTIRES;

        public static final String TABLE_NAME = "attires";
        public final static String _ID = BaseColumns._ID;
        public static final String COLUMN_ATTIRE_TYPE = "type"; // trouser, pants
        public static final String COLUMN_ATTIRE_USAGE = "usage"; // indoor, outdoor
    }
}
