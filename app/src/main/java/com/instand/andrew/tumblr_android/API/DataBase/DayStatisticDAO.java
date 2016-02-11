package com.instand.andrew.tumblr_android.API.DataBase;

import android.content.Context;

import com.instand.andrew.tumblr_android.API.Entity.StatisticDay;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Andrew on 10.02.2016.
 */
public class DayStatisticDAO {
    Context context = null;

    public DayStatisticDAO(Context context) {
        this.context = context;
    }

    public void saveDayStatistic(StatisticDay statisticDay) {
        Realm realm = Realm.getInstance(context);
        realm.beginTransaction();
        realm.copyToRealm(statisticDay);
        realm.commitTransaction();
    }

    public RealmResults<StatisticDay> getAllDayStatistic() {
        Realm realm = Realm.getInstance(context);
        realm.beginTransaction();
        RealmResults<StatisticDay> statisticDays = realm.allObjects(StatisticDay.class);
        realm.commitTransaction();
        return statisticDays;
    }

    public RealmResults<StatisticDay> getDayStatisticByMonth(Integer month) {
        Realm realm = Realm.getInstance(context);
        RealmQuery query = realm.where(StatisticDay.class);
        return query.equalTo("month", month).findAll();
    }

    public RealmResults<StatisticDay> getDayStatisticByWeek(Integer week) {
        Realm realm = Realm.getInstance(context);
        RealmQuery query = realm.where(StatisticDay.class);
        return query.equalTo("weekOnTheYear", week).findAll();
    }

    public StatisticDay getDayStatisticByDay(Integer day) {
        Realm realm = Realm.getInstance(context);
        RealmQuery query = realm.where(StatisticDay.class);
        return (StatisticDay) query.equalTo("dayOnTheYear", day).findAll().first();
    }
}