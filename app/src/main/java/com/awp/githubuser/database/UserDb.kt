package com.awp.githubuser.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [UserFavoriteData::class],
    version = 1
)

abstract class UserDb: RoomDatabase() {

    companion object{
        var INITIATE : UserDb? = null
        fun getDatabase(context: Context): UserDb?{
            if(INITIATE == null) {
                synchronized(UserDb::class){
                    INITIATE = Room.databaseBuilder(context.applicationContext, UserDb::class.java, "user_database").build()
                }
            }
            return INITIATE
        }
    }

    abstract fun favoriteUserDao(): FavoriteUserDao
}