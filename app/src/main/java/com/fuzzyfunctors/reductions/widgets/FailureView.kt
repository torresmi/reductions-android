package com.fuzzyfunctors.reductions.widgets

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.fuzzyfunctors.reductions.R
import com.fuzzyfunctors.reductions.widgets.remotedata.ShowsFailure
import kotlinx.android.synthetic.main.include_error_view.view.errorTextView
import kotlinx.android.synthetic.main.include_error_view.view.retryButton
import kotlin.properties.Delegates

class FailureView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle), ShowsFailure {

    interface Listener {
        fun onRetryClicked()
    }
    var listener: Listener? = null

    var showRetry: Boolean by Delegates.observable(false) { _, old, new ->
        applyIfChanged(old, new) {
            retryButton.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    var errorText: String by Delegates.observable("") { _, old, new ->
        applyIfChanged(old, new) {
            errorTextView.text = it
        }
    }

    var retryText: String by Delegates.observable("") { _, old, new ->
        applyIfChanged(old, new) {
            retryButton.text = it
        }
    }

    var retryColor: Int by Delegates.observable(Color.BLACK) { _, old, new ->
        applyIfChanged(old, new) {
            retryButton.setTextColor(it)
        }
    }

    var errorTextColor: Int by Delegates.observable(Color.BLACK) { _, old, new ->
        applyIfChanged(old, new) {
            errorTextView.setTextColor(it)
        }
    }

    init {
        View.inflate(context, R.layout.include_error_view, this)

        val theme = context.theme
        val a = theme.obtainStyledAttributes(attrs, R.styleable.FailureView, 0, 0)

        try {
            showRetry = a.getBoolean(R.styleable.FailureView_allowRetry, false)
            errorText = a.getString(R.styleable.FailureView_errorText).orEmpty()
            retryText = a.getString(R.styleable.FailureView_retryText).orEmpty()
            retryColor = a.getColor(R.styleable.FailureView_retryColor, Color.BLACK)
            errorTextColor = a.getColor(R.styleable.FailureView_errorTextColor, Color.BLACK)
        }
        finally {
            a.recycle()
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        retryButton.setOnClickListener { listener?.apply { onRetryClicked() } }
        retryButton.visibility = if (showRetry) View.VISIBLE else View.GONE

        errorTextView.text = errorText
        errorTextView.setTextColor(errorTextColor)
        retryButton.text = retryText
        retryButton.setTextColor(retryColor)
    }

    override fun setErrorMessage(message: String) {
        errorText = message
    }

    fun setErrorText(resId: Int) {
        setErrorMessage(context.getString(resId, ""))
    }

    fun setRetryText(resId: Int) {
        retryText = context.getString(resId, "")
    }

    fun setErrorTextColorResId(resId: Int) {
        errorTextColor = ContextCompat.getColor(context, resId)
    }

    fun setRetryTextColorResId(resId: Int) {
        retryColor = ContextCompat.getColor(context, resId)
    }

    private fun <A> applyIfChanged(old: A, new: A, f: (A) -> Unit) {
        if (new != old) {
            f(new)
        }
    }
}
