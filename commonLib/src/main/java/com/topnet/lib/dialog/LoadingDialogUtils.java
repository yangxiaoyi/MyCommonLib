package com.topnet.lib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.topnet.lib.R;


/**
 * @author yxy
 * @date 2018/05/22
 */
public class LoadingDialogUtils {


    private static LoadingDialogUtils loadingDialogUtils;
    private Dialog popupDialog;
    private LayoutInflater layoutInflater;
    private RelativeLayout layout;
    private RelativeLayout layoutBg;
    private String msg;
    private View loadingDialog;
    private TextView loadingTv;
    /**
     * 背景添加旋转动画效果，实现了转动动作
     **/
    private RotateAnimation rotateAnim;
    /**
     * 透明度动画效果
     **/
    private AlphaAnimation alphaAnimIn;
    private AlphaAnimation alphaAnimOut;


    public static LoadingDialogUtils getInstance() {
        if (loadingDialogUtils != null) {
            loadingDialogUtils.dismiss();
            loadingDialogUtils = null;
        }
        loadingDialogUtils = new LoadingDialogUtils();
        return loadingDialogUtils;
    }


    private void initAnim() {
        rotateAnim = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnim.setDuration(2000);
        rotateAnim.setRepeatMode(Animation.RESTART);
        rotateAnim.setRepeatCount(-1);
        rotateAnim.setInterpolator(new LinearInterpolator());
        alphaAnimIn = new AlphaAnimation(0f, 1f);
        alphaAnimIn.setFillAfter(true);
        alphaAnimIn.setDuration(200);
        alphaAnimIn.setInterpolator(new LinearInterpolator());
        alphaAnimOut = new AlphaAnimation(1f, 0f);
        alphaAnimOut.setFillAfter(true);
        alphaAnimOut.setDuration(100);
        alphaAnimOut.setInterpolator(new LinearInterpolator());

        /** 监听动作，动画结束时，隐藏LoadingColorDialog **/
        alphaAnimOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                dismiss();
            }
        });
    }

    /**
     * 判断是否显示
     *
     * @return
     */
    public boolean isShowing() {
        if (popupDialog != null && popupDialog.isShowing()) {
            return true;
        }
        return false;
    }


    /**
     * 显示
     */
    public void show(Context context, String msg) {
        try {
            dismiss();
            AppCompatActivity mActivity = (AppCompatActivity) context;
            if (!mActivity.isFinishing()) {
                layoutInflater = LayoutInflater.from(mActivity);
                this.msg = msg;
                initAnim();
                layout = (RelativeLayout) layoutInflater.inflate(R.layout.lib_dialog_loading_layout, null);
                loadingDialog = layout.findViewById(R.id.loading_dialog);
                loadingTv = layout.findViewById(R.id.loading_tv);

                if (!TextUtils.isEmpty(msg)) {
                    loadingTv.setText(msg);
                } else {
                    loadingTv.setText("");
                }

                layoutBg = layout.findViewById(R.id.bgLayout);
                layoutBg.startAnimation(alphaAnimIn);
                loadingDialog.startAnimation(rotateAnim);

                popupDialog = new Dialog(context, R.style.lib_dialog_style);

                popupDialog.setContentView(layout);
                popupDialog.setCanceledOnTouchOutside(false);
                popupDialog.show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏
     */
    public void dismiss() {
        if (popupDialog != null && popupDialog.isShowing()) {
            try {
                layoutBg.clearAnimation();
                loadingDialog.clearAnimation();
                popupDialog.dismiss();
            } catch (Exception e) {

            }

        }
    }
}