@[TOC](目录)

# UploadMultiImageView 多图上传组件
项目中经常会有多图上传的需求，每次都重复写，实在不是一个好办法。于是基于 **【recycleview】** 来实现一个仅仅需要 **【十几行代码】** 就可拥有拖拽排序功能的多图上传组件。

目前使用的AndroidX，不是的同学，请尽快升级吧！
# 先看效果
![效果图](https://img-blog.csdnimg.cn/20200610222609657.gif#pic_center)

[UploadMultiImageView源码传送门](https://gitee.com/mtj_java/UploadMultiImageView)
# 使用说明
## 1、在根目录 build.gradle 添加:

```java
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

```
## 2、在module项目下的build.gradle中添加：

```java
dependencies {
	        implementation 'com.gitee.mtj_java:UploadMultiImageView:1.0.0'
	}
```

## 3、在布局xml中添加组件

```java
<cn.mtjsoft.multiimagelibrary.UploadMultiImageView
        android:id="@+id/uploadMultiImageView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="#fff"
        app:addRes="@drawable/img_add"
        app:deleteRes="@drawable/img_delete"
        app:column_count="3"
        app:is_Drag="true"
        app:is_showAdd="true"
        app:item_spacing="2dp"
        app:max_count="9" />
```
# 版本及自定义属性说明
V1.0.0
--------------------------
1.0.0 属性  | 属性说明
------------- | -------------
img_height | 图片高度（dp）默认与宽度相等
item_spacing| 图片之间的间隔（dp）默认2dp
column_count|列数  默认每行3列
max_count|最多显示张数   默认 Integer.MAX_VALUE
is_showDelete|是否显示删除按钮  默认true
is_showAdd|是否显示添加按钮 默认false
deleteRes|设置删除按钮
deleteWH|删除按钮的宽高（dp）默认25dp
deleteResMargin|删除按钮的边距（dp）默认6dp
addRes|设置添加按钮
is_Drag|是否开启拖拽排序  默认false

# 代码实现（这里用kotlin写的）
## 1、Activity

```kotlin
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 循环添加几条数据
        val list: MutableList<ImageModel> = ArrayList()
        for (i in 0..7) {
            val model = ImageModel()
            model.path = R.mipmap.android
            list.add(model)
        }

        /**
        * 核心实现在这里，是不是只有仅仅十几行代码？
        */
        uploadMultiImageView
            .setImageInfoList(list.toList())
            // 所有属性都可以在代码中再设置
            // 开启拖拽排序
            .setDrag(true)
            // 设置每行3列
            .setColumns(3)
            // 显示新增按钮
            .setShowAdd(true)
            // item点击回调
            .setImageItemClickListener { position ->
                // imageview点击
                Toast.makeText(baseContext, "点击第了${position}个", Toast.LENGTH_SHORT).show()
            }
            // 图片加载
            .setImageViewLoader { context, path, imageView ->
                // 图片加载（这里自己选择图片加载框架，不做限制）
                imageView.setImageResource(path as Int)
            }
            // 新增按钮点击回调
            .setAddClickListener {
                // 模拟新增一条数据
                addNewData()
            }
            .show()
    }

    /**
     * 新增一条或多条数据
     */
    private fun addNewData() {
        val tempList: MutableList<ImageModel> = ArrayList()
        val model = ImageModel()
        model.path = R.mipmap.android
        tempList.add(model)
        // 调用新增数据
        uploadMultiImageView.addNewData(tempList.toList())
    }
}
```

## 2、数据实体类 实现 ImageInfo

```java
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
     * @return 图片地址
     */
    @Override
    public Object getImagePath() {
        return path;
    }

    /**
     * @return 固定返回 false
     */
    @Override
    public boolean isLastAddViewData() {
        return false;
    }
}
```
# 完结
怎么样？是不是十分的简洁，核心实现是不是只有仅仅十几行代码？

**本人公众号，关注一波，共同交流吧。**
![在这里插入图片描述](https://img-blog.csdnimg.cn/2019012509485178.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzI4Nzc5MDgz,size_16,color_FFFFFF,t_70)
