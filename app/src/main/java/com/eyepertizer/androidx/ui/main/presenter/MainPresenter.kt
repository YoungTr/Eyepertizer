package com.eyepertizer.androidx.ui.main.presenter

import com.eyepertizer.androidx.base.presenter.BaseMvpPresenter
import com.eyepertizer.androidx.data.AppDataManager
import com.eyepertizer.androidx.ui.main.view.MainMvpView
import javax.inject.Inject

class MainPresenter<V : MainMvpView> @Inject constructor(dataManager: AppDataManager) :
    BaseMvpPresenter<V>(dataManager), MainMvpPresenter<V> {

}