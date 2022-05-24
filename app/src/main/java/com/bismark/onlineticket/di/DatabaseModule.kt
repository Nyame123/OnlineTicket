package com.bismark.onlineticket.di

import android.content.Context
import androidx.room.Room
import com.bismark.onlineticket.data_layer.DATABASE_NAME
import com.bismark.onlineticket.data_layer.TicketDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule constructor(
    applicationContext: Context
) {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext applicationContext: Context): TicketDatabase =
        Room.databaseBuilder(applicationContext, TicketDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideTicketProvider(ticketDatabase: TicketDatabase): RoomTicketProvider =
        RoomTicketProviderImpl(ticketDatabase)
}