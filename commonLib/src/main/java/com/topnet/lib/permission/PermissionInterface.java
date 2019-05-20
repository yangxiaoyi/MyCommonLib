package com.topnet.lib.permission;

/**
 * 权限请求接口
 *
 * @author dway
 * @date 2018/1/10
 */
public interface PermissionInterface {

    /**
     * 可设置请求权限请求码
     * @return
     */
    int getPermissionsRequestCode();

    /**
     * 设置需要请求的权限
     * @return
     */
    String[] getPermissions();

    /**
     * 请求权限成功回调
     */
    void requestPermissionsSuccess();

    /**
     * 请求权限失败回调
     */
    void requestPermissionsFail();

}