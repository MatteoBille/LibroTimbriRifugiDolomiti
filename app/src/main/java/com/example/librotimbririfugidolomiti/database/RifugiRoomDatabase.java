package com.example.librotimbririfugidolomiti.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.librotimbririfugidolomiti.database.Dao.DatabaseCondivisioneLibroDao;
import com.example.librotimbririfugidolomiti.database.Dao.DatabasePersoneDao;
import com.example.librotimbririfugidolomiti.database.Dao.DatabaseRifugiDao;
import com.example.librotimbririfugidolomiti.database.Dao.DatabaseVisiteRifugiDao;
import com.example.librotimbririfugidolomiti.database.Entity.CondivisioneLibro;
import com.example.librotimbririfugidolomiti.database.Entity.Persona;
import com.example.librotimbririfugidolomiti.database.Entity.Rifugio;
import com.example.librotimbririfugidolomiti.database.Entity.VisitaRifugio;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {
        Rifugio.class,
        Persona.class,
        VisitaRifugio.class,
        CondivisioneLibro.class
}, version = 1, exportSchema = false)
public abstract class RifugiRoomDatabase extends RoomDatabase {

    public abstract DatabaseVisiteRifugiDao visiteRifugiDao();
    public abstract DatabasePersoneDao personeDao();

    public abstract DatabaseRifugiDao rifugiDao();

    public abstract DatabaseCondivisioneLibroDao condivisioneLibroDao();

    private static volatile RifugiRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static RifugiRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RifugiRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RifugiRoomDatabase.class, "RifugiDolomiti.db").createFromAsset("database/RifugiDolomiti.db").allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}