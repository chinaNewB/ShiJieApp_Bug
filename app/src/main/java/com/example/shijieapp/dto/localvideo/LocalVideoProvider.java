package com.example.shijieapp.dto.localvideo;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;


import com.example.shijieapp.base.BaseApplication;
import com.example.shijieapp.bean.LocalVideoItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 1278324384@qq.com
 */
public class LocalVideoProvider implements AbstructProvider {
    private Uri uri;
    private Cursor cursor;

    private static String[] thumbColumns = {MediaStore.Video.Thumbnails.DATA};

    private static String getThumbnailPathForLocalFile(Context context, long fileId) {

        MediaStore.Video.Thumbnails.getThumbnail(context.getContentResolver(),
                fileId, MediaStore.Video.Thumbnails.MICRO_KIND, null);

        Cursor thumbCursor = null;
        try {

            thumbCursor = context.getContentResolver().query(
                    MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
                    thumbColumns, MediaStore.Video.Thumbnails.VIDEO_ID + " = "
                            + fileId, null, null);

            if (thumbCursor.moveToFirst()) {
                String thumbPath = thumbCursor.getString(thumbCursor
                        .getColumnIndex(MediaStore.Video.Thumbnails.DATA));

                return thumbPath;
            }

        } finally {
            thumbCursor.close();
        }

        return null;
    }

    @Override
    public List<LocalVideoItem> getList() {
        List<LocalVideoItem> list = null;
        if (BaseApplication.getAppContext() != null) {
            uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            cursor = BaseApplication.getAppContext().getContentResolver().query(
                    uri,  null, null,
                    null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
            if (cursor != null) {
                list = new ArrayList<LocalVideoItem>();
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor
                            .getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                    String title = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
                    String album = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.ALBUM));
                    String artist = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST));
                    String displayName = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
                    String mimeType = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
                    String path = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                    long duration = cursor
                            .getInt(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                    long size = cursor
                            .getLong(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));

                    String imgPath = getThumbnailPathForLocalFile(BaseApplication.getAppContext(), id);
                    LocalVideoItem video = new LocalVideoItem(id, title, album, artist, displayName, mimeType, path, size, duration,imgPath);
                    list.add(video);
                }
                cursor.close();
            }
        }
        return list;
    }


}
