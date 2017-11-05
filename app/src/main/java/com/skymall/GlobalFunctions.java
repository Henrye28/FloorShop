package com.skymall;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;

import de.mrapp.android.dialog.MaterialDialog;

/**
 * Created by henryye on 5/28/17.
 */
public class GlobalFunctions {

    public static final String PASSWORD_PATTERN = "^(?![^a-zA-Z]+$)(?!\\D+$)[0-9a-zA-Z]{8,16}$";
    public static final String EMAIL_PATTERN = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
    public static final String MOBILE_PATTERN = "^(13[0-9]|14[57]|15[0-35-9]|17[6-8]|18[0-9])[0-9]{8}$";

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
}
