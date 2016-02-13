package com.instand.andrew.tumblr_android.API.DataBase;

import android.content.Context;

import com.instand.andrew.tumblr_android.API.Entity.StatisticDay;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Andrew on 10.02.2016.
 */
public class DayStatisticDAO {
    Context context = null;
    Realm realm = null;

    public DayStatisticDAO(Context context) {
        this.context = context;
        RealmConfiguration config = new RealmConfiguration.Builder(context).build();
        Realm.setDefaultConfiguration(config);
        realm = Realm.getInstance(config);
    }

    public void saveDayStatistic(StatisticDay statisticDay) {
        realm.beginTransaction();
        realm.copyToRealm(statisticDay);
        realm.commitTransaction();
    }
    public void deleteAllDayStatistic() {
        realm.beginTransaction();
        realm.clear(StatisticDay.class);
        realm.commitTransaction();
    }

    public RealmResults<StatisticDay> getAllDayStatistic() {
        realm.beginTransaction();
        RealmResults<StatisticDay> statisticDays = realm.allObjects(StatisticDay.class);
        realm.commitTransaction();
        return statisticDays;
    }

    public StatisticDay getDayStatisticByMonth(Integer month, Integer year) {
        RealmQuery query = realm.where(StatisticDay.class);
        StatisticDay statisticDay = null;
        statisticDay = (StatisticDay) query.equalTo("month", month).equalTo("year", year).findFirst();
        return statisticDay;
    }

    public StatisticDay getDayStatisticByWeek(Integer week, Integer year) {
        RealmQuery query = realm.where(StatisticDay.class);
        StatisticDay statisticDay = null;
        statisticDay = (StatisticDay) query.equalTo("weekOnTheYear", week).equalTo("year", year).findFirst();
        return statisticDay;
    }

    public StatisticDay getDayStatisticByDay(Integer day, Integer year) {
        RealmQuery query = realm.where(StatisticDay.class);
        StatisticDay statisticDay = null;
        try {
            statisticDay = (StatisticDay) query.lessThan("dayOnTheYear", day).equalTo("year", year).findAll().last();
        } catch (Exception e) {
            return statisticDay;
        }
        return statisticDay;
    }
}