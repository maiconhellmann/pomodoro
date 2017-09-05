package br.com.maiconhellmann.pomodoro.data.local

import android.content.ContentValues
import android.database.Cursor
import br.com.maiconhellmann.pomodoro.data.model.Pomodoro
import br.com.maiconhellmann.pomodoro.data.model.PomodoroStatus
import br.com.maiconhellmann.pomodoro.util.extension.getLong
import java.util.*

object Db {

    object PomodoroTable{
        val TABLE_NAME = "pomodoro"

        val COLUMN_ID = "_id"
        val COLUMN_START_TIME = "dat_start_time"
        val COLUMN_END_TIME = "dat_end_time"
        val COLUMN_STATUS = "ind_status"

        val CREATE = """
            CREATE TABLE $TABLE_NAME(
              $COLUMN_ID            INTEGER PRIMARY KEY,
              $COLUMN_START_TIME    REAL NOT NULL,
              $COLUMN_END_TIME      REAL NOT NULL,
              $COLUMN_STATUS        INTEGER NOT NULL
            )
        """

        fun toContentValues(pomodoro: Pomodoro): ContentValues {
            val values = ContentValues()
            values.put(PomodoroTable.COLUMN_START_TIME, pomodoro.startDateTime.time)
            values.put(PomodoroTable.COLUMN_END_TIME, pomodoro.endDateTime.time)
            values.put(PomodoroTable.COLUMN_STATUS, pomodoro.status.id)
            return values
        }

        fun parseCursor(cursor: Cursor): Pomodoro {
            return Pomodoro(
                    id = cursor.getLong(COLUMN_ID),
                    startDateTime = Date(cursor.getLong(COLUMN_START_TIME)),
                    endDateTime = Date(cursor.getLong(COLUMN_END_TIME)),
                    status = PomodoroStatus.findById(cursor.getLong(COLUMN_STATUS)))
        }
    }
}
