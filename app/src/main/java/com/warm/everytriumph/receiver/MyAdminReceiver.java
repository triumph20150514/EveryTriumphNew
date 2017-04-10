package com.warm.everytriumph.receiver;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;


public class MyAdminReceiver extends DeviceAdminReceiver {

	@Override
	public DevicePolicyManager getManager(Context context) {
		return super.getManager(context);
	}

	@Override
	public ComponentName getWho(Context context) {
		return super.getWho(context);
	}

	@Override
	public CharSequence onDisableRequested(Context context, Intent intent) {
		return super.onDisableRequested(context, intent);
	}

	/**
	 * 禁用
	 */
	@Override
	public void onDisabled(Context context, Intent intent) {
		super.onDisabled(context, intent);
	}

	/**
	 * 激活
	 */
	@Override
	public void onEnabled(Context context, Intent intent) {
		super.onEnabled(context, intent);
	}

	/**
	 * 密码改变
	 */
	@Override
	public void onPasswordChanged(Context context, Intent intent) {
		super.onPasswordChanged(context, intent);
//		Utils.clearLockPwd(context);
	}

	@Override
	public void onPasswordExpiring(Context context, Intent intent) {
		super.onPasswordExpiring(context, intent);
	}

	@Override
	public void onPasswordFailed(Context context, Intent intent) {
		super.onPasswordFailed(context, intent);
	}

	@Override
	public void onPasswordSucceeded(Context context, Intent intent) {
		super.onPasswordSucceeded(context, intent);
	}

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		super.onReceive(arg0, arg1);
	}
}
