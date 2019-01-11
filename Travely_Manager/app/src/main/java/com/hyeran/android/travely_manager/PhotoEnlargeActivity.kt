package com.hyeran.android.travely_manager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_photo_enlarge.*

class PhotoEnlargeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_enlarge)

        var imgUrl : String = intent.getStringExtra("imgUrl")

        Glide.with(this@PhotoEnlargeActivity)
                .load(imgUrl)
                .into(iv_photo_enlarge)

        btn_close_photo_enlarge.setOnClickListener {
            finish()
        }
    }
}
