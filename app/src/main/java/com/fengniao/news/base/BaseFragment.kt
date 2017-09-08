package com.fengniao.news.base


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import butterknife.ButterKnife


/**
 * A simple [Fragment] subclass.
 */
abstract class BaseFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(setLayoutId(), container, false)
        ButterKnife.bind(this, view)
        return view
    }

    abstract fun setLayoutId(): Int

}// Required empty public constructor
