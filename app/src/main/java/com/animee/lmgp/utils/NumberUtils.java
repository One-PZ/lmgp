package com.animee.lmgp.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtils {

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    public static String double2Sting(double[] doubles){
        StringBuffer s4 = new StringBuffer();
        for (double string : doubles) {
            s4.append(string);
            s4.append(",");
        }
        return s4.toString();
    }
}

