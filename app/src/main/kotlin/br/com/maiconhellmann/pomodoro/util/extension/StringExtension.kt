package br.com.maiconhellmann.pomodoro.util.extension

fun String.Companion.empty(): String{
    return ""
}

fun String.Companion.isEmpty(text: Any?): Boolean {
    return text==null || text.toString().trim() == String.empty()
}

fun String.Companion.isNotEmpty(text: Any?): Boolean {
    return text!=null && text.toString().trim() != String.empty()
}

fun String.leftPadding(size: Int, strToAdd: String): String {
    var newString = this

    while(newString.length < size) {
        newString = strToAdd + newString
    }

    return newString
}
