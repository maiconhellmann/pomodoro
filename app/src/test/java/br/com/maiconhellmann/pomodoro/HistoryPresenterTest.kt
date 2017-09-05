package br.com.maiconhellmann.pomodoro

import br.com.maiconhellmann.pomodoro.data.DataManager
import br.com.maiconhellmann.pomodoro.data.model.Pomodoro
import br.com.maiconhellmann.pomodoro.ui.history.HistoryContract
import br.com.maiconhellmann.pomodoro.ui.history.HistoryPresenter
import br.com.maiconhellmann.pomodoro.util.RxSchedulersOverrideRule
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import rx.Observable
import java.util.*

/**
 * Created by hellmanss on 9/5/17.
 */

@RunWith(MockitoJUnitRunner::class)
class HistoryPresenterTest {
    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    @Mock
    lateinit var view: HistoryContract.View

    @Mock
    lateinit var dataManager: DataManager

    lateinit var presenter: HistoryPresenter

    private var list: List<Pomodoro> = listOf(
            Pomodoro(0, Date(), Date()),
            Pomodoro(0, Date(), Date()),
            Pomodoro(0, Date(), Date()),
            Pomodoro(0, Date(), Date()),
            Pomodoro(0, Date(), Date())
    )

    @Before
    fun setUp() {
        presenter = HistoryPresenter(dataManager)
        presenter.attachView(view)
    }

    @After
    fun tearDown() {
        presenter.detachView()
    }

    @Test
    fun testHistory(){
        //mock
        whenever(dataManager.getPomodoroList())
                .thenReturn(Observable.just(list))

        presenter.getPomodoroList()
        verify(view, times(1)).showPomodoroList(list)
    }

    @Test
    fun testEmptyHistoryList(){
        val emptyList = emptyList<Pomodoro>()
        whenever(dataManager.getPomodoroList())
                .thenReturn(Observable.just(emptyList))

        presenter.getPomodoroList()
        verify(view, times(1)).showEmptyList()
    }

    @Test
    fun testErrorGetHistoryList(){
        val error = Throwable()

        whenever(dataManager.getPomodoroList())
                .thenReturn(Observable.error(error))

        presenter.getPomodoroList()
        verify(view, times(1)).showError(error)
    }
}