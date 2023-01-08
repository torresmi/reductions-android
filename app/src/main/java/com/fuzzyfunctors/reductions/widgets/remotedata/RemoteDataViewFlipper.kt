package com.fuzzyfunctors.reductions.widgets.remotedata

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ViewFlipper
import androidx.annotation.LayoutRes
import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import com.fuzzyfunctors.reductions.R

class RemoteDataViewFlipper @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : ViewFlipper(context, attrs), RemoteDataRenderer {

    var notAskedView: Option<View>
    var loadingView: View
    var failureView: View
    lateinit var successView: View

    private val successViewSpecified: Boolean
    private val defaultResId = 0

    init {
        val theme = context.theme
        val a = theme.obtainStyledAttributes(attrs, R.styleable.RemoteDataView, 0, 0)

        try {
            val notAskedId = a.getResourceId(R.styleable.RemoteDataView_notAskedView, defaultResId)
            val loadingId = a.getResourceId(
                R.styleable.RemoteDataView_loadingView,
                R.layout.center_loading_view,
            )
            val successId = a.getResourceId(R.styleable.RemoteDataView_successView, defaultResId)
            val failureId = a.getResourceId(R.styleable.RemoteDataView_failureView, defaultResId)

            successViewSpecified = successId != defaultResId

            val inflater = LayoutInflater.from(context)

            failureView = createFailureView(context, failureId, inflater)
            loadingView = inflater.inflate(loadingId, this, false)
            notAskedView = createOptionalNotAskedView(notAskedId, inflater)

            addView(failureView, ViewType.FAILURE.ordinal)
            addView(loadingView, ViewType.LOADING.ordinal)

            addSpecifiedSuccessView(inflater, successId)
        } finally {
            a.recycle()
        }
    }

    private fun addSpecifiedSuccessView(inflater: LayoutInflater, successId: Int) {
        if (successViewSpecified) {
            successView = inflater.inflate(successId, this, false)
            addView(successView, ViewType.SUCCESS.ordinal)

            addNotAskedView()
        }
    }

    private fun createOptionalNotAskedView(
        notAskedId: Int,
        inflater: LayoutInflater,
    ): Option<View> {
        return if (notAskedId != defaultResId) {
            val view = inflater.inflate(notAskedId, this, false)
            Some(view)
        } else {
            None
        }
    }

    private fun createFailureView(
        context: Context,
        failureId: Int,
        inflater: LayoutInflater,
    ): View {
        return if (failureId != defaultResId) {
            inflater.inflate(failureId, this, false)
        } else {
            FrameLayout(context)
        }
    }

    fun setNotAskedView(@LayoutRes resId: Int) {
        when (notAskedView) {
            is Some -> {
                removeViewAt(ViewType.NOT_ASKED.ordinal)
            }
        }
        val inflater = LayoutInflater.from(context)
        notAskedView = createOptionalNotAskedView(resId, inflater)
        addNotAskedView()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        if (!successViewSpecified) {
            successView = getChildAt(ViewType.SUCCESS.ordinal)
                ?: throw IllegalArgumentException("RemoteDataFlipper needs a success view")

            addNotAskedView()
        }
    }

    override fun showNotAskedView() {
        displayedChild = when (notAskedView) {
            is Some -> {
                ViewType.NOT_ASKED.ordinal
            }
            is None -> {
                // Default to success view if not asked and no special view is provided
                ViewType.SUCCESS.ordinal
            }
        }
    }

    override fun showLoading() {
        displayedChild = ViewType.LOADING.ordinal
    }

    override fun showSuccess() {
        displayedChild = ViewType.SUCCESS.ordinal
    }

    override fun showFailure(failureMessage: String?) {
        failureMessage?.let {
            val currentFailureView = failureView
            if (currentFailureView is ShowsFailure) {
                currentFailureView.setErrorMessage(it)
            }
        }
        displayedChild = ViewType.FAILURE.ordinal
    }

    private fun addNotAskedView() {
        val notAsked = notAskedView
        when (notAsked) {
            is Some -> {
                addView(notAsked.value, ViewType.NOT_ASKED.ordinal)
            }
        }
    }

    private enum class ViewType {
        FAILURE,
        LOADING,
        SUCCESS,
        NOT_ASKED,
    }
}
