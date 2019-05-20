package com.topnet.lib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.topnet.lib.R;


/**
 * 提示Dialog
 *
 * @author YXY
 * @date 7/26 0026
 */

public class TiShiDialogManager implements View.OnClickListener {

    private Context context;
    private Dialog dialog;
    private TextView contentTxt;
    private Button okBtn;
    private Button cancelBtn;
    private TiShiDialogListen dao;

    public void setTiShiDialogListen(TiShiDialogListen dao) {
        this.dao = dao;
    }

    public void setContextTxt(String contextTxt) {
        if (contentTxt != null) {
            contentTxt.setText(contextTxt);
        }
    }

    public void setOkBtnTxt(String okTxt) {
        if (okBtn != null) {
            okBtn.setText(okTxt);
        }
    }

    public void setCancelBtnTxt(String cancelTxt) {
        if (cancelBtn != null) {
            cancelBtn.setText(cancelTxt);
        }
    }

    public void hideCancelBtn() {
        if (cancelBtn != null) {
            cancelBtn.setVisibility(View.GONE);
        }
    }


    public TiShiDialogManager(Context context) {
        this.context = context;
        initDialog();
        initView();
    }

    private void initDialog() {
        if (dialog == null) {
            dialog = new Dialog(context, R.style.lib_dialog_style);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.lib_dialog_tishi_layout);
            Window window = dialog.getWindow();
            window.setGravity(Gravity.CENTER);
            WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(dm);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = dm.widthPixels - 100;
            window.setAttributes(lp);
        }
    }


    private void initView() {
        contentTxt = dialog.findViewById(R.id.content_txt);
        okBtn = dialog.findViewById(R.id.ok_btn);
        okBtn.setOnClickListener(this);
        cancelBtn = dialog.findViewById(R.id.cancel_btn);
        cancelBtn.setOnClickListener(this);

    }

    public void show() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.cancel_btn) {
            if (dao != null) {
                dao.cancelClickListen();
            }
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } else if (view.getId() == R.id.ok_btn) {
            if (dao != null) {
                dao.okClickListen();
            }
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    public interface TiShiDialogListen {
        /**
         * 监听对话框中的确定按钮
         */
        void okClickListen();

        /**
         * 监听对话框中的取消按钮
         */
        void cancelClickListen();

    }

}
