
package ca.uottawa.seg2505.feedreader;

import android.provider.BaseColumns;

/**
 * Defines the database schema for the FeedReader application.
 * Contains the table name and column names used by SQLite.
 */
public final class FeedReaderContract {

    /**
     * Private constructor to prevent instantiation.
     */
    private FeedReaderContract() {
    }

    /**
     * Defines the columns of the entry table.
     */
    public static class FeedEntry implements BaseColumns {

        public static final String TABLE_NAME = "entry";

        public static final String COLUMN_NAME_TITLE = "title";

        public static final String COLUMN_NAME_SUBTITLE = "subtitle";
    }
}
