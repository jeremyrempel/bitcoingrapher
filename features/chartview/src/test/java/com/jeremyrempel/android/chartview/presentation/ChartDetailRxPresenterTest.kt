package com.jeremyrempel.android.chartview.presentation

import com.jeremyrempel.android.chartview.api.BlockChainService
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert.assertTrue
import org.junit.Test

class ChartDetailRxPresenterTest {

    @Test
    fun `test request data returns states loading and content`() {
        // setup
        val mockService: BlockChainService = mockk()
        every { mockService.marketPrice() } returns Single.just(mockk(relaxed = true))

        val testScheduler = TestScheduler()
        val presenter = ChartDetailRxPresenter(mockService, testScheduler, testScheduler)

        // test
        presenter.requestData()

        // verify
        assertTrue(presenter.getViewState().test().values()[0] is Lce.Loading<ChartViewState>)
        testScheduler.triggerActions()
        assertTrue(presenter.getViewState().test().values()[0] is Lce.Content<ChartViewState>)
    }

    @Test
    fun `test request data error returns state`() {
        // setup
        val mockService: BlockChainService = mockk()
        every { mockService.marketPrice() } returns Single.error(Exception("failwhale"))

        val testScheduler = TestScheduler()
        val presenter = ChartDetailRxPresenter(mockService, testScheduler, testScheduler)

        // test
        presenter.requestData()

        // verify
        testScheduler.triggerActions()
        assertTrue(presenter.getViewState().test().values()[0] is Lce.Error<ChartViewState>)
    }
}
