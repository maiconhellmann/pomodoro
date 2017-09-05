package br.com.maiconhellmann.pomodoro.util.extension

import com.squareup.sqlbrite.BriteDatabase


fun BriteDatabase.deleteAll(tableName: String): Int {
    return this.delete(tableName, null)
}