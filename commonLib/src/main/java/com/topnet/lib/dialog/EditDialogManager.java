package com.topnet.lib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.InputFilter;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.topnet.lib.R;
import com.topnet.lib.utils.StringUtils;
import com.topnet.lib.utils.SystemUtils;


/**
 * 提示
 * @author yangxiaoyi 2018/6/14
 */

public class EditDialogManager implements View.OnClickListener {

    private Context context;
    private Dialog dialog;

    private Button ok, cancel;
    private EditText edt;
    private TextView title;

    private EditDialogListen dao;

    public void setEditDialogListen(EditDialogListen dao) {
        this.dao = dao;
    }

    private boolean isEditNum;


    public EditDialogManager(Context context, boolean isEditNum) {
        this.context = context;
        this.isEditNum = isEditNum;
        initDialog();
        initView();
    }

    private void initDialog() {
        if (dialog == null) {
            dialog = new Dialog(context, R.style.lib_dialog_style);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.lib_edit_dialog_layout);
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

        ok = dialog.findViewById(R.id.ok);
        ok.setVisibility(View.VISIBLE);
        cancel = (Button) dialog.findViewById(R.id.cancel);
        edt = (EditText) dialog.findViewById(R.id.edt);
        if (isEditNum) {
            edt.setSingleLine(true);
            edt.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
            edt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        }
        title = (TextView) dialog.findViewById(R.id.title);
        title.setVisibility(View.GONE);
        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }

    public void show() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }

    }


    /**
     * 隐藏 确定按钮
     */
    public void hideOkBtn() {
        if (ok != null) {
            ok.setVisibility(View.GONE);

        }
    }

    /**
     * 隐藏 取消按钮
     */
    public void hideCancelBtn() {
        if (cancel != null) {
            cancel.setVisibility(View.GONE);

        }
    }

    /**
     * 隐藏 Title
     */
    public void hideTitle() {
        if (title != null) {
            title.setVisibility(View.GONE);
        }
    }

    /**
     * 关闭Dialog
     */
    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;

        }

    }


    /**
     * 设置内容
     *
     * @param str
     */
    public void setDialogContant(String str) {
        if (edt != null) {
            edt.setText(str);
        }
    }

    /**
     * 设置取消按钮
     *
     * @param str
     */
    public void setDialogCancel(String str) {
        if (cancel != null) {
            cancel.setText(str);
        }
    }

    /**
     * 设置确定按钮
     *
     * @param str
     */
    public void setDialogOk(String str) {
        if (ok != null) {
            ok.setText(str);
        }
    }

    /**
     * 设置Dialog Title
     *
     * @param str
     */
    public void setDialogTitle(String str) {
        if (title != null) {
            title.setText(str);
            title.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.cancel) {
            if (dao != null) {
                dao.cancleEditClickListen();
            }
            SystemUtils.hideSoftInput(context, edt);
            dismiss();

        } else if (i == R.id.ok) {
            if (dao != null) {
                if (StringUtils.checkTextIsEmpty(edt)) {
                    if (isEditNum) {
                        dao.okEditClickListen("0");
                    } else {
                        dao.okEditClickListen("");
                    }
                } else {
                    dao.okEditClickListen(edt.getText().toString());
                }

            }
            SystemUtils.hideSoftInput(context, edt);
            dismiss();

        } else {
        }

    }

    public interface EditDialogListen {

        /**
         * 点击Ok按钮
         * @param str
         */
        void okEditClickListen(String str);

        /**
         * 取消按钮
         */
        void cancleEditClickListen();

    }

}
