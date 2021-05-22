package com.eyepertizer.androidx.ui.main.presenter

import com.eyepertizer.androidx.base.presenter.BaseMvpPresenter
import com.eyepertizer.androidx.data.AppDataManager
import com.eyepertizer.androidx.ui.main.view.MainMvpView
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainPresenter<V : MainMvpView> @Inject constructor(
    dataManager: AppDataManager,
    compositeDisposable: CompositeDisposable,
) :
    BaseMvpPresenter<V>(dataManager, compositeDisposable), MainMvpPresenter<V> {

}