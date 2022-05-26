package com.example.onthicuoiky.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.onthicuoiky.dao.UserDao;
import com.example.onthicuoiky.model.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppdataBase extends RoomDatabase {

    public abstract UserDao UserDAO();

    //variable check init database
    private static AppdataBase INSTANCE;

    //func get database checked by INSTANCE variable
    public static AppdataBase getDatabase(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppdataBase.class, "users_database")
                    .allowMainThreadQueries().build();
        }

        return INSTANCE;
    }

}
