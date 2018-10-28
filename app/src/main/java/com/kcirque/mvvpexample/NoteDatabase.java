package com.kcirque.mvvpexample;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import static com.kcirque.mvvpexample.NoteDatabase.DATABASE_VERSION;

@Database(entities = {Note.class}, version = DATABASE_VERSION)
public abstract class NoteDatabase extends RoomDatabase {
    private static NoteDatabase instance;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "note_database";

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, NoteDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(noteCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback noteCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDatabaseAsyncTask(instance).execute();

        }
    };

    public static class PopulateDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;

        public PopulateDatabaseAsyncTask(NoteDatabase db) {
            this.noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insertNote(new Note("Title 1", "Description 1"));
            noteDao.insertNote(new Note("Title 2", "Description 2"));
            noteDao.insertNote(new Note("Title 3", "Description 3"));
            noteDao.insertNote(new Note("Title 4", "Description 4"));
            return null;
        }
    }
}
