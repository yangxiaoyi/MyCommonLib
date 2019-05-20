package com.yang.common.lib;

import android.Manifest;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.topnet.lib.base.BaseActivity;
import com.topnet.lib.permission.PermissionHelper;
import com.topnet.lib.permission.PermissionInterface;
import com.topnet.lib.utils.ToastUtils;

/**
 * @author yangxiaoyi
 * @date 2019/1/10
 * @describe com.yang.common.lib
 */
public class MainActitity extends BaseActivity implements PermissionInterface {

    private PermissionHelper mPermissionHelper;

    @Override
    protected int setViewId() {
        return R.layout.main_act;
    }

    @Override
    public void initView() {
        super.initView();
        setImmersionBar();

        mPermissionHelper = new PermissionHelper(this, this);
        mPermissionHelper.requestPermissions();


        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build("/login/login")
                        .withString("name", "121212").withString("password", "12222312312").navigation();
            }
        });
    }

    @Override
    public int getPermissionsRequestCode() {
        return 10000;
    }

    @Override
    public String[] getPermissions() {
        return new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
        };
    }

    @Override
    public void requestPermissionsSuccess() {
        ToastUtils.show(this, "权限获取成功");

    }

    @Override
    public void requestPermissionsFail() {
        ToastUtils.show(this, "权限获取失败");
    }


}
