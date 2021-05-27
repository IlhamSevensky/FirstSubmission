package com.app.search.di

import com.app.firstsubmission.di.SearchFeatureDependencies
import com.app.search.ui.SearchFragment
import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

/**
 * Hilt cannot process annotations in feature modules and must use dagger to perform dependency injection in feature modules
 * Refer to documentation
 * - https://developer.android.com/training/dependency-injection/hilt-multi-module
 * - https://github.com/google/dagger/issues/1865
 */

@FlowPreview
@ExperimentalCoroutinesApi
@Component(dependencies = [SearchFeatureDependencies::class])
interface SearchComponent {

    fun inject(searchFragment: SearchFragment)

    @Component.Factory
    interface Factory {
        fun create(dependencies: SearchFeatureDependencies): SearchComponent
    }

}