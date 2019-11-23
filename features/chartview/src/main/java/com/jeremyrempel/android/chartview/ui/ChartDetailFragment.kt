package com.jeremyrempel.android.chartview.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.jeremyrempel.android.chartview.R
import com.jeremyrempel.android.chartview.presentation.ChartDetailPresenter
import com.jeremyrempel.android.chartview.presentation.ChartViewState
import com.jeremyrempel.android.chartview.presentation.Lce
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ChartDetailFragment @Inject constructor(
    private val presenter: ChartDetailPresenter
) : Fragment(R.layout.fragment_chartdetail) {

    private var disposables: CompositeDisposable = CompositeDisposable()

    companion object {
        const val DATE_FORMAT = "dd MMM"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chartView = view.findViewById<LineChart>(R.id.view_chart)
        setupChart(chartView)

        presenter.requestData()
    }

    private fun setupChart(chartView: LineChart) {

        val xAxis = chartView.xAxis
        xAxis.position = XAxis.XAxisPosition.TOP_INSIDE
        xAxis.setCenterAxisLabels(true)
        xAxis.valueFormatter = object : ValueFormatter() {

            private val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH)

            override fun getFormattedValue(value: Float): String {
                val millis = TimeUnit.HOURS.toMillis(value.toLong())
                return dateFormat.format(Date(millis))
            }
        }

        val leftAxis = chartView.axisLeft
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        leftAxis.axisMinimum = 0f
        leftAxis.yOffset = -9f

        val rightAxis = chartView.axisRight
        rightAxis.isEnabled = false
    }

    private fun showData(data: List<Entry>, description: String) {
        val chartView = requireView().findViewById<LineChart>(R.id.view_chart)

        val dataSet = LineDataSet(data, getString(R.string.price))
        val lineData = LineData(dataSet)

        chartView.data = lineData
        chartView.description.text = description
        chartView.invalidate()
    }

    fun render(viewState: Lce<ChartViewState>) {
        val view = requireView()
        val loadingView = view.findViewById<View>(R.id.view_loading)
        val errorView = view.findViewById<View>(R.id.view_error)
        val contentView = view.findViewById<View>(R.id.view_content)

        when (viewState) {
            is Lce.Loading -> {
                loadingView.visibility = View.VISIBLE
                errorView.visibility = View.GONE
                contentView.visibility = View.GONE
            }
            is Lce.Error -> {
                loadingView.visibility = View.GONE
                errorView.visibility = View.VISIBLE
                contentView.visibility = View.GONE

                view.findViewById<TextView>(R.id.txt_error_msg).text = viewState.errorMsg
            }
            is Lce.Content -> {
                loadingView.visibility = View.GONE
                errorView.visibility = View.GONE
                contentView.visibility = View.VISIBLE

                showData(viewState.result.data, viewState.result.description)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        disposables.add(
            presenter
                .getViewState()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { Timber.d("----- onNext VS $it") }
                .subscribe(
                    ::render
                ) {
                    Timber.w(it, "Error rendering view state. This shouldn't happen :(")
                }
        )
    }

    override fun onStop() {
        super.onStop()
        disposables.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.clear()
    }
}
