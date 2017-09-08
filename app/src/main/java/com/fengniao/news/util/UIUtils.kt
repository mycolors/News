package com.fengniao.news.util


import android.app.Activity
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.fengniao.news.app.AppContext

object UIUtils {

    fun showToast(msg: String) {
        Toast.makeText(AppContext.appContext, msg, Toast.LENGTH_SHORT).show()
    }


    //获取状态栏高度
    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    //沉浸式状态栏
    fun translucentBar(activity: Activity, statusColor: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            val localLayoutParams = activity.window.attributes
            //设置状态栏透明，activity全屏显示
            localLayoutParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or localLayoutParams.flags
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    // SYSTEM_UI_FLAG_LAYOUT_STABLE:防止系统栏隐藏时内容区域大小发生变化
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            setStatusBarColor(activity, statusColor)
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    fun setStatusBarColor(activity: Activity, statusColor: Int) {
        activity.window.statusBarColor = statusColor
    }

    //设置虚拟按键透明
    fun translucentNavigation(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            val localLayoutParams = activity.window.attributes
            localLayoutParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION or localLayoutParams.flags
        }
    }


    //显示或隐藏状态栏
    fun isShowStatusBar(activity: Activity, show: Boolean) {
        //        显示和隐藏方法最好对应使用，这些方法都是不同版本时期使用的，优先级可能不同，不对号使用可能不会产生作用
        if (show) {
            //            如果是动态且频繁的操作状态栏，建议使用方法1，SYSTEM_UI_FLAG_LAYOUT_STABLE标签能防止内容区域大小发生变化引起画面晃动
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            //            方法2
            //            WindowManager.LayoutParams attr = activity.getWindow().getAttributes();
            //            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            //            activity.getWindow().setAttributes(attr);
            //如果不注释下面这句话，状态栏将把界面挤下去
            /*getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);*/
            //            方法3
            //下面方法也能实现显示状态栏效果，但是过度不是很自然
            //            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
            //                    WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        } else {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    //该属性可是实现沉浸式的状态栏，点击状态栏，状态栏出现后一段时间后会自动消失，常用于视频和游戏
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            //            方法2  该方法会使虚拟按键也变成透明
            //            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            //            //隐藏状态栏
            //            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            //            activity.getWindow().setAttributes(lp);
            //            activity.getWindow().addFlags(
            //                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

            //方法3
            //下面方法也能实现显示状态栏效果，但是过度不是很自然
            //            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            //                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }


}
