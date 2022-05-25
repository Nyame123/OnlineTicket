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
    private val applicationContext: Context
) {

    @Singleton
    @Provides
    fun provideContext(): Context = applicationContext

    @Singleton
    @Provides
    fun provideRoomDatabase(
        applicationContext: Context
    ): TicketDatabase =
        Room.databaseBuilder(applicationContext, TicketDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

    @Singleton
    @Provides
    fun provideTicketProvider(ticketDatabase: TicketDatabase): RoomTicketProvider =
        RoomTicketProviderImpl(ticketDatabase)


}