package com.antroid.nbateamviewer.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.antroid.nbateamviewer.database.converter.PlayersTypeConverter;

import static com.antroid.nbateamviewer.constants.ConstantsKt.NBA_DATABASE_NAME;


@Database(entities = {TeamsListTable.class}, version = 1, exportSchema = false)

@TypeConverters({PlayersTypeConverter.class})
public abstract class NBADatabase extends RoomDatabase {

    private final static Object LOCK = new Object();
    private static NBADatabase instance;

    public abstract TeamsDao teamsDao();

    public static NBADatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                instance = Room.databaseBuilder(
                        context.getApplicationContext()
                        ,NBADatabase.class,
                        NBA_DATABASE_NAME)
                        .build();
            }
        }
        return instance;
    }
}
