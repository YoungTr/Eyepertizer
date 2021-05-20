package com.eyepertizer.androidx.di.module

import com.eyepertizer.androidx.ui.home.HomePageFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author youngtr
 * @data 2021/5/20
 */
@Module
abstract class FragmentBindModule {

    @ContributesAndroidInjector
    abstract fun homePageFragmentInjector(): HomePageFragment

}