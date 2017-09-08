package com.fengniao.news.ui.contract

import com.fengniao.news.base.BasePresent


interface ZhiHuContract {

    interface Present :BasePresent{

        fun getZhiHuData(date:String)

    }


    interface View {



    }
}