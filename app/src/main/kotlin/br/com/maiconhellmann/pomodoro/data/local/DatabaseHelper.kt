package br.com.maiconhellmann.pomodoro.data.local

import android.content.ContentValues
import br.com.maiconhellmann.pomodoro.data.model.Pomodoro
import br.com.maiconhellmann.pomodoro.data.model.PomodoroStatus
import com.squareup.sqlbrite.BriteDatabase
import rx.Emitter
import rx.Observable
import timber.log.Timber
import java.sql.SQLException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseHelper

@Inject constructor(val db: BriteDatabase) {

    fun findAllPomodoro(): Observable<List<Pomodoro>>{
        return db.createQuery(Db.PomodoroTable.TABLE_NAME,
                "SELECT * FROM ${Db.PomodoroTable.TABLE_NAME}")
                .mapToList<Pomodoro> {
                    Db.PomodoroTable.parseCursor(it)
                }
    }

    fun savePomodoro(pomodoro: Pomodoro): Observable<Pomodoro> {
        return Observable.create<Pomodoro>({emitter ->

            val transaction = db.newTransaction()
            try{
                if(pomodoro.id != 0L){
                    db.update(Db.PomodoroTable.TABLE_NAME,
                            Db.PomodoroTable.toContentValues(pomodoro),
                            Db.PomodoroTable.COLUMN_ID+ " = ?",
                            pomodoro.id.toString())
                }else{
                    val id = db.insert(Db.PomodoroTable.TABLE_NAME, Db.PomodoroTable.toContentValues(pomodoro))
                    pomodoro.id = id
                }

                transaction.markSuccessful()
                emitter.onNext(pomodoro)
            } catch (exception: SQLException) {
                Timber.e(exception)
                emitter.onError(exception)
            }
            transaction.end()

        }, Emitter.BackpressureMode.BUFFER )
    }

    fun findRunningPomodoro(): Observable<Pomodoro>{
        val sql = "SELECT * FROM ${Db.PomodoroTable.TABLE_NAME} " +
                "where ${Db.PomodoroTable.COLUMN_STATUS} = ? " +
                "order by ${Db.PomodoroTable.COLUMN_ID} desc " +
                "limit 1 "

        var cursor = db.createQuery(Db.PomodoroTable.TABLE_NAME, sql, PomodoroStatus.RUNNING.id.toString())
        return cursor.mapToOne{Db.PomodoroTable.parseCursor(it)}
    }

    fun stopRunningPomodoro(pomodoro: Pomodoro): Observable<Unit>{

        return Observable.create<Unit>({emitter ->

            val transaction = db.newTransaction()
            try{
                val cv = ContentValues()
                cv.put(Db.PomodoroTable.COLUMN_STATUS, PomodoroStatus.STOPPED.id)
                cv.put(Db.PomodoroTable.COLUMN_END_TIME, System.currentTimeMillis())

                db.update(Db.PomodoroTable.TABLE_NAME, cv,
                         "${Db.PomodoroTable.COLUMN_ID} =? ",
                        pomodoro.id.toString())

                transaction.markSuccessful()
                emitter.onCompleted()
            } catch (exception: SQLException) {
                Timber.e(exception)
                emitter.onError(exception)
            }
            transaction.end()

        }, Emitter.BackpressureMode.BUFFER )
    }
}

