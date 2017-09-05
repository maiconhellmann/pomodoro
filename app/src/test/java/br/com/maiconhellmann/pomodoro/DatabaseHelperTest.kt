package br.com.maiconhellmann.pomodoro

import br.com.maiconhellmann.pomodoro.data.local.DatabaseHelper
import br.com.maiconhellmann.pomodoro.data.local.DbOpenHelper
import br.com.maiconhellmann.pomodoro.util.DefaultConfig
import br.com.maiconhellmann.pomodoro.util.RxSchedulersOverrideRule
import com.squareup.sqlbrite.SqlBrite
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import rx.android.schedulers.AndroidSchedulers

/**
 * Unit tests integration with a SQLite Database using Robolectric
 */
@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(DefaultConfig.EMULATE_SDK))
class DatabaseHelperTest {

    @Rule @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    val databaseHelper: DatabaseHelper by lazy {
        val dbHelper = DbOpenHelper(RuntimeEnvironment.application)
        val sqlBrite = SqlBrite.Builder()
                .build()
                .wrapDatabaseHelper(dbHelper, AndroidSchedulers.mainThread())
        DatabaseHelper(sqlBrite)
    }

}
