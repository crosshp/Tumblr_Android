package com.instand.andrew.tumblr_android.API.BusinessLogic;

import android.content.SharedPreferences;

import java.util.Calendar;

/**
 * Created by Andrew on 09.02.2016.
 */
public class Inizialization {

    public String getLambdaPersentCount(Integer firstCount, Integer secondCount) {
        try {
            Integer result = (Integer) (((firstCount - secondCount) * 100) / firstCount);
            if (Math.abs(result) > 100) {
                return "+100%";
            } else {
                if (result < 0) {
                    return String.valueOf(-result + "%");
                } else {
                    return String.valueOf(result + "%");
                }
            }
        } catch (Exception e) {
            return "0%";
        }
    }
}
