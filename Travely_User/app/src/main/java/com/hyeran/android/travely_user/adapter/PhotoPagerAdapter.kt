package com.hyeran.android.travely_user.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.hyeran.android.travely_user.R

class PhotoPagerAdapter : PagerAdapter {
    var context : Context
    var images : Array<Int>
    lateinit var inflater : LayoutInflater

    constructor(context: Context, images:Array<Int>): super() {
        this.context = context
        this.images = images
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == "object" as RelativeLayout
    override fun getCount(): Int = images.size
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var image : ImageView
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view : View = inflater.inflate(R.layout.vp_slider_photo, container, false)
        image = view.findViewById(R.id.iv_vp_slider_photo)
        image.setBackgroundResource(images[position])
        container!!.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container!!.removeView("object" as RelativeLayout)
    }


}

//class PhotoPagerAdapter (val ctx : Context, val pageList : ArrayList<PagerModel>) : PagerAdapter (){
//    private val inflater : LayoutInflater = LayoutInflater.from(ctx)
//
//    override fun isViewFromObject(view: View, `object`: Any): Boolean {
//        return view == object
//    }
//
//    override fun getCount(): Int {
//        return pageList.size
//    }
//}

