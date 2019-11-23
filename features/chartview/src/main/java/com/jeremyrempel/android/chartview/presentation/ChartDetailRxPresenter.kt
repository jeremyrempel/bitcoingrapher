package com.jeremyrempel.android.chartview.presentation

import com.github.mikephil.charting.data.Entry
import com.jeremyrempel.android.chartview.api.BlockChainApiResponse
import com.jeremyrempel.android.chartview.api.BlockChainService
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

class ChartDetailRxPresenter(
    private val service: BlockChainService,
    private val schedulerCompute: Scheduler,
    private val schedulerMain: Scheduler
) : ChartDetailPresenter {

    private val disposable = CompositeDisposable()

    private val _viewState: Subject<Lce<ChartViewState>> = BehaviorSubject.create()

    override fun requestData() {

        _viewState.onNext(Lce.Loading())

        service
            .marketPrice()
            .map(::mapToViewState)
            .subscribeOn(schedulerCompute)
            .observeOn(schedulerMain)
            .subscribe(::onData, ::onError)
            .also { disposable.add(it) }
    }

    fun onData(result: ChartViewState) {
        _viewState.onNext(Lce.Content(result))
    }

    fun onError(error: Throwable) {
        _viewState.onNext(Lce.Error(error.message ?: "Error"))
    }

    override fun clear() {
        disposable.clear()
    }

    override fun getViewState(): Observable<Lce<ChartViewState>> {
        return _viewState.hide()
    }

    fun mapToViewState(response: BlockChainApiResponse): ChartViewState {
        val data = response.values.map { v ->
            Entry(v.x.toFloat(), v.y.toFloat())
        }

        return ChartViewState(response.description, data)
    }
}
