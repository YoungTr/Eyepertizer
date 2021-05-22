/*
 * Copyright (c) 2020. vipyinzhiwei <vipyinzhiwei@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.eyepertizer.androidx.data.network.api

import com.eyepertizer.androidx.data.network.model.HomePageRecommend
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * 主页界面，主要包含：（首页，社区，通知，我的）对应的 API 接口。
 *
 * @author vipyinzhiwei
 * @since  2020/5/15
 */
interface MainPageApis {

    /**
     * 首页-推荐列表
     */
    @GET("api/v7/community/tab/rec")
    fun getHomePageRecommend(): Observable<HomePageRecommend>


}