package cn.mtjsoft.uploadmultiimagedemo;

import cn.mtjsoft.multiimagelibrary.imp.ImageInfo;

public class ImageModel implements ImageInfo {
    private Object path;

    public Object getPath() {
        return path;
    }

    public void setPath(Object path) {
        this.path = path;
    }

    @Override
    public Object getImagePath() {
        return path;
    }

    @Override
    public boolean isLastAddViewData() {
        return false;
    }
}
