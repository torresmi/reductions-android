package com.fuzzyfunctors.reductions.deals

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import com.fuzzyfunctors.reductions.R
import com.google.android.material.tabs.TabLayout

class DealTypesView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    override fun onFinishInflate() {
        super.onFinishInflate()

        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        viewPager.adapter = DealTypeAdapter(context)
        tabLayout.setupWithViewPager(viewPager)
    }
}
