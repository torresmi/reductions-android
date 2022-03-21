package com.fuzzyfunctors.reductions.deals

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.viewpager.widget.PagerAdapter
import arrow.analysis.unsafeBlock
import com.fuzzyfunctors.reductions.R

class DealTypeAdapter(private val context: Context) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val view = inflater.inflate(R.layout.deals_view, container, false)
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }

    override fun isViewFromObject(view: View, item: Any): Boolean = view == item

    override fun getCount(): Int = DealType.values().size

    override fun getPageTitle(position: Int): CharSequence? {
        val titleRes = unsafeBlock { DealType.values()[position].titleRes }
        return context.getString(titleRes)
    }

    private enum class DealType(@StringRes val titleRes: Int) {
        TOP(R.string.deals_top_title),
        NEWEST_GAMES(R.string.deals_newest_games_title),
        LATEST_DEALS(R.string.deals_latest_deals_title),
        MOST_SAVINGS(R.string.deals_most_savings_title)
    }
}
