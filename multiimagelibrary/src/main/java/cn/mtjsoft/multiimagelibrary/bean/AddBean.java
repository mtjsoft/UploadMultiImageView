package cn.mtjsoft.multiimagelibrary.bean;

import cn.mtjsoft.multiimagelibrary.imp.ImageInfo;

public class AddBean implements ImageInfo {
    private Object addPath;

    private boolean isAdd;

    public Object getAddPath() {
        return addPath;
    }

    public void setAddPath(Object addPath) {
        this.addPath = addPath;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    @Override
    public Object getImagePath() {
        return addPath;
    }

    @Override
    public boolean isLastAddViewData() {
        return isAdd;
    }
}
