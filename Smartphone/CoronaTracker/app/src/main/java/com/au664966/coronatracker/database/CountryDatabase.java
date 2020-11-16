package com.au664966.coronatracker.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.au664966.coronatracker.model.Country;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = Country.class, version = 1)
public abstract class CountryDatabase extends RoomDatabase {
    public abstract CountryDAO countryDAO();
    /*
        The Java volatile keyword is intended to address variable visibility problems.
        By declaring the counter variable volatile all writes to the counter
        variable will be written back to main memory immediately.
        Also, all reads of the counter variable will be read directly from main memory.
        Extracted from: http://tutorials.jenkov.com/java-concurrency/volatile.html
     */
    private static volatile CountryDatabase instance;

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // We use this instead of the RoomDatabase.Callback to not expose database functions outside of
    // this class. Also we have more control on how to do things in case, making the design more flexible
    public interface InitializeCallback{
        void OnCreateDatabase();
        void OnOpenDatabase();
    }


    /**
     * Opens the database and itinitializes it by using the callback parameter
     * @param context The context to open the db
     * @param callback The db initialization, it will be called if the db needs to be initialized
     * @return The CountryDB instance
     */
    public static CountryDatabase getDatabase(final Context context,
                                              final InitializeCallback callback) {
        if(instance == null){
            synchronized (CountryDatabase.class){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            CountryDatabase.class, "country_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(new RoomDatabase.Callback(){
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);

                                    callback.OnCreateDatabase();
                                }

                                @Override
                                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                    super.onOpen(db);
                                    callback.OnOpenDatabase();
                                }
                            })
                            // .addCallback(_roomDatabaseCallback) //Used for debugging purposes
                            .build();
                }
            }
        }
        return  instance;
    }



    /**
     * Callback used to prepopulate the database. It's for debugging purposes
     * https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#12
     */
    private static RoomDatabase.Callback _roomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    // Populate the database in the background.
                    // If you want to start with more words, just add them.
                    CountryDAO dao = instance.countryDAO();
                    instance.clearAllTables();
                  /*  dao.addCountry(new Country("Canada", "CA", 142866, 9248));
                    dao.addCountry(new Country("China", "CN", 90294, 4736));
                    dao.addCountry(new Country("Denmark", "DK", 21836, 635));
                    dao.addCountry(new Country("Germany", "DE", 269048, 9376));
                    dao.addCountry(new Country("Finland", "FI", 8799, 339));
                    dao.addCountry(new Country("India", "IN", 5118253, 83198));
                    dao.addCountry(new Country("Japan", "JP", 77488, 1490));
                    dao.addCountry(new Country("Norway", "NO", 12644, 266));
                    dao.addCountry(new Country("Russian", "RU", 1081152, 18996));
                    dao.addCountry(new Country("Sweden", "SE", 87885, 5864));
                    dao.addCountry(new Country("USA", "US", 6674411, 197633));*/
                }
            });
        }
    };
}
