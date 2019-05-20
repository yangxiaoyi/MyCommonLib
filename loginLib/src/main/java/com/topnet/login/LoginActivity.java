package com.topnet.login;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.topnet.lib.base.BaseActivity;
import com.topnet.lib.utils.LogUtils;

/**
 * @author yangxiaoyi
 * @date 2019/1/18
 * @describe com.topnet.login
 */
@Route(path = "/login/login")
public class LoginActivity extends BaseActivity {


    @Autowired()
    String name;
    @Autowired()
    String password;

    @Override
    protected int setViewId() {
        return R.layout.login_act_login;
    }

    @Override
    public void initView() {
        super.initView();
        setImmersionBar();
        LogUtils.e("--" + name + "   " + password);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.e("--" + name + "   " + password);
    }
}
