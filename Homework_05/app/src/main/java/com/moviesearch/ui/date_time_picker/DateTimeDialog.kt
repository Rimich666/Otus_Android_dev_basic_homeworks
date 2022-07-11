package com.moviesearch.ui.date_time_picker

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.moviesearch.R
import kotlinx.coroutines.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class SelectedDateTime(){
    var dateSelected = false
    var timeSelected = false
    var selected = false
    var date: LocalDate = LocalDate.now()
    var time: LocalTime = LocalTime.of(0,0,0)
    lateinit var dateTime: LocalDateTime
    var dateColor = R.color.red
    var timeColor = R.color.red
    fun setDate(year: Int, month: Int, day: Int){
        date = LocalDate.of(year, month + 1, day)
        setDateTime()
        dateColor = R.color.green
        dateSelected = true
        selected = dateSelected && timeSelected
    }
    fun setTime(hour:Int, minute: Int, second: Int){
        time = LocalTime.of(hour, minute, second)
        timeColor = R.color.green
        timeSelected = true
        selected = dateSelected && timeSelected
        setDateTime()
    }
    fun set(){
        if (!dateSelected) date = LocalDate.now()
        if (!timeSelected) time = LocalTime.now()
    }
    private fun setDateTime(){
        dateTime = LocalDateTime.of(date, time)
    }
    fun dateStr():String{
        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    }

    fun  timeStr(): String{
        return time.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
    }

}

@RequiresApi(Build.VERSION_CODES.O)
class DateTimeDialog : DialogFragment() {
    private lateinit var btnOK: Button
    private lateinit var btnCancel: Button
    private lateinit var twDate: TextView
    private lateinit var twTime: TextView
    private lateinit var timePicker: TimePicker
    private lateinit var datePicker: DatePicker
    private lateinit var image: ImageView
    private lateinit var xodiki: Job
    private lateinit var pulsar: Job
    lateinit var currentView: View
    private var selection = SelectedDateTime()
    private val scope = CoroutineScope(Dispatchers.Default)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.date_time_dialog, container, false)
        btnOK = view.findViewById(R.id.button_ok_date_time)
        btnCancel = view.findViewById(R.id.button_cancel_date_time)
        twDate = view.findViewById(R.id.text_date)
        twTime = view.findViewById(R.id.text_time)
        timePicker = view.findViewById(R.id.time_picker)
        datePicker = view.findViewById(R.id.date_picker)
        image = view.findViewById(R.id.image_clock_alarm)
        btnOK.setOnClickListener{ clickOk() }
        btnCancel.setOnClickListener{ clickCancel() }
        twDate.setOnClickListener { datePickerShow() }
        twTime.setOnClickListener { timePickerShow() }
        xodiki = scope.launch { xodiki() }
        xodiki.start()
        pulsar = scope.launch { pulsar() }
        pulsar.start()
        currentView = image

        datePicker.setOnDateChangedListener{ _, year, month, day -> dateChanged(year, month, day)}
        timePicker.setOnTimeChangedListener{ _, hour, minute -> timeChanged(hour, minute) }

        return view
    }

    private fun timeChanged(hour: Int, minute: Int){
        selection.setTime(hour, minute, 0)
        twTime.text = selection.timeStr()
        twTime.setTextColor(resources.getColor(selection.timeColor, context?.theme))
    }

    private fun dateChanged(year: Int, month: Int, day: Int){
        selection.setDate(year, month + 1, day)
        twDate.text = selection.dateStr()
        twDate.setTextColor(resources.getColor(selection.dateColor, context?.theme))
    }

    private suspend fun pulsar(){
        val sts = 20f
        val fns = 30f
        val twDateHeight = twDate.height
        val increaseTextAnimator = ValueAnimator.ofFloat(sts, fns)
        increaseTextAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            twDate.textSize = value
            twDate.height = twDateHeight
        }
        increaseTextAnimator.duration = 300L
        val decreaseTextAnimator = ObjectAnimator.ofFloat(twDate,"textSize", fns, sts)
        decreaseTextAnimator.duration = 200L
        val animatorSet = AnimatorSet()
        animatorSet.play(increaseTextAnimator).before(decreaseTextAnimator)
        animatorSet.interpolator = LinearInterpolator()

        while (true){
            delay(2000L)
            withContext(Dispatchers.Main){ animatorSet.start()}
        }
    }

    private suspend fun xodiki(){
        while (!selection.selected){
            delay(1000L)
            selection.set()
            twTime.text = selection.timeStr()
            twDate.text = selection.dateStr()
        }
    }


    private fun startAnimationDatePicker(view: View){
        if (view == currentView) return
        val scaleAnimator = ValueAnimator.ofFloat(1f, 0f)
        scaleAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            currentView.scaleY = value
            currentView.translationY = (1 - value) * currentView.height.toFloat() / 2
            view.translationY = -value * view.height.toFloat()
        }

        scaleAnimator.addListener(object : Animator.AnimatorListener{
            override fun onAnimationStart(animation: Animator?) {
                view.visibility = VISIBLE
            }

            override fun onAnimationEnd(animation: Animator?) {
                currentView.scaleY = 1F
                currentView.visibility = INVISIBLE
                currentView = view
            }

            override fun onAnimationCancel(animation: Animator?) {}

            override fun onAnimationRepeat(animation: Animator?) {}
        })
        scaleAnimator.interpolator = LinearInterpolator()
        scaleAnimator.duration = 1000L
        scaleAnimator.start()
    }

    private fun timePickerShow(){
        startAnimationDatePicker(timePicker)
    }

    private fun datePickerShow(){
        pulsar.cancel()
        startAnimationDatePicker(datePicker)
    }

    private fun clickOk(){
        dismiss()
    }

    private fun clickCancel(){
        dismiss()
    }


    companion object {
        const val TAG = "DateTimeDialogFragment"
        @JvmStatic
        fun newInstance() = DateTimeDialog()
    }
}