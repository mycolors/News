package com.fengniao.news.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.fengniao.news.R;
import com.fengniao.news.util.UIUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;

public abstract class BaseActivity extends AppCompatActivity {

    public final String TAG = getClass().getSimpleName();
    public Toolbar mToolbar;
    protected Disposable mDisposable;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIUtils.translucentBar(this, Color.TRANSPARENT);
        setContentView(getLayoutId());
        //   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }


    //  调用setContentView时，会调用此方法
    @Override
    public void onContentChanged() {
        super.onContentChanged();
        setToolbar();
        unbinder = ButterKnife.bind(this);
    }


    public void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void showToast(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }


    private ProgressDialog mProgressDialog;

    public void showProgressDialog(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
        }
        if (TextUtils.isEmpty(msg)) {
            msg = "正在加载数据...";
        }
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    public void cancelProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.cancel();
        }
    }


    public void jumpToActivity(Class activity) {
        Intent intent = new Intent(getActivity(), activity);
        startActivity(intent);
    }

    public void jumpToActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        setTextViewText(R.id.title, title);
    }

    public void setTextViewText(int id, CharSequence text) {
        TextView tv = (TextView) findViewById(id);
        if (tv != null) {
            tv.setText(text);
        }
    }


    public Activity getActivity() {
        return this;
    }

    public abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
