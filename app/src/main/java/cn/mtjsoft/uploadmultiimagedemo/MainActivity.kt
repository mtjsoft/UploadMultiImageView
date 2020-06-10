package cn.mtjsoft.uploadmultiimagedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list: MutableList<ImageModel> = ArrayList()
        for (i in 0..7) {
            val model = ImageModel()
            model.path = R.mipmap.android
            list.add(model)
        }

        uploadMultiImageView
            .setImageInfoList(list.toList())
            .setImageItemClickListener { position ->
                // imageview点击
                Toast.makeText(baseContext, "点击第了${position}个", Toast.LENGTH_SHORT).show()
            }
            .setImageViewLoader { context, path, imageView ->
                // 图片加载
                imageView.setImageResource(path as Int)
            }
            .setAddClickListener {
                // 新增按钮点击
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
        // 新增数据
        uploadMultiImageView.addNewData(tempList.toList())
    }
}
