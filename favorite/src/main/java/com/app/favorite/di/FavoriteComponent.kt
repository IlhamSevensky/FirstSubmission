package com.app.favorite.di

import com.app.favorite.ui.FavoriteFragment
import com.app.firstsubmission.di.FavoriteFeatureDependencies
import dagger.Component

/**
 * Hilt cannot process annotations in feature modules and must use dagger to perform dependency injection in feature modules
 * Refer to documentation
 * - https://developer.android.com/training/dependency-injection/hilt-multi-module
 * - https://github.com/google/dagger/issues/1865
 */

@Component(dependencies = [FavoriteFeatureDependencies::class])
interface FavoriteComponent {

    fun inject(favoriteFragment: FavoriteFragment)

    @Component.Factory
    interface Factory {
        fun create(dependencies: FavoriteFeatureDependencies): FavoriteComponent
    }

}