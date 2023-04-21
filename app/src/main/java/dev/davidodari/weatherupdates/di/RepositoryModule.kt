package dev.davidodari.weatherupdates.di

import dev.davidodari.weatherupdates.core.api.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dev.davidodari.weatherupdates.data.DefaultWeatherRepository

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun bindWeatherRepository(weatherRepository: DefaultWeatherRepository): WeatherRepository

}
