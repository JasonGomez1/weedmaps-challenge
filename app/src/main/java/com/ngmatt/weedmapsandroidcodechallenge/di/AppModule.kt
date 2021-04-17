package com.ngmatt.weedmapsandroidcodechallenge.di

import com.ngmatt.weedmapsandroidcodechallenge.data.repository.BusinessRepo
import com.ngmatt.weedmapsandroidcodechallenge.data.repository.BusinessRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun bindBusinessRepo(businessRepoImpl: BusinessRepoImpl): BusinessRepo
}
