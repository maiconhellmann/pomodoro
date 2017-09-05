package br.com.maiconhellmann.pomodoro.ui.history

import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.maiconhellmann.pomodoro.R
import br.com.maiconhellmann.pomodoro.data.model.Pomodoro
import br.com.maiconhellmann.pomodoro.util.extension.gone
import br.com.maiconhellmann.pomodoro.util.extension.leftPadding
import br.com.maiconhellmann.pomodoro.util.extension.visible
import kotlinx.android.synthetic.main.fragment_pomodoro.*
import kotlinx.android.synthetic.main.item_history_pomodoro.view.*
import rx.Observable
import rx.subjects.PublishSubject
import javax.inject.Inject


class HistoryAdapter
@Inject
constructor() : RecyclerView.Adapter<HistoryAdapter.PomodoroViewHolder>() {

    private val clickSubject = PublishSubject.create<Pomodoro>()
    val clickEvent: Observable<Pomodoro> = clickSubject

    var  dataList: List<Pomodoro> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PomodoroViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_history_pomodoro, parent, false)
        return PomodoroViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PomodoroViewHolder, position: Int) {
        val data = dataList[position]

        val now = System.currentTimeMillis()
        val header = DateUtils.getRelativeTimeSpanString(data.startDateTime.time, now, DateUtils.DAY_IN_MILLIS)

        if(position > 0){
            val dataPrevius = dataList[position-1]

            val previousHeader = DateUtils.getRelativeTimeSpanString(dataPrevius.startDateTime.time, now, DateUtils.DAY_IN_MILLIS)

            if(previousHeader != header){
                holder.itemView.tvHeader.visible()
            }else{
                holder.itemView.tvHeader.gone()
            }
        }else{
            holder.itemView.tvHeader.visible()
        }

        holder.itemView.tvHeader.text = header

        val relativeTimeStarted = DateUtils.getRelativeTimeSpanString(data.startDateTime.time).toString()
        holder.itemView.tvElapsedTime.text = relativeTimeStarted

        holder.itemView.tvStatus.text = data.status.name

        val elapsedTime = (data.endDateTime.time - data.startDateTime.time)/1000
        val sec = elapsedTime % 60
        val min = elapsedTime / 60 % 60

        holder.itemView.tvTime.text = holder.itemView.context.getString(
                R.string.min_sec,
                min.toString().leftPadding(2, "0"),
                sec.toString().leftPadding(2, "0"))

    }

    override fun getItemCount(): Int = dataList.size

    inner class PomodoroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener{
                clickSubject.onNext(dataList[layoutPosition])
            }
        }
    }
}
