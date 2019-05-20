package com.topnet.lib.widget;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

/**
 * 验证码计时器
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/16 0016 10:47
 *
 * @author heyingying
 */
public class CaptchaTimer extends CountDownTimer {

    private TextView btnGetCaptcha;

    public CaptchaTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    /**
     * @param millisInFuture    总时间  60*1000
     * @param countDownInterval 间隔时间  1000
     * @param btnGetCaptcha
     */
    public CaptchaTimer(long millisInFuture, long countDownInterval, TextView btnGetCaptcha) {
        super(millisInFuture, countDownInterval);
        this.btnGetCaptcha = btnGetCaptcha;
    }

    @Override
    public void onFinish() {
        btnGetCaptcha.setText("重新获取");
        btnGetCaptcha.setEnabled(true);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        //strategy1是一个TextView
        String texts = millisUntilFinished / 1000L + "s";
        btnGetCaptcha.setText(texts);
        SpannableStringBuilder builder1 = new SpannableStringBuilder(btnGetCaptcha.getText().toString());
        //设置前景色为蓝色
        ForegroundColorSpan blue = new ForegroundColorSpan(Color.GRAY);
        btnGetCaptcha.setEnabled(false);
    }


}