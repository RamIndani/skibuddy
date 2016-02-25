package com.learninghorizon.skibuddy.utility;

/**
 * Created by ramnivasindani on 11/25/15.
 */
public class Strings {

    public static boolean isNotEmpty(CharSequence str) {
        return !isEmpty(str);
    }

    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }
}
