package com.instand.andrew.tumblr_android.API.BusinessLogic;

import android.content.SharedPreferences;

import java.util.Calendar;

/**
 * Created by Andrew on 09.02.2016.
 */
public class Inizialization {

    public String getLambdaPersentCount(Integer firstCount, Integer secondCount) {
        try {
            if (secondCount > firstCount) {
                return String.valueOf((secondCount - firstCount) * 100 / firstCount) + "%";
            } else {
                return "-" + String.valueOf((secondCount * 100) / firstCount) + "%";
            }
        } catch (Exception e) {
            return "0%";
        }
    }
}
