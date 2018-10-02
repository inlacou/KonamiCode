package io.kimo.konamicode.layout

import android.app.AlertDialog
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnClickListener
import android.widget.FrameLayout
import io.kimo.konamicode.R
import io.kimo.konamicode.enums.Button

class KonamiKodeView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
	: FrameLayout(context, attrs, defStyleAttr) {

	private var buttonDialog: AlertDialog? = null
	private val buttonsClickListener = OnClickListener { v ->
		val id = v.id
		controller.registerPress(when (id) {
			R.id.konami_button_a -> Button.A
			R.id.konami_button_b -> Button.B
			R.id.konami_button_start -> Button.START
			else -> Button.NONE
		})
	}

	var model: KonamiKodeViewMdl = KonamiKodeViewMdl()
	set(value) {
		field = value
		controller.model = value
		populate()
	}
	private var controller: KonamiKodeViewCtrl

	init {
		val buttonsView = LayoutInflater.from(context).inflate(R.layout.dialog_buttons, this, false)

		val aButton = buttonsView.findViewById<View>(R.id.konami_button_a)
		val bButton = buttonsView.findViewById<View>(R.id.konami_button_b)
		val startButton = buttonsView.findViewById<View>(R.id.konami_button_start)

		aButton.setOnClickListener(buttonsClickListener)
		bButton.setOnClickListener(buttonsClickListener)
		startButton.setOnClickListener(buttonsClickListener)

		buttonDialog = AlertDialog.Builder(context)
				.setView(buttonsView)
				.create()

		controller = KonamiKodeViewCtrl(this, model)
	}

	fun populate(){
	}

	override fun onTouchEvent(event: MotionEvent): Boolean {
		val child = getChildAt(0)
		return child != null && child.dispatchTouchEvent(event)
	}

	override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
		controller.onTouchEvent(ev)
		return super.dispatchTouchEvent(ev)
	}

	fun showDialog() {
		buttonDialog?.show()
	}

	fun hideDialog() {
		buttonDialog?.dismiss()
	}

	companion object {
		val TAG = KonamiKodeView::class.java.simpleName
	}
}