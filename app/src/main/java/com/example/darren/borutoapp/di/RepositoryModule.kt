package com.example.darren.borutoapp.di

import android.content.Context
import com.example.darren.borutoapp.data.repository.DataStoreOperationsImpl
import com.example.darren.borutoapp.data.repository.Repository
import com.example.darren.borutoapp.domain.repository.DataStoreOperations
import com.example.darren.borutoapp.domain.use_cases.UseCases
import com.example.darren.borutoapp.domain.use_cases.get_all_goats.GetAllGoatsUseCase
import com.example.darren.borutoapp.domain.use_cases.get_all_heroes.GetAllHeroesUseCase
import com.example.darren.borutoapp.domain.use_cases.get_selected_goat.GetSelectedGoatUseCase
import com.example.darren.borutoapp.domain.use_cases.get_selected_hero.GetSelectedHeroUseCase
import com.example.darren.borutoapp.domain.use_cases.read_onboarding.ReadOnBoardingUseCase
import com.example.darren.borutoapp.domain.use_cases.save_onboarding.SaveOnBoardingUseCase
import com.example.darren.borutoapp.domain.use_cases.search_goats.SearchGoatsUseCase
import com.example.darren.borutoapp.domain.use_cases.search_heroes.SearchHeroesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDataStoreOperations(
        @ApplicationContext context: Context
    ): DataStoreOperations {
        return DataStoreOperationsImpl(context = context)
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: Repository): UseCases {
        return UseCases(
            saveOnBoardingUseCase = SaveOnBoardingUseCase(repository),
            readOnBoardingUseCase = ReadOnBoardingUseCase(repository),
            getAllHeroesUseCase = GetAllHeroesUseCase(repository),
            searchHeroesUseCase = SearchHeroesUseCase(repository),
            getSelectedHeroUseCase = GetSelectedHeroUseCase(repository),
            getAllGoatsUseCase = GetAllGoatsUseCase(repository),
            searchGoatsUseCase = SearchGoatsUseCase(repository),
            getSelectedGoatUseCase = GetSelectedGoatUseCase(repository)
        )
    }


}