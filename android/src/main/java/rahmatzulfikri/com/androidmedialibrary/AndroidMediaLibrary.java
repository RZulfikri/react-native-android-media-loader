package rahmatzulfikri.com.androidmedialibrary;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

/**
 * Created by Rahmat Zulfikri on 5/23/17.
 */

public class AndroidMediaLibrary {

    private int limit = 0;
    private int startId = -1;
    private ReactApplicationContext mContext;
    private CursorLoader cursorLoader;
    private String selection;

    public AndroidMediaLibrary(ReactApplicationContext context){
        mContext = context;
    }

    public void getMediaList(int limit, int startId, Promise promise){
        this.limit = limit;
        this.startId = startId;
        getMediaList(promise);
    }

    public void getMediaList(int limit, Promise promise){
        this.limit = limit;
        getMediaList(promise);
    }

    public void getMediaList(Promise promise){
        String[] projection = {
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.DATE_ADDED,
                MediaStore.Files.FileColumns.MEDIA_TYPE,
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.TITLE
        };

        if(startId > 0){
            selection = "(" + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
                + " OR "
                + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO
                + ") AND ("
                + MediaStore.Files.FileColumns._ID
                + " <=" + startId +" )";
        }else{
            selection = "(" + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                    + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
                    + " OR "
                    + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                    + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO
                    + ")";
        }

        Uri queryUri = MediaStore.Files.getContentUri("external");

        if(limit == 0) {
            cursorLoader = new CursorLoader(
                    mContext,
                    queryUri,
                    projection,
                    selection,
                    null,
                    MediaStore.Files.FileColumns._ID + " DESC, " + MediaStore.Files.FileColumns.DATE_ADDED + " DESC"
            );
        }else{
            cursorLoader = new CursorLoader(
                    mContext,
                    queryUri,
                    projection,
                    selection,
                    null,
                    MediaStore.Files.FileColumns._ID + " DESC, " + MediaStore.Files.FileColumns.DATE_ADDED + " DESC LIMIT + "+(limit+1)
            );
        }

        Cursor cursor = cursorLoader.loadInBackground();

        WritableArray content_list = Arguments.createArray();
        WritableMap status = Arguments.createMap();
        WritableMap result = Arguments.createMap();

        Log.e("DEBUG", String.valueOf(cursor.getCount()));
        if (cursor.moveToFirst()) {
            final int max_col = cursor.getColumnCount();
            int count = 0;
            do {
                if(count < limit || count == 0) {
                    WritableMap content = Arguments.createMap();

                    content.putString("type", cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE)));
                    content.putString("path", cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));
                    content.putString("title", cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE)));
                    content.putString("id", cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)));
                    content_list.pushMap(content);
                }else{
                    status.putString("next",cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)));
                    status.putBoolean("next_load", true);
                }
                if(limit > 0){
                    count ++;
                }
            } while (cursor.moveToNext());

            if(count <= limit){
                status.putBoolean("next_load", false);
            }
        }

        result.putMap("load_status", status);
        result.putArray("result", content_list);
        
        promise.resolve(result);
        cursor.close();

    }
}
