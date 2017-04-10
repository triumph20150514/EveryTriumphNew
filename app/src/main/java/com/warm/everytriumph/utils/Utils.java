package com.warm.everytriumph.utils;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.warm.everytriumph.receiver.MyAdminReceiver;


/**
 * 工具类
 *
 * @author HUA-ZHONG-WEI
 */
public class Utils {

    /**
     * 系统可能存放温度的文件
     */
    private static String[] tempFiles = {"/sys/devices/platform/omap/omap_temp_sensor.0/temperature",
            "/sys/kernel/debug/tegra_thermal/temp_tj", "/sys/devices/system/cpu/cpu0/cpufreq/cpu_temp",
            "/sys/class/thermal/thermal_zone0/temp", "/sys/class/thermal/thermal_zone1/temp",
            "/sys/devices/platform/s5p-tmu/curr_temp", "/sys/devices/virtual/thermal/thermal_zone0/temp",
            "/sys/devices/virtual/thermal/thermal_zone1/temp",
            "/sys/devices/system/cpu/cpufreq/cput_attributes/cur_temp",
            "/sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq", "/sys/devices/platform/s5p-tmu/temperature",};

    /**
     * 连接wifi
     * <p>
     * 权限：
     * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
     * <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
     *
     * @param context
     */
    public static void startWifi(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(true);
        // if (!wifi.isWifiEnabled()) {
        // wifi.setWifiEnabled(true);
        // }
    }

    /**
     * 判断文件是否存在或能能正确读；
     *
     * @param path
     * @return
     */
    public static boolean isRightFile(String path) {
        File file = new File(path);
        if (!file.exists() || !file.canRead()) {
            return false;
        }
        return true;
    }

    /**
     * 获取温度，如果为-1，为找不到系统相关温度的文件
     *
     * @return
     */
    public static float getCPUTemp() {
        List<Float> list = new ArrayList<Float>();
        int count = 0;
        for (String tempFileName : tempFiles) {
            if (isRightFile(tempFileName)) {
                list.add(readTempFile(new File(tempFileName)));
                count++;
            }
        }
        if (count == 0) {
            return -1;
        } else {
            Collections.sort(list);// 默认为升序
            return list.get(list.size() - 1);
        }
    }

    /**
     * 从文件中读取温度
     *
     * @param ret
     * @return
     */
    private static float readTempFile(File ret) {
        float temp = 0;
        try {
            if (ret != null)

            {
                FileInputStream fis = new FileInputStream(ret);
                StringBuffer sbTemp = new StringBuffer("");
                // read temperature
                byte[] buffer = new byte[1024];
                while (fis.read(buffer) != -1) {
                    sbTemp.append(new String(buffer));
                }
                fis.close();
                // parse temp
                String sTemp = sbTemp.toString().replaceAll("[^0-9.]+", "");
                temp = Float.valueOf(sTemp);
            }
        } catch (Exception e) {
            e.printStackTrace();

            temp = -1;
        }
        return temp;
    }

    /**
     * 调节系统音量
     *
     * @param context 上下文
     * @param volume  音量大小
     * @param flags   调节呈现方式 ： FLAG_PLAY_SOUND 调整音量时播放声音 FLAG_SHOW_UI
     *                调整时显示音量条,就是按音量键出现的那个 0 表示什么也没有
     */
    public static void changeVolume(Context context, int volume, int flags) {
        try {
            AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            am.setStreamVolume(AudioManager.STREAM_RING, volume, flags);
            am.setStreamVolume(AudioManager.STREAM_MUSIC, volume, flags);
        } catch (Exception e) {
            L.e("音量改变失败 " + e.getMessage());
        }

    }

    /**
     * 切换为全屏，并强制竖屏
     *
     * @param activity
     */
    public static void fullScreen(Activity activity) {
        // 去掉标题栏
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 去掉信息栏，全屏
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 强制竖屏
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 强制横屏
        // activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * 改变屏幕亮度,改为最亮
     */
    public static void changeBrightness(Context context) {

        try {
            // 先关闭自动亮度
            Settings.System.putInt(context.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            // 调节亮度 255最亮
            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 255);
        } catch (Exception e) {
            L.e("屏幕亮度改变失败" + e.getMessage());
        }
    }

    /**
     * 设置系统休眠时间
     *
     * @param context 上下文
     * @param second  休眠时间，单位秒
     */
    public static void changeScreenOff(Context context, int second) {
        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, second * 1000);
    }

