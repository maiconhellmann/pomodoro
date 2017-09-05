package br.com.maiconhellmann.pomodoro.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.maiconhellmann.pomodoro.R
import br.com.maiconhellmann.pomodoro.data.model.Pomodoro
import br.com.maiconhellmann.pomodoro.ui.base.BaseFragment
import br.com.maiconhellmann.pomodoro.util.extension.toast
import kotlinx.android.synthetic.main.fragment_history.*
import javax.inject.Inject

/**
 * Created by hellmanss on 9/4/17.
 */
class HistoryFragment: BaseFragment(), HistoryContract.View {

    @Inject lateinit var presenter: HistoryContract.Presenter
    @Inject lateinit var adapter: HistoryAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  inflater?.inflate(R.layout.fragment_history, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component.inject(this)
        presenter.attachView(this)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view?.let {
            recyclerView.adapter = adapter
            presenter.getPomodoroList()
        }
    }


    override fun showEmptyList() {
        adapter.dataList = emptyList()
        adapter.notifyDataSetChanged()
    }

    override fun showPomodoroList(pomodoroList: List<Pomodoro>) {
        adapter.dataList = pomodoroList
        adapter.notifyDataSetChanged()
    }

    override fun showError(t: Throwable) {
        toast(t.toString())
    }

    override fun onDetach() {
        super.onDetach()
        presenter.detachView()
    }
}