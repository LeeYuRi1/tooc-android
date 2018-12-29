package com.hyeran.android.travely_user.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.hyeran.android.travely_user.MainActivity
import com.hyeran.android.travely_user.R
import kotlinx.android.synthetic.main.dialog_reserve_cancel_password.*


class ReserveCancelPasswordDialog(val ctx : Context?, val password : String?) : Dialog(ctx){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setBackgroundDrawable(ColorDrawable(Color.GRAY))
        setContentView(R.layout.dialog_reserve_cancel_password)

        var mEtPassword = findViewById(R.id.et_input_password_reserve_cancel) as EditText
        mEtPassword.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)

        btn_reserve_cancel_password_confirm.setOnClickListener{

            if(password == mEtPassword.text.toString()) {
                dismiss()
                ReserveCancelPasswordConfirmDialog(context).show()
            }
            else {
                Toast.makeText(ctx,"비밀번호가 틀렸습니다.",Toast.LENGTH_LONG).show()
                dismiss()
            }
        }
    }

}