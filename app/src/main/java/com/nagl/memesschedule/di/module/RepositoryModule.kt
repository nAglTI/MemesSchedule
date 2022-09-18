package com.nagl.memesschedule.di.module

import com.nagl.memesschedule.data.source.repository.IDataRepository
import com.nagl.memesschedule.data.source.repository.DataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindNetworkRepository(networkRepository: DataRepository): IDataRepository

//    @Binds
//    abstract fun bindDataStoreRepository(dataStoreRepository: DataStoreRepository): DataStoreRepository
}