    /**
     * 时间转字符串
     *
     * @param date
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String dateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = formatter.format(date);
        return formattedDate;
    }

    /**
     * 字符串转时间
     *
     * @param value
     * @return
     * @throws Exception
     */
    @SuppressLint("SimpleDateFormat")
    public static Date stringToDate(String value) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = formatter.parse(value);
        return date;
    }

    /**
     * 返回当前时间
     *
     * @return
     */
    public static Date nowTime() {
        return new Date();
    }

    /**
     * 获取设备IMEI串
     *
     * @param context
     * @return
     */
    public static String getDeviceIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    /**
     * 计算两个日期之间间隔多少分钟
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static int getAmongMinute(Date startTime, Date endTime) {
        return (int) ((endTime.getTime() - startTime.getTime()) / 60000);
    }

    /**
     * 获取管理员权限，会弹出激活界面
     *
     * @param context
     */
    public static void getAdmin(Context context) {
        DevicePolicyManager policyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName componentName = new ComponentName(context, MyAdminReceiver.class);
        if (!policyManager.isAdminActive(componentName)) {
            Intent manager = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            manager.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
//			manager.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            manager.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "提示文字");
            context.startActivity(manager);
        }
    }

    /**
     * 获取得系统管理员权限，不会弹出激活页面
     *
     * @param context
     */
    public static void addDeviceAdmin(Context context) {
        ComponentName adminName = new ComponentName(context, MyAdminReceiver.class);
        DevicePolicyManager dpm = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        if (!dpm.isAdminActive(adminName)) {
            try {
//				dpm.setActiveAdmin(adminName, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 移除管理员权限
     *
     * @param context
     */
    public static void removeDeviceAdmin(Context context) {
        DevicePolicyManager policyManager = (DevicePolicyManager) context
                .getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName componentName = new ComponentName(context, MyAdminReceiver.class);
        policyManager.removeActiveAdmin(componentName);
    }

    /**
     * 关闭软件盘
     *
     * @param activity
     */
    public static void closeSoftKeyboard(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 恢复桌面图标
     *
     * @param context
     */
    @SuppressLint("NewApi")
    public static void recoverDesktop(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

//		// com.huawei.android.launcher 为launcher包名
//		am.clearApplicationUserData("com.huawei.android.launcher", new IPackageDataObserver.Stub() {
//			@Override
//			public IBinder asBinder() {
//				return null; // 返回null，系统重置
//			}
//
//			@Override
//			public void onRemoveCompleted(String arg0, boolean arg1) throws RemoteException {
//				Toast.makeText(context, "恢复图标", Toast.LENGTH_LONG).show();
//			}
//		});
    }

    /**
     * 删除所有联系人
     *
     * @param context
     */
    public static void deleteContacts(Context context) {
        ContentResolver cr = context.getContentResolver();
        cr.delete(
                Uri.parse(ContactsContract.RawContacts.CONTENT_URI.toString() + "?"
                        + ContactsContract.CALLER_IS_SYNCADAPTER + "=true"),
                ContactsContract.RawContacts._ID + ">0", null);

    }

    /**
     * 删除通话记录
     *
     * @param context
     */
    public static void deleteCalllog(Context context) {
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(CallLog.Calls.CONTENT_URI, null, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(CallLog.Calls._ID));
            cr.delete(CallLog.Calls.CONTENT_URI, CallLog.Calls._ID + "=?", new String[]{id + ""});
        }
        cursor.close();
    }

    /**
     * 删除指定文件
     *
     * @param path
     * @return
     */
    public static boolean deleteFile(String path) {
        boolean flag = false;
        if (path.startsWith("/system")) {
            return flag;
        }
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isFile()) {
            return flag;
        }
        file.delete();
        flag = true;
        return flag;
    }

    /**
     * 删除非系统图片文件
     *
     * @param context
     */
    public static void deleteImages(Context context) {
        List<Integer> listId = new ArrayList<Integer>();
        List<String> listPath = new ArrayList<String>();

        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA}, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String path = cursor.getString(1);
                if (!path.startsWith("/system")) {
                    listId.add(id);
                    listPath.add(path);
                }

            }
            for (int i = 0; i < listId.size(); i++) {
                deleteFile(listPath.get(i));
                cr.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        MediaStore.Images.Media._ID + "=" + listId.get(i), null);
            }
        }
        cursor.close();
    }

    /**
     * 删除非系统音频文件
     *
     * @param context
     */
    public static void deleteAudios(Context context) {
        List<Integer> listId = new ArrayList<Integer>();
        List<String> listPath = new ArrayList<String>();

        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DATA}, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String path = cursor.getString(1);
                if (!path.startsWith("/system")) {
                    listId.add(id);
                    listPath.add(path);
                }

            }
            for (int i = 0; i < listId.size(); i++) {
                deleteFile(listPath.get(i));
                cr.delete(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, MediaStore.Audio.Media._ID + "=" + listId.get(i),
                        null);
            }
        }
        cursor.close();
    }

    /**
     * 删除非系统视频文件
     *
     * @param context
     */
    public static void deleteVideos(Context context) {
        List<Integer> listId = new ArrayList<Integer>();
        List<String> listPath = new ArrayList<String>();

        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Video.Media._ID, MediaStore.Video.Media.DATA}, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String path = cursor.getString(1);
                if (!path.startsWith("/system")) {
                    listId.add(id);
                    listPath.add(path);
                }

            }
            for (int i = 0; i < listId.size(); i++) {
                deleteFile(listPath.get(i));
                cr.delete(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, MediaStore.Video.Media._ID + "=" + listId.get(i), null);
            }
        }
        cursor.close();
    }

    /**
     * 删除短信
     *
     * @param context
     */
    @SuppressLint({"InlinedApi", "NewApi"})
    public static void deleteSms(Context context) {
        ContentResolver cr = context.getContentResolver();

        Cursor cursor = cr.query(Uri.parse("content://sms"), new String[]{"_id"}, null, null, null);
        List<Integer> list = new ArrayList<Integer>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            list.add(id);

        }
        for (Integer id : list) {
            cr.delete(Uri.parse("content://sms/" + id), null, null);
        }
        cursor.close();
    }

    /**
     * 清除密码
     *
     * @param context
     */
    public static void clearLockPwd(Context context) {
//		LockPatternUtils lockPatternUtils = new LockPatternUtils(context);
//		lockPatternUtils.clearLock(UserHandle.getCallingUserId());
    }

    /**
     * 验证字符串是否可用
     *
     * @param text
     * @return 非空 true
     */
    public static boolean isUsedText(String text) {
        if (!TextUtils.isEmpty(text) && !text.equals("")) {
            return true;
        }
        return false;
    }

    /**
     * 验证集合是否可用
     *
     * @param list
     * @return 非空 true
     */
    public static boolean isUsedList(List<CharSequence> list) {
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }
}
