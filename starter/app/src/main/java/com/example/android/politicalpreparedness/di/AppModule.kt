package com.example.android.politicalpreparedness.di

import android.app.Application
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.election.ElectionRepository
import com.example.android.politicalpreparedness.election.ElectionRepositoryImpl
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.CivicsApiService
import com.example.android.politicalpreparedness.representative.RepresentativeRepository
import com.example.android.politicalpreparedness.representative.RepresentativeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    @Singleton
    fun bindElectionRepo(
        electionRepository: ElectionRepositoryImpl
    ) : ElectionRepository

    @Binds
    @Singleton
    fun bindRepresentativeRepo(
        representativeRepository: RepresentativeRepositoryImpl
    ) : RepresentativeRepository
}
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideElectionDao(application: Application): ElectionDao {
        return ElectionDatabase.getInstance(application).electionDao
    }

    @Provides
    @Singleton
    fun provideRetrofitService(): CivicsApiService {
        return CivicsApi.retrofitService
    }

}