package com.example.zametkus.data.zamDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [ZamDto::class, GroupDto::class, HistoryDto::class],
    version = 1
)
abstract class ZamDatabase : RoomDatabase() {
    abstract fun dao(): ZamDao

    companion object {
        @Volatile
        private var instance: ZamDatabase? = null

        fun getDatabase(context: Context): ZamDatabase {
            return instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context): ZamDatabase {
            return Room.databaseBuilder(
                context,
                ZamDatabase::class.java,
                "MyDatabase"
            )
                .addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            //TODO Внимательно
                            // Global Scope модный метод
                            //Initial data
                            /*
                            GlobalScope.launch {
                                instance?.let {
                                    it.dao().insertInitialGroup(
                                        GroupDto(9999, "All notes", "Gray")
                                    )
                                }
                            }*/
                            /* Executor не модный метод
                            Executors.newSingleThreadExecutor().execute {

                            }*/
                        }
                    }
                )
                .build()
        }
    }
}

//Это было в fun getDatabase
/*val tempINSTANCE = INSTANCE
            if (tempINSTANCE!=null){
                return tempINSTANCE
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ZamDatabase::class.java,
                    "zamDatabase"
                ).build()
                INSTANCE = instance
                return instance
            }*/