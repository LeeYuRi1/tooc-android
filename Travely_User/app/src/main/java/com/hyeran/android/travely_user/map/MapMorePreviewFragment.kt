package com.hyeran.android.travely_user.map

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hyeran.android.travely_user.R
import kotlinx.android.synthetic.main.fragment_map_more_preview.view.*
import org.jetbrains.anko.support.v4.startActivity

class MapMorePreviewFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_map_more_preview, container, false)

        view.btn_fragment_map_more_preview.setOnClickListener {
            startActivity<MapMoreActivity>()
        }

        return view
    }
}