package br.com.maiconhellmann.pomodoro.ui.pomodoro

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.maiconhellmann.pomodoro.R
import br.com.maiconhellmann.pomodoro.ui.base.BaseFragment
import br.com.maiconhellmann.pomodoro.util.extension.leftPadding
import br.com.maiconhellmann.pomodoro.util.extension.toast
import kotlinx.android.synthetic.main.fragment_pomodoro.*
import javax.inject.Inject


/**
 * Created by hellmanss on 9/4/17.
 */
class PomodoroFragment: BaseFragment(), PomodoroContract.View {

    @Inject lateinit var presenter: PomodoroContract.Presenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  inflater?.inflate(R.layout.fragment_pomodoro, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component.inject(this)
        presenter.attachView(this)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view?.let {
            configureUI()
        }
    }


    private fun configureUI() {
        configureStartButton()
    }

    override fun configureStartButton(){
        tvPomodoro.text = getString(R.string.min_sec, "25","00")

        fabController.setOnClickListener {
            presenter.startTimer()
            tvPomodoro.setTextColor(Color.RED)
            fabController.setImageResource(R.drawable.ic_stop_white_24dp)
        }
    }

    override fun configureStopButton(){
        fabController.setOnClickListener {
            presenter.stopTimer()
            tvPomodoro.setTextColor(Color.GRAY)
            fabController.setImageResource(R.drawable.ic_play_arrow_white_24dp)
        }
    }

    override fun timerFinished() {
        toast("Timer finished")
        configureUI()
    }

    override fun showError(t: Throwable) {
        toast(t.toString())
    }

    override fun updateTimer(t: Long) {
        val sec = t % 60
        val min = t / 60 % 60

        tvPomodoro.text = getString(
                R.string.min_sec,
                min.toString().leftPadding(2, "0"),
                sec.toString().leftPadding(2, "0"))
    }

    override fun onDetach() {
        super.onDetach()
        presenter.stopTimer()
        presenter.detachView()
    }

    override fun showStoppedMessage() {
//        toast("Pomodoro stopped")
        configureStartButton()
    }
}