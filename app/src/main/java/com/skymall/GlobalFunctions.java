package com.skymall;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.mrapp.android.dialog.MaterialDialog;

/**
 * Created by henryye on 5/28/17.
 */
public class GlobalFunctions {

    public static final String PASSWORD_PATTERN = "^(?![^a-zA-Z]+$)(?!\\D+$)[0-9a-zA-Z]{8,16}$";
    public static final String EMAIL_PATTERN = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
    public static final String MOBILE_PATTERN = "^(13[0-9]|14[57]|15[0-35-9]|17[6-8]|18[0-9])[0-9]{8}$";
    public static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;


    //Password verify : check if password contains both character and numbers
    public static final boolean isRightPwd(String pwd) {
        Pattern p = Pattern.compile("^(?![^a-zA-Z]+$)(?!\\D+$)[0-9a-zA-Z]{8,16}$");
        Matcher m = p.matcher(pwd);
        return m.matches();
    }

    //Mobile verify
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^(13[0-9]|14[57]|15[0-35-9]|17[6-8]|18[0-9])[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static void createDialogWithAlertMsg(Context context, int msg){
        MaterialDialog alertDialog;
        MaterialDialog.Builder dialogBuilder = new MaterialDialog.Builder(context);
        dialogBuilder.setMessage(msg);
        dialogBuilder.setPositiveButton(android.R.string.ok, null);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
    public static void createDialogWithAlertMsg(Context context, String msg){
        MaterialDialog alertDialog;
        MaterialDialog.Builder dialogBuilder = new MaterialDialog.Builder(context);
        dialogBuilder.setMessage(msg);
        dialogBuilder.setPositiveButton(android.R.string.ok, null);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }


    //判断是否安装目标应用
    public static boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName)
                .exists();
    }

    /**
     * * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法 * * 将 BD-09 坐标转换成GCJ-02 坐标 * * @param
     * bd_lat * @param bd_lon * @return
     */
    public static double[] bd09_To_Gcj02(double lat, double lon) {
        double x = lon - 0.0065, y = lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        double tempLon = z * Math.cos(theta);
        double tempLat = z * Math.sin(theta);
        double[] gps = {tempLat,tempLon};
        return gps;
    }

    public static void goByGaode(String latlng, String address, Activity activity){
        String[] location = latlng.split(",");
        //Assume latitude and longitude are not null in any case
        double[] laln =  bd09_To_Gcj02(Double.valueOf(location[0]),Double.valueOf(location[1]));
        Intent intent = new Intent("android.intent.action.VIEW",
                android.net.Uri.parse("androidamap://route?sourceApplication=softname&sname=我的位置&dlat=" + laln[0] + "&dlon=" + laln[0] + "&dname=" + "香港特别行政区深水埗区福荣街218号" + "&dev=0&m=0&t=1"));
        intent.addCategory("android.intent.category.DEFAULT");

        if(GlobalFunctions.isInstallByread("com.autonavi.minimap")){
            activity.startActivity(intent);
        }else {
            Log.e(activity.getPackageName(), activity.getResources().getString(R.string.amap_not_installed)) ;
        }
    }

    public static void goByGoogle(String latlng, String address, Activity activity){
        String[] location = latlng.split(",");
        //Assume latitude and longitude are not null in any case
        double[] laln =  bd09_To_Gcj02(Double.valueOf(location[0]),Double.valueOf(location[1]));

        if (GlobalFunctions.isInstallByread("com.google.android.apps.maps")) {
            Uri gmmIntentUri = Uri.parse("google.navigation:q="+laln[0]+","+laln[1]+", + "+ address);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            activity.startActivity(mapIntent);
        }else {
            Log.e(activity.getPackageName(), activity.getResources().getString(R.string.Googlemap_not_installed));
        }
    }

    public static void goByBaidu(String latlng, String address, Activity activity){
        Intent intent = new Intent();
        intent.setData(Uri.parse("baidumap://map/direction?origin=我的位置&destination=latlng:" + latlng + "|name:" + address + "&mode=driving&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end"));

        if(GlobalFunctions.isInstallByread("com.baidu.BaiduMap")){
            activity.startActivity(intent);
        }else {
            Log.e(activity.getPackageName(), activity.getResources().getString(R.string.baidumap_not_installed)) ;
        }
    }

    public static void pickNavApp(String latlng, String address, Activity activity){
        MaterialDialog listDialog = null;
        String baidumap = activity.getResources().getString(R.string.baidumap);
        String gaodemap = activity.getResources().getString(R.string.gaodemap);
        String googlemap = activity.getResources().getString(R.string.googlemap);

        MaterialDialog.Builder dialogBuilder = new de.mrapp.android.dialog.MaterialDialog.Builder(activity);

        final MaterialDialog finalListDialog = listDialog;
        dialogBuilder.setItems(new String[]{baidumap, gaodemap, googlemap,""}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        GlobalFunctions.goByBaidu(latlng, address, activity);
                        break;
                    case 1:
                        GlobalFunctions.goByGaode(latlng, address, activity);
                        break;
                    case 3:
                        GlobalFunctions.goByGoogle(latlng, address, activity);
                        break;
                    default:
                        finalListDialog.dismiss();
                        break;
                }
            }
        });

        listDialog = dialogBuilder.create();
        listDialog.setGravity(Gravity.CENTER);
        listDialog.show();
    }

}
