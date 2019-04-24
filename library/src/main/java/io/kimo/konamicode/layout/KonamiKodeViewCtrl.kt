package io.kimo.konamicode.layout

import android.support.v4.view.MotionEventCompat
import android.util.Log
import android.view.MotionEvent
import android.view.ViewConfiguration
import io.kimo.konamicode.enums.Button
import io.kimo.konamicode.enums.Direction
import io.kimo.konamicode.enums.ErrorType


class KonamiKodeViewCtrl(val view: KonamiKodeView, var model: KonamiKodeViewMdl) {

	init { //Initialize
		val viewConfiguration = ViewConfiguration.get(view.context)
		model.swipeThreshold = viewConfiguration.scaledTouchSlop
	}

	internal fun registerPress(button: Button) {
		if (button!=Button.NONE) {
			model.pressedButtons.add(button)

			if (!validPressedSequence()) {
				Log.d(TAG, "Invalid button sequence!")
				model.onError?.invoke(ErrorType.INVALID_BUTTON_SEQUENCE, model.swipes, model.pressedButtons)
				resetPressedSequence()
				view.hideDialog()
			} else {
				if (model.pressedSequenceAchieved) {
					onPressedSequenceAchieved()
				}
			}
		}
	}

	private fun registerSwipe(direction: Direction = model.lastSwipedDirection) {
		if (direction != Direction.NONE) {
			Log.d(TAG, "registerSwipe: $direction")
			model.swipes.add(direction)

			if (!validSwipeSequence()) {
				Log.d(TAG, "Invalid swipe sequence")
				model.onError?.invoke(ErrorType.INVALID_SWIPE_SEQUENCE, model.swipes, model.pressedButtons)
				resetSwipeSequence()
			} else {
				Log.d(TAG, "maybe valid swipe sequence")
				if (model.swipeSequenceAchieved) {
					resetSwipeSequence()
					if(model.buttonsOrder.isNotEmpty()) {
						view.showDialog()
					}else{
						onPressedSequenceAchieved()
					}
				}
			}
		}
	}

	private fun onPressedSequenceAchieved() {
		triggerFinalCallback()
		resetPressedSequence()
	}

	private fun validSwipeSequence(): Boolean {
		val index = model.swipes.size - 1

		val lastDirection = model.swipes[index]
		val correctDirection = model.directionsOrder[index]

		return correctDirection == lastDirection
	}

	private fun validPressedSequence(): Boolean {
		val index = model.pressedButtons.size - 1

		val currentPressedButton = model.pressedButtons[index]
		val correctPressedButton = model.buttonsOrder[index]

		return currentPressedButton == correctPressedButton
	}

	private fun resetPressedSequence() {
		model.pressedButtons.clear()
	}

	private fun resetSwipeSequence() {
		model.swipes.clear()
	}
	private fun triggerFinalCallback() {
		view.hideDialog()
		model.onFinish?.invoke()
	}

	fun onTouchEvent(ev: MotionEvent) {
		val action = MotionEventCompat.getActionMasked(ev)

		when (action) {
			MotionEvent.ACTION_DOWN -> {
				model.lastX = ev.x
				model.lastY = ev.y
			}
			MotionEvent.ACTION_MOVE -> {

				val diffY = ev.y - model.lastY
				val diffX = ev.x - model.lastX

				if (Math.abs(diffX) > Math.abs(diffY)) {
					if (Math.abs(diffX) > model.swipeThreshold) {
						model.lastSwipedDirection = if (diffX > 0) {
							Direction.RIGHT
						} else {
							Direction.LEFT
						}
					}
				} else if (Math.abs(diffY) > model.swipeThreshold) {
					model.lastSwipedDirection = if (diffY > 0) {
						Direction.DOWN
					} else {
						Direction.UP
					}
				}
			}
			MotionEvent.ACTION_UP -> {
				registerSwipe()
			}
		}
	}

	companion object {
		val TAG = KonamiKodeViewCtrl::class.java.simpleName
	}
}