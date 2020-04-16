package com.frikiplanet.nasaapod.apod.data.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.frikiplanet.nasaapod.apod.data.model.Apod;

import java.util.Date;
import java.util.List;

@Dao
public interface ApodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long saveApod(Apod apod);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> saveApods(List<Apod> apods);

    @Query("SELECT * FROM apods WHERE date = :from")
    Apod loadApod(Date from);

    @Query("SELECT * FROM apods ORDER BY date DESC")
    List<Apod> loadApods();

    @Query("SELECT * FROM apods WHERE date BETWEEN :from AND :to")
    List<Apod> loadApodsBetweenDates(Date from, Date to);

    @Delete
    int deleteApod(Apod apod);
}
