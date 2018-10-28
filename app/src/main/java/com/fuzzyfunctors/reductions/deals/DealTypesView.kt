package com.fuzzyfunctors.reductions.deals

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.deal_types_view.view.*

class DealTypesView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    override fun onFinishInflate() {
        super.onFinishInflate()

        viewPager.adapter = DealTypeAdapter(context)
        tabLayout.setupWithViewPager(viewPager)
    }
}
