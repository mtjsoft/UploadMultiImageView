package cn.mtjsoft.uploadmultiimagedemo;

import cn.mtjsoft.multiimagelibrary.imp.ImageInfo;

/**
 * 实现 ImageInfo
 */
public class ImageModel implements ImageInfo {

    private Object path;

    public Object getPath() {
        return path;
    }

    public void setPath(Object path) {
        this.path = path;
    }

    /**
     * @return 返回图片地址
     */
    @Override
    public Object getImagePath() {
        return path;
    }

    /**
     * @return 这里固定返回 false
     */
    @Override
    public boolean isLastAddViewData() {
        return false;
    }
}
