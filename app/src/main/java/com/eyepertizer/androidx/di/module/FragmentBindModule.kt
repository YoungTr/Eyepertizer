package com.eyepertizer.androidx.di.module

import com.eyepertizer.androidx.ui.community.CommunityFragment
import com.eyepertizer.androidx.ui.community.commend.CommendFragmentProvider
import com.eyepertizer.androidx.ui.community.follow.FollowFragment
import com.eyepertizer.androidx.ui.community.follow.FollowFragmentProvider
import com.eyepertizer.androidx.ui.home.HomePageFragment
import com.eyepertizer.androidx.ui.home.commend.view.CommendFragment
import com.eyepertizer.androidx.ui.home.daily.DailyFragment
import com.eyepertizer.androidx.ui.home.daily.DailyFragmentProvider
import com.eyepertizer.androidx.ui.home.discovery.DiscoveryFragment
import com.eyepertizer.androidx.ui.home.discovery.DiscoveryFragmentProvider
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

    @ContributesAndroidInjector(modules = [DiscoveryFragmentProvider::class])
    abstract fun discoveryFragmentInjector(): DiscoveryFragment

    @ContributesAndroidInjector
    abstract fun commendFragmentInjector(): CommendFragment

    @ContributesAndroidInjector(modules = [DailyFragmentProvider::class])
    abstract fun dailyFragmentInjector(): DailyFragment

    @ContributesAndroidInjector
    abstract fun communityFragmentInjector(): CommunityFragment

    @ContributesAndroidInjector(modules = [CommendFragmentProvider::class])
    abstract fun communityCommendFragmentInjector(): com.eyepertizer.androidx.ui.community.commend.CommendFragment

    @ContributesAndroidInjector(modules = [FollowFragmentProvider::class])
    abstract fun followFragmentInjector(): FollowFragment

}