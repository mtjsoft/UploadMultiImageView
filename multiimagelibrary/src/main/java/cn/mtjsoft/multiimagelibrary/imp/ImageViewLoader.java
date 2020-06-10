package cn.mtjsoft.multiimagelibrary.imp;

import android.content.Context;
import android.widget.ImageView;

/**
 * 图片加载器
 */
public interface ImageViewLoader {
    void displayImage(Context context, Object path, ImageView imageView);
}
