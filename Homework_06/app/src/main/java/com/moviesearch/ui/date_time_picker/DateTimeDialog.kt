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
import androidx.compose.ui.input.key.Key.Companion.Period
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.moviesearch.R
import com.moviesearch.databinding.DateTimeDialogBinding
import com.moviesearch.trace
import kotlinx.coroutines.*
import java.time.*
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class SelectedDateTime(){
    var dateSelected = false
    var timeSelected = false
    var selected = false
    var date: LocalDate = LocalDate.now()
    var time: LocalTime = LocalTime.of(0,0,0)
    lateinit var dateTime: LocalDateTime
    var interval: Long = 0
    var dateColor = R.color.red
    var timeColor = R.color.red
    fun setDate(year: Int, month: Int, day: Int){
        date = LocalDate.of(year, month, day)
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
    private lateinit var image: ImageView
    private lateinit var xodiki: Job
    private lateinit var pulsar: Job
    lateinit var currentView: View
    private var selection = SelectedDateTime()
    private val scope = CoroutineScope(Dispatchers.Default)
    private lateinit var binding: DateTimeDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("datetime", "${trace()} $container")
        //val view = inflater.inflate(R.layout.date_time_dialog, container, false)
        binding = DataBindingUtil.inflate(inflater,R.layout.date_time_dialog, container, false)
        val view = binding.root
        binding.buttonOkDateTime.setOnClickListener{ clickOk() }
        binding.buttonCancelDateTime.setOnClickListener{ clickCancel() }
        binding.textDate.setOnClickListener { datePickerShow() }
        binding.textTime.setOnClickListener { timePickerShow() }
        xodiki = scope.launch { xodiki() }
        xodiki.start()
        pulsar = scope.launch { pulsar() }
        pulsar.start()
        currentView = binding.imageClockAlarm

        binding.datePicker.setOnDateChangedListener{ _, year, month, day -> dateChanged(year, month, day)}
        binding.timePicker.setOnTimeChangedListener{ _, hour, minute -> timeChanged(hour, minute) }

        return view
    }

    private fun timeChanged(hour: Int, minute: Int){
        selection.setTime(hour, minute, 0)
        binding.textTime.text = selection.timeStr()
        binding.textTime.setTextColor(resources.getColor(selection.timeColor, context?.theme))
    }

    private fun dateChanged(year: Int, month: Int, day: Int){
        selection.setDate(year, month + 1, day)
        binding.textDate.text = selection.dateStr()
        binding.textDate.setTextColor(resources.getColor(selection.dateColor, context?.theme))
    }

    private suspend fun pulsar(){
        val sts = 20f
        val fns = 30f
        val twDateHeight = binding.textDate.height
        val increaseTextAnimator = ValueAnimator.ofFloat(sts, fns)
        increaseTextAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            binding.textDate.textSize = value
            binding.textDate.height = twDateHeight
        }
        increaseTextAnimator.duration = 300L
        val decreaseTextAnimator = ObjectAnimator.ofFloat(binding.textDate,"textSize", fns, sts)
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
            binding.textTime.text = selection.timeStr()
            binding.textDate.text = selection.dateStr()
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
        startAnimationDatePicker(binding.timePicker)
    }

    private fun datePickerShow(){
        pulsar.cancel()
        startAnimationDatePicker(binding.datePicker)
    }

    private fun clickOk(){
        setFragmentResult("selectDate", bundleOf("ok" to true,
            "dateTime" to selection.dateTime.format(DateTimeFormatter.ISO_DATE_TIME)))
        dismiss()
    }

    private fun clickCancel(){
        setFragmentResult("selectDate", bundleOf("ok" to false))
        dismiss()
    }


    companion object {
        const val TAG = "DateTimeDialogFragment"
        @JvmStatic
        fun newInstance() = DateTimeDialog()
    }
}