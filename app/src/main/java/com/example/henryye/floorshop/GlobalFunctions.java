package com.example.henryye.floorshop;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by henryye on 5/28/17.
 */
public class GlobalFunctions {

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


}
