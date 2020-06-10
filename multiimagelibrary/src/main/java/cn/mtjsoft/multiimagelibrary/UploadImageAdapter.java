package cn.mtjsoft.multiimagelibrary;

import android.content.Context;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import cn.mtjsoft.multiimagelibrary.imp.ImageInfo;
import cn.mtjsoft.multiimagelibrary.imp.ImageViewLoader;

public class UploadImageAdapter extends BaseQuickAdapter<ImageInfo, BaseViewHolder> {

    private ImageViewLoader imageViewLoader;
    private Context context;

    public UploadImageAdapter(Context context, int layoutResId, List<ImageInfo> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ImageInfo imageInfo) {
        ImageView imageView = baseViewHolder.getView(R.id.iv_item);
        ImageView deleteImage = baseViewHolder.getView(R.id.iv_delete);
        if (imageViewLoader != null) {
            imageViewLoader.displayImage(context, imageInfo.getImagePath(), imageView);
        }
    }
}
