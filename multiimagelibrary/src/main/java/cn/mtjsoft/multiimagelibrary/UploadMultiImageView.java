package cn.mtjsoft.multiimagelibrary;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.mtjsoft.multiimagelibrary.bean.AddBean;
import cn.mtjsoft.multiimagelibrary.imp.ImageAddClickListener;
import cn.mtjsoft.multiimagelibrary.imp.ImageDeleteClickListener;
import cn.mtjsoft.multiimagelibrary.imp.ImageInfo;
import cn.mtjsoft.multiimagelibrary.imp.ImageItemClickListener;
import cn.mtjsoft.multiimagelibrary.imp.ImageViewLoader;
import cn.mtjsoft.multiimagelibrary.utils.HHDensityUtils;

public class UploadMultiImageView extends RecyclerView {

    //数据
    private List<ImageInfo> imageInfoList = new ArrayList<>();
    // adapter
    private UploadImageAdapter uploadImageAdapter;
    // item拖拽处理
    private ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemDragTouchCallback());
    // 图片加载器
    private ImageViewLoader imageViewLoader;
    // 图片点击回调
    private ImageItemClickListener imageItemClickListener;
    // 新增按钮点击回调
    private ImageAddClickListener addClickListener;
    // 删除回调
    private ImageDeleteClickListener deleteClickListener;
    // 设置图片缩放类型
    private ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER_CROP;
    // 间隔大小 默认2dp
    private int itemDecorationSize = 2;
    // 是否展示删除按钮
    private boolean isShowDelete = true;
    // 是否展示最后面的新增按钮(当超过最大张数时，也会隐藏)
    private boolean isShowAdd = false;
    // 设置删除按钮
    private int deleteRes = R.drawable.img_delete;
    // 设置删除按钮按钮大小 默认25dp
    private int deleteResWH = 25;
    // 设置删除按钮margin
    private int deleteResMargin = 6;
    // 设置新增按钮
    private int addRes = R.drawable.img_add;
    // 是否开启拖拽排序
    private boolean isDrag = false;
    // 最大显示张数
    private int maxNumber = Integer.MAX_VALUE;
    // 每行展示列数
    private int columns = 3;
    // 图片的高度 默认100dp
    private int imgHeight = 0;

    // itemView宽度
    private int itemViewWidth;

    public UploadMultiImageView(Context context) {
        this(context, null);
    }

    public UploadMultiImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UploadMultiImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        handleTypedArray(context, attrs);
    }

    private void handleTypedArray(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UploadMultiImageView);
        imgHeight = typedArray.getDimensionPixelSize(R.styleable.UploadMultiImageView_img_height, 0);
        itemDecorationSize = typedArray.getDimensionPixelSize(R.styleable.UploadMultiImageView_item_spacing, HHDensityUtils.dip2px(getContext(), itemDecorationSize));
        columns = typedArray.getInt(R.styleable.UploadMultiImageView_column_count, columns);
        maxNumber = typedArray.getInt(R.styleable.UploadMultiImageView_max_count, maxNumber);
        isShowDelete = typedArray.getBoolean(R.styleable.UploadMultiImageView_is_showDelete, isShowDelete);
        isShowAdd = typedArray.getBoolean(R.styleable.UploadMultiImageView_is_showAdd, isShowAdd);
        deleteRes = typedArray.getResourceId(R.styleable.UploadMultiImageView_deleteRes, deleteRes);
        deleteResWH = typedArray.getDimensionPixelSize(R.styleable.UploadMultiImageView_deleteWH, HHDensityUtils.dip2px(getContext(), deleteResWH));
        deleteResMargin = typedArray.getDimensionPixelSize(R.styleable.UploadMultiImageView_deleteResMargin, HHDensityUtils.dip2px(getContext(), deleteResMargin));
        addRes = typedArray.getResourceId(R.styleable.UploadMultiImageView_addRes, addRes);
        isDrag = typedArray.getBoolean(R.styleable.UploadMultiImageView_is_Drag, isDrag);
        typedArray.recycle();
    }

    /**
     * @param imgHeight 设置图片高度，默认与宽度相等
     * @return
     */
    public UploadMultiImageView setImgHeight(int imgHeight) {
        this.imgHeight = HHDensityUtils.dip2px(getContext(), imgHeight);
        return this;
    }

    /**
     * @param columns 设置列数
     */
    public UploadMultiImageView setColumns(int columns) {
        this.columns = columns;
        return this;
    }

    /**
     * @param deleteRes 设置 删除按钮
     */
    public UploadMultiImageView setDeleteRes(int deleteRes) {
        this.deleteRes = deleteRes;
        return this;
    }

    /**
     * @param deleteResWH 设置删除按钮宽高
     * @return
     */
    public UploadMultiImageView setDeleteResWH(int deleteResWH) {
        this.deleteResWH = HHDensityUtils.dip2px(getContext(), deleteResWH);
        return this;
    }

    /**
     * @param deleteResMargin 设置删除按钮 Margin
     * @return
     */
    public UploadMultiImageView setDeleteResMargin(int deleteResMargin) {
        this.deleteResMargin = HHDensityUtils.dip2px(getContext(), deleteResMargin);
        return this;
    }

    /**
     * @param addRes 设置新增按钮
     */
    public UploadMultiImageView setAddRes(int addRes) {
        this.addRes = addRes;
        return this;
    }

    /**
     * @param drag 是否开启拖拽排序
     */
    public UploadMultiImageView setDrag(boolean drag) {
        isDrag = drag;
        return this;
    }

    /**
     * @param itemDecorationSize 间隔大小
     */
    public UploadMultiImageView setItemDecorationSize(int itemDecorationSize) {
        this.itemDecorationSize = HHDensityUtils.dip2px(getContext(), itemDecorationSize);
        return this;
    }

    /**
     * @param showAdd 是否展示最后面的新增按钮
     */
    public UploadMultiImageView setShowAdd(boolean showAdd) {
        isShowAdd = showAdd;
        return this;
    }

    /**
     * @param maxNumber 设置最大显示数量
     */
    public UploadMultiImageView setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
        return this;
    }

    /**
     * @param showDelete 是否显示删除按钮
     */
    public UploadMultiImageView setShowDelete(boolean showDelete) {
        isShowDelete = showDelete;
        return this;
    }

    /**
     * @param imageInfoList 设置数据
     */
    public UploadMultiImageView setImageInfoList(List<ImageInfo> imageInfoList) {
        if (imageInfoList != null) {
            this.imageInfoList = imageInfoList;
        }
        return this;
    }

    /**
     * @param imageViewLoader 图片加载
     */
    public UploadMultiImageView setImageViewLoader(ImageViewLoader imageViewLoader) {
        this.imageViewLoader = imageViewLoader;
        return this;
    }

    /**
     * @param imageItemClickListener 图片点击
     */
    public UploadMultiImageView setImageItemClickListener(ImageItemClickListener imageItemClickListener) {
        this.imageItemClickListener = imageItemClickListener;
        return this;
    }

    /**
     * @param addClickListener 新增按钮点击
     */
    public UploadMultiImageView setAddClickListener(ImageAddClickListener addClickListener) {
        this.addClickListener = addClickListener;
        return this;
    }

    /**
     * 删除点击回调
     *
     * @param deleteClickListener
     * @return
     */
    public UploadMultiImageView setDeleteClickListener(ImageDeleteClickListener deleteClickListener) {
        this.deleteClickListener = deleteClickListener;
        return this;
    }

    /**
     * 设置图片缩放类型
     *
     * @param scaleType
     * @return
     */
    public UploadMultiImageView setScaleType(ImageView.ScaleType scaleType) {
        this.scaleType = scaleType;
        return this;
    }

    public void show() {
        if (isDrag) {
            itemTouchHelper.attachToRecyclerView(this);
        }
        addItemDecoration();
        addViewData();
        this.setLayoutManager(new GridLayoutManager(getContext(), columns));
        this.post(new Runnable() {
            @Override
            public void run() {
                itemViewWidth = (getMeasuredWidth() - (columns + 1) * itemDecorationSize) / columns;
                uploadImageAdapter = new UploadImageAdapter(getContext(), imageInfoList);
                setAdapter(uploadImageAdapter);
            }
        });
    }

    /**
     * 添加新数据
     */
    public void addNewData(List<ImageInfo> data) {
        if (data != null && data.size() > 0) {
            // 如果有新增按钮，先移除，再添加
            removeViewData();
            imageInfoList.addAll(data);
            addViewData();
            if (uploadImageAdapter != null) {
                uploadImageAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 刷新数据
     */
    public void notifyDataSetChanged() {
        if (uploadImageAdapter != null) {
            uploadImageAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 删除指定item
     *
     * @param position
     */
    public void deleteItem(int position) {
        if (position >= 0 && position < imageInfoList.size()) {
            imageInfoList.remove(position);
            addViewData();
            uploadImageAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取数据
     */
    public List<ImageInfo> getImageInfoList() {
        if (isShowAdd && imageInfoList.size() > 0 && imageInfoList.get(imageInfoList.size() - 1).isLastAddViewData()) {
            // 最后一个是新增按钮,删掉
            List<ImageInfo> list = new ArrayList<>(imageInfoList);
            list.remove(imageInfoList.size() - 1);
            return list;
        } else {
            return imageInfoList;
        }
    }

    /**
     * 添加间隔
     */
    private void addItemDecoration() {
        if (itemDecorationSize > 0) {
            this.addItemDecoration(new ItemDecoration() {
                @Override
                public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    int posi = parent.getChildAdapterPosition(view);
                    int itemPad = itemDecorationSize;
                    boolean isLastRaw = isLastRaw(posi, columns, imageInfoList.size());
                    if (isFirstColum(posi, columns)) { // 第一列
                        if (isLastRaw) {
                            // 最后一行
                            outRect.set(itemPad, itemPad, itemPad / 2, itemPad);
                        } else {
                            outRect.set(itemPad, itemPad, itemPad / 2, 0);
                        }
                    } else if (isLastColum(posi, columns)) { // 最后一列
                        if (isLastRaw) {
                            // 最后一行
                            outRect.set(itemPad / 2, itemPad, itemPad, itemPad);
                        } else {
                            outRect.set(itemPad / 2, itemPad, itemPad, 0);
                        }
                    } else {
                        // 中间列
                        if (isLastRaw) {
                            outRect.set(itemPad / 2, itemPad, itemPad / 2, itemPad);
                        } else {
                            outRect.set(itemPad / 2, itemPad, itemPad / 2, 0);
                        }
                    }
                }
            });
        }
    }

    /**
     * 添加新增按钮
     */
    private void addViewData() {
        if (isShowAdd && maxNumber > imageInfoList.size()) {
            if (imageInfoList.size() == 0 || !imageInfoList.get(imageInfoList.size() - 1).isLastAddViewData()) {
                AddBean bean = new AddBean();
                bean.setAddPath(addRes);
                bean.setAdd(true);
                imageInfoList.add(bean);
            }
        }
        if (maxNumber > 0 && maxNumber < imageInfoList.size()) { // 超出最大显示值，直接截取
            imageInfoList = imageInfoList.subList(0, maxNumber);
        }
    }

    /**
     * 移除新增按钮
     */
    private void removeViewData() {
        if (isShowAdd && imageInfoList.size() > 0 && imageInfoList.get(imageInfoList.size() - 1).isLastAddViewData()) {
            imageInfoList.remove(imageInfoList.size() - 1);
        }
    }

    /**
     * 是否是最后一列
     */
    private boolean isLastColum(int pos, int spanCount) {
        return (pos + 1) % spanCount == 0;
    }

    /**
     * 是否是第一列
     */
    private boolean isFirstColum(int pos, int spanCount) {
        return (pos + 1) % spanCount == 1;
    }

    /**
     * 是否是最后一行
     */
    private boolean isLastRaw(int pos, int spanCount, int childCount) {
        int ys = childCount % spanCount;
        boolean isLastRaw;
        if (ys > 0) {
            isLastRaw = (pos >= childCount - ys);
        } else {
            isLastRaw = (pos >= childCount - spanCount);
        }
        return isLastRaw;
    }

    /**
     * adapter
     */
    private class UploadImageAdapter extends RecyclerView.Adapter<UploadImageAdapter.MyViewHolder> {

        private ViewGroup.LayoutParams layoutParams;

        private FrameLayout.LayoutParams deleteViewParams;

        private Context context;

        private List<ImageInfo> data;

        public UploadImageAdapter(Context context, List<ImageInfo> data) {
            this.context = context;
            this.data = data;
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, imgHeight <= 0 ? itemViewWidth : imgHeight);
            deleteViewParams = new FrameLayout.LayoutParams(deleteResWH, deleteResWH);
            deleteViewParams.topMargin = deleteResMargin;
            deleteViewParams.rightMargin = deleteResMargin;
            deleteViewParams.gravity = Gravity.END;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyViewHolder(View.inflate(context, R.layout.item_def, null));
        }


        @Override
        public int getItemCount() {
            return data == null ? 0 : data.size();
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            ImageInfo imageInfo = data.get(position);
            holder.frameLayout.setLayoutParams(layoutParams);
            holder.deleteImage.setImageResource(deleteRes);
            holder.deleteImage.setVisibility(isShowDelete && !imageInfo.isLastAddViewData() ? VISIBLE : GONE);
            holder.deleteImage.setLayoutParams(deleteViewParams);
            holder.imageView.setScaleType(scaleType);
            if (imageInfo.isLastAddViewData()) {
                holder.imageView.setImageResource(addRes);
            } else {
                if (imageViewLoader != null) {
                    imageViewLoader.displayImage(getContext(), imageInfo.getImagePath(), holder.imageView);
                }
                holder.deleteImage.setOnClickListener(new MyClick(position));
            }
            holder.imageView.setOnClickListener(new MyClick(position));
        }

        private class MyClick implements OnClickListener {
            private int position;

            public MyClick(int position) {
                this.position = position;
            }

            @Override
            public void onClick(View v) {
                int id = v.getId();
                if (id == R.id.iv_item) {
                    if (data.get(position).isLastAddViewData() && addClickListener != null) {
                        addClickListener.ImageAddClick();
                    } else if (imageItemClickListener != null) {
                        imageItemClickListener.ImageItemClick(position);
                    }
                } else if (id == R.id.iv_delete) {
                    if (deleteClickListener != null) {
                        deleteClickListener.ImageDeleteClick(UploadMultiImageView.this, position);
                    } else {
                        deleteItem(position);
                    }
                }
            }
        }

        private class MyViewHolder extends ViewHolder {
            private ImageView imageView;
            private ImageView deleteImage;
            private FrameLayout frameLayout;

            public MyViewHolder(@NonNull View baseViewHolder) {
                super(baseViewHolder);
                frameLayout = baseViewHolder.findViewById(R.id.fl_layout);
                imageView = baseViewHolder.findViewById(R.id.iv_item);
                deleteImage = baseViewHolder.findViewById(R.id.iv_delete);
            }
        }
    }

    /**
     * 拖拽排序
     */
    private class ItemDragTouchCallback extends ItemTouchHelper.Callback {
        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            int dragFlags = 0;
            int position = viewHolder.getAdapterPosition();
            if (isDrag && (!isShowAdd || !imageInfoList.get(position).isLastAddViewData())) {
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            }
            return makeMovementFlags(dragFlags, 0);
        }

        /**
         * 针对drag状态，当前target对应的item是否允许移动
         * 我们一般用drag来做一些换位置的操作，就是当前对应的target对应的Item可以移动
         */
        @Override
        public boolean canDropOver(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            //得到当拖拽的viewHolder的Position
            int fromPosition = viewHolder.getAdapterPosition();
            //拿到当前拖拽到的item的viewHolder
            int toPosition = target.getAdapterPosition();
            // 当存在新增按钮时，当前和目标值，不能是最后一个新增按钮
            return !isShowAdd || (!imageInfoList.get(fromPosition).isLastAddViewData() && !imageInfoList.get(toPosition).isLastAddViewData());
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            //得到当拖拽的viewHolder的Position
            int fromPosition = viewHolder.getAdapterPosition();
            //拿到当前拖拽到的item的viewHolder
            int toPosition = target.getAdapterPosition();
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(imageInfoList, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(imageInfoList, i, i - 1);
                }
            }
            uploadImageAdapter.notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        /**
         * 针对swipe状态，是否允许swipe(滑动)操作
         */
        public boolean isItemViewSwipeEnabled() {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }

        @Override
        public void onSelectedChanged(@Nullable ViewHolder viewHolder, int actionState) {
            if (viewHolder != null && actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                int position = viewHolder.getAdapterPosition();
                if (isDrag && (!isShowAdd || !imageInfoList.get(position).isLastAddViewData())) {
                    AnimatorSet animatorSet = new AnimatorSet();
                    ObjectAnimator animatorScaleX =
                            ObjectAnimator.ofFloat(viewHolder.itemView, "ScaleX", 1.0f, 0.85f);
                    ObjectAnimator animatorScaleY =
                            ObjectAnimator.ofFloat(viewHolder.itemView, "ScaleY", 1.0f, 0.85f);
                    animatorSet.playTogether(animatorScaleX, animatorScaleY);
                    animatorSet.setDuration(200L);
                    animatorSet.start();
                }
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        @Override
        public void clearView(@NonNull RecyclerView recyclerView, @NonNull ViewHolder viewHolder) {
            int position = viewHolder.getAdapterPosition();
            if (isDrag && (!isShowAdd || !imageInfoList.get(position).isLastAddViewData())) {
                AnimatorSet animatorSet = new AnimatorSet();
                ObjectAnimator animatorScaleX =
                        ObjectAnimator.ofFloat(viewHolder.itemView, "ScaleX", 0.85f, 1.0f);
                ObjectAnimator animatorScaleY =
                        ObjectAnimator.ofFloat(viewHolder.itemView, "ScaleY", 0.85f, 1.0f);
                animatorSet.playTogether(animatorScaleX, animatorScaleY);
                animatorSet.setDuration(100L);
                animatorSet.start();
            }
            super.clearView(recyclerView, viewHolder);
        }
    }
}
