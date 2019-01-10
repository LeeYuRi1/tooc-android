package com.tooc.android.tooc

import android.widget.Toast
import android.app.Activity

class BackPressClossHandler(var activity: Activity){
    private var backKeyPressedTime: Long = 0
    private var toast: Toast? = null
//    private var activity= Activity

    fun onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis()
            showGuide()
            return
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            activity.finish()
            toast!!.cancel()
        }
    }

    fun showGuide() {
        toast = Toast.makeText(activity, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG)
        toast!!.show()
    }

}