package com.topnet.lib.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.topnet.lib.R;
import com.topnet.lib.dialog.LoadingDialogUtils;
import com.topnet.lib.utils.ToastUtils;


/**
 * @author yangxiaoyi on 2018/6/14
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener, BaseView {

    private InputMethodManager imm;

    private ImageView ivTopBack;
    private ImageView ivTopRight;
    private TextView tvTopTitle;
    private TextView tvTopRightTxt;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setViewId());
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    public void initData() {
    }

    public void initView() {
        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();
        ivTopBack = findViewById(R.id.iv_top_back);
        ivTopRight = findViewById(R.id.iv_top_right);
        tvTopTitle = findViewById(R.id.tv_top_title);
        tvTopRightTxt = findViewById(R.id.tv_top_right_txt);

        if (null != ivTopBack) {
            ivTopBack.setOnClickListener(this);
        }

    }


    public void setImmersionBar() {
        ImmersionBar.with(this)
                .hideBar(BarHide.FLAG_SHOW_BAR)
                .statusBarColor(R.color.lib_colorPrimary)
                .navigationBarColor(R.color.lib_colorPrimary)
                .barColor(R.color.lib_colorPrimary)
                .fitsSystemWindows(true)
                .keyboardEnable(true)
                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
                .init();  //必须调用方可沉浸式
    }

    public void setImmersionBar1() {
        ImmersionBar.with(this)
                .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
                .statusBarColor(R.color.lib_colorPrimary)
                .navigationBarColor(R.color.lib_colorPrimary)
                .barColor(R.color.lib_colorPrimary)
                .fitsSystemWindows(true)
                .keyboardEnable(true)
                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
                .init();  //必须调用方可沉浸式
    }

    /**
     * 初始化布局界面
     *
     * @return
     */
    protected abstract int setViewId();


    @Override
    protected void onDestroy() {

        cancelProgressDialog();
        try {
            ImmersionBar.with(this).destroy();
        } catch (Exception e) {

        }
        super.onDestroy();

    }

    public void cancelProgressDialog() {
        LoadingDialogUtils.getInstance().dismiss();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // 如果你的app可以横竖屏切换，并且适配4.4或者emui3手机请务必在onConfigurationChanged方法里添加这句话
        ImmersionBar.with(this).init();
    }


    /**
     * 设置标题栏标题
     *
     * @param title
     */
    public void setTopTitle(String title) {
        if (tvTopTitle != null) {
            tvTopTitle.setText(title);
        }
    }

    /**
     * 设置标题栏 右边文字按钮
     *
     * @param str
     */
    public void setTopRightTxt(String str) {
        if (ivTopRight != null) {
            ivTopRight.setVisibility(View.INVISIBLE);
        }
        if (tvTopRightTxt != null) {
            tvTopRightTxt.setVisibility(View.VISIBLE);
            tvTopRightTxt.setText(str);
        }

    }

    /**
     * 标题上的返回按钮
     *
     * @param view
     */
    public void back(View view) {
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        hideSoftKeyBoard();
    }

    public void hideSoftKeyBoard() {
        View localView = getCurrentFocus();
        if (this.imm == null) {
            this.imm = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        }
        if ((localView != null) && (this.imm != null)) {
            this.imm.hideSoftInputFromWindow(localView.getWindowToken(), 2);
        }
    }

    public void toast(String str) {
        Toast toast = Toast.makeText(this, "" + str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void startAct(Class c) {
        Intent intent = new Intent(this, c);
        this.startActivity(intent);
    }


    /**
     * 显示或者隐藏标题栏 右边控件
     *
     * @param isShow
     */
    public void hideOrShowTopRight(boolean isShow) {
        if (ivTopRight != null) {
            if (isShow) {
                ivTopRight.setVisibility(View.VISIBLE);
            } else {
                ivTopRight.setVisibility(View.INVISIBLE);
            }
        }

    }

    /**
     * 设置标题栏 右边控件图片
     *
     * @param resID
     */
    public void setTopRightRes(int resID) {
        if (ivTopRight != null) {
            ivTopRight.setVisibility(View.VISIBLE);
            ivTopRight.setImageResource(resID);
        }
        if (tvTopRightTxt != null) {
            tvTopRightTxt.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.iv_top_back) {
            onTopBackClick();
        } else if (i == R.id.iv_top_right) {
            onTopRightClick();
        } else if (i == R.id.tv_top_right_txt) {
            onTopRightTxtClick();
        }
    }


    public void onTopBackClick() {
        this.finish();
    }

    public void onTopRightClick() {

    }

    public void onTopRightTxtClick() {

    }

    /**
     * 这样 APP的字体 就不会随系统字体的大小而变动了
     *
     * @return
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration newConfig = new Configuration();
        //控制字体缩放 1.0为默认
        newConfig.fontScale = 1.0f;
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            //7.0以上系统手机 显示大小 对APP的影响
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (displayMetrics.density < DisplayMetrics.DENSITY_DEVICE_STABLE / (float) DisplayMetrics.DENSITY_DEFAULT) {
                    displayMetrics.densityDpi = (int) (DisplayMetrics.DENSITY_DEVICE_STABLE * 0.92);
                } else {
                    displayMetrics.densityDpi = DisplayMetrics.DENSITY_DEVICE_STABLE;
                }
                newConfig.densityDpi = displayMetrics.densityDpi;
            }
            createConfigurationContext(newConfig);
        }
        res.updateConfiguration(newConfig, displayMetrics);
        return res;
    }


    @Override
    public void showProgess() {
        LoadingDialogUtils.getInstance().show(this, "");
    }

    @Override
    public void hideProgess() {
        LoadingDialogUtils.getInstance().dismiss();

    }

    @Override
    public void showMsg(String msg) {
        ToastUtils.show(this, msg);

    }

    @Override
    public void destory() {

    }


}
