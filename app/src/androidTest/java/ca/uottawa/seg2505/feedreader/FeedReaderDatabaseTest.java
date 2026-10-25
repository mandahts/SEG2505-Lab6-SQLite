package ca.uottawa.seg2505.feedreader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented tests for the FeedReader SQLite database.
 */
@RunWith(AndroidJUnit4.class)
public class FeedReaderDatabaseTest {

    /**
     * Verifies that an entry can be inserted and retrieved.
     */
    @Test
    public void insertAndReadEntry() {
        Context context = InstrumentationRegistry
                .getInstrumentation().getTargetContext();

        FeedReaderDbHelper helper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        String table = FeedReaderContract.FeedEntry.TABLE_NAME;
        String title = FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE;
        String subtitle = FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE;
        String id = FeedReaderContract.FeedEntry._ID;

        ContentValues values = new ContentValues();
        values.put(title, "JUnit Test");
        values.put(subtitle, "SQLite Verification");

        long rowId = -1;

        try {
            rowId = db.insertOrThrow(table, null, values);
            assertTrue(rowId != -1);

            try (Cursor cursor = db.query(
                    table,
                    new String[]{title, subtitle},
                    id + " = ?",
                    new String[]{String.valueOf(rowId)},
                    null, null, null)) {

                assertTrue(cursor.moveToFirst());
                assertEquals("JUnit Test", cursor.getString(0));
                assertEquals("SQLite Verification", cursor.getString(1));
            }
        } finally {
            if (rowId != -1) {
                db.delete(table, id + " = ?",
                        new String[]{String.valueOf(rowId)});
            }
            helper.close();
        }
    }
}

