package com.eyepertizer.androidx.ui.notification.push

import com.eyepertizer.androidx.base.viewmodel.BaseRefreshViewModel
import com.eyepertizer.androidx.data.IDataManager
import com.eyepertizer.androidx.data.network.api.MainPageApis
import com.eyepertizer.androidx.data.network.model.PushMessage

class PushViewModel constructor(dataManager: IDataManager) :

    BaseRefreshViewModel<PushMessage>(dataManager) {

    override fun getURL(): String {
        return MainPageApis.PUSHMESSAGE_URL
    }

    override fun getNextUrl(data: PushMessage?): String? {
        return data?.nextPageUrl
    }

    override suspend fun fetchData(_url: String): PushMessage? {
        return getDataManager().getPushMessage(url!!)
    }


}