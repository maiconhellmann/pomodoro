package br.com.maiconhellmann.pomodoro.ui.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import br.com.maiconhellmann.pomodoro.R
import br.com.maiconhellmann.pomodoro.ui.base.BaseActivity
import br.com.maiconhellmann.pomodoro.ui.history.HistoryFragment
import br.com.maiconhellmann.pomodoro.ui.pomodoro.PomodoroFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityComponent.inject(this)
        setContentView(R.layout.activity_main)

        configureViewPager()
    }

    private fun configureViewPager() {
        var tabAdapter = SimpleFragmentPagerAdapter(
                supportFragmentManager,
                listOf(PomodoroFragment(), HistoryFragment()),
                listOf(getString(R.string.new_pomodoro), getString(R.string.history)))

        view_pager.adapter = tabAdapter

        tab_layout.setupWithViewPager(view_pager)
    }

}

class SimpleFragmentPagerAdapter

constructor(fm: FragmentManager,
            private val fragmentList: List<Fragment>,
            private val titleList: List<String>): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount() = fragmentList.size

    override fun getPageTitle(position: Int): CharSequence {
        return titleList[position]
    }
}
