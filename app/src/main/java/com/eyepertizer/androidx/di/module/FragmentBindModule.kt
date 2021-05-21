package com.eyepertizer.androidx.di.module

import com.eyepertizer.androidx.ui.home.HomePageFragment
import com.eyepertizer.androidx.ui.home.commend.CommendFragment
import com.eyepertizer.androidx.ui.home.daily.DailyFragment
import com.eyepertizer.androidx.ui.home.discovery.DiscoveryFragment
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

    @ContributesAndroidInjector
    abstract fun discoveryFragmentInjector(): DiscoveryFragment

    @ContributesAndroidInjector
    abstract fun commendFragmentInjector(): CommendFragment

    @ContributesAndroidInjector
    abstract fun dailyFragmentInjector(): DailyFragment

}