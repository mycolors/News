# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\workspace\android\android_sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-optimizationpasses 5
#包明不混合大小写
-dontusemixedcaseclassnames
#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses
 #优化  不优化输入的类文件
-dontoptimize
 #预校验
-dontpreverify
 # 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#保护注解
-keepattributes *Annotation*


#如果有引用v4包可以添加下面这行
#-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.support.** { *; }
#如果引用了v4或者v7包
-dontwarn android.support.*
#忽略警告
#-ignorewarning

#如果用用到Gson解析包的，直接添加下面这几行就能成功混淆，不然会报错。
#//原因分析，可能是高版本的 sdk 通过 proguard 混淆代码时默认已经将 lib目录中的 jar 都已经添加到打包脚本中，所以不需要再次手动添加
# 混淆jar
-libraryjars libs/gson-2.8.0.jar
# 混淆类
#-keep class sun.misc.Unsafe { *; }
# 混淆包
#-keep class com.google.gson.examples.android.model.** { *; }
#dialog
-keep class me.drakeet.materialdialog.** { *; }
#加载框
-keep class com.kaopiz.kprogresshud.** { *; }
#下拉刷新
-keep class in.srain.cube.views.ptr.** { *; }
#实体类不混淆

####混淆保护自己项目的部分代码以及引用的第三方jar包library-end####


-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}



#不混淆资源类
-keepclassmembers class **.R$* {
    public static <fields>;
}
 -keep class **.R$* { *; }
