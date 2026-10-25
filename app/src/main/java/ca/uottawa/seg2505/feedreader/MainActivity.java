package ca.uottawa.seg2505.feedreader;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Demonstrates SQLite CRUD operations.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "FeedReader";
    private FeedReaderDbHelper dbHelper;

    /**
     * Initializes the activity and executes database operations.
     *
     * @param savedInstanceState Previous activity state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView resultView = new TextView(this);
        resultView.setPadding(40, 60, 40, 40);
        resultView.setTextSize(18);
        setContentView(resultView);

        dbHelper = new FeedReaderDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String table = FeedReaderContract.FeedEntry.TABLE_NAME;
        String title = FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE;
        String subtitle = FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE;
        String id = FeedReaderContract.FeedEntry._ID;

        // Insert a new entry.
        ContentValues values = new ContentValues();
        values.put(title, "SEG2505");
        values.put(subtitle, "SQLite Lab 6");

        long newRowId = db.insert(table, null, values);

        // Read the inserted entry.
        String[] projection = {id, title, subtitle};
        String selection = id + " = ?";
        String[] selectionArgs = {String.valueOf(newRowId)};

        StringBuilder result = new StringBuilder("SQLite CRUD Demo\n\n");

        try (Cursor cursor = db.query(
                table, projection, selection, selectionArgs,
                null, null, null)) {

            if (cursor.moveToFirst()) {
                result.append("INSERT / READ\n")
                        .append("Title: ")
                        .append(cursor.getString(cursor.getColumnIndexOrThrow(title)))
                        .append("\nSubtitle: ")
                        .append(cursor.getString(cursor.getColumnIndexOrThrow(subtitle)))
                        .append("\n\n");
            }
        }

        // Update the entry.
        ContentValues updatedValues = new ContentValues();
        updatedValues.put(subtitle, "SQLite Updated");

        int updatedRows = db.update(
                table, updatedValues, selection, selectionArgs);

        result.append("UPDATE\nRows updated: ")
                .append(updatedRows)
                .append("\n\n");

        // Delete the entry.
        int deletedRows = db.delete(table, selection, selectionArgs);

        result.append("DELETE\nRows deleted: ")
                .append(deletedRows);

        resultView.setText(result.toString());
        Log.d(TAG, result.toString());
    }

    /**
     * Closes the database helper when the activity is destroyed.
     */
    @Override
    protected void onDestroy() {
        if (dbHelper != null) {
            dbHelper.close();
        }
        super.onDestroy();
    }
}
