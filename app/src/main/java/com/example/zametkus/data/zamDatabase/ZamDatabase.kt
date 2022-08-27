package com.example.zametkus.data.zamDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ZamDto::class],
    version = 1
)
abstract class ZamDatabase:RoomDatabase() {
    abstract fun dao():ZamDao

    companion object{
        @Volatile
        private var INSTANCE:ZamDatabase? = null

        fun getDatabase(context: Context):ZamDatabase{
            val tempINSTANCE = INSTANCE
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
            }
        }
    }
}