package com.example.boss.DI

import com.example.boss.Repositery.Repositery
import com.example.boss.Viewmodel.ChatViewmodel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class Module {


    @Singleton
    @Provides
    fun providesRepositeryInstance(): Repositery {

        return Repositery()

    }

    @Singleton
    @Provides
    fun providesViewmodelInstance(repositery: Repositery): ChatViewmodel {

        return  ChatViewmodel(repositery)

    }
}