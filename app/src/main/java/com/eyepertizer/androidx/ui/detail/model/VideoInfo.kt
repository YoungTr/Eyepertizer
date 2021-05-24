package com.eyepertizer.androidx.ui.detail.model

import android.os.Parcelable
import com.eyepertizer.androidx.data.network.model.Author
import com.eyepertizer.androidx.data.network.model.Consumption
import com.eyepertizer.androidx.data.network.model.Cover
import com.eyepertizer.androidx.data.network.model.WebUrl
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoInfo(
    val videoId: Long,
    val playUrl: String,
    val title: String,
    val description: String,
    val category: String,
    val library: String,
    val consumption: Consumption,
    val cover: Cover,
    val author: Author?,
    val webUrl: WebUrl
) : Parcelable
