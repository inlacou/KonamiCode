package io.kimo.konamicode.layout

import io.kimo.konamicode.enums.Button
import io.kimo.konamicode.enums.Direction
import io.kimo.konamicode.enums.ErrorType

data class KonamiKodeViewMdl(val directionsOrder: List<Direction> = listOf(Direction.UP, Direction.UP,
		Direction.DOWN, Direction.DOWN,
		Direction.LEFT, Direction.RIGHT,
		Direction.LEFT, Direction.RIGHT),
                             val buttonsOrder: List<Button> = listOf(Button.B, Button.A, Button.START),
                             val onFinish: (() -> Unit)? = null,
                             val onError: ((error: ErrorType, directions: List<Direction>, buttons: List<Button>) -> Unit)? = null){

	var lastX: Float = 0.toFloat()
	var lastY: Float = 0.toFloat()

	var lastSwipedDirection = Direction.NONE
	var swipeThreshold: Int = 0
	val swipes: MutableList<Direction> = mutableListOf()
	val pressedButtons: MutableList<Button> = mutableListOf()
	val swipeSequenceAchieved: Boolean get() { return swipes == directionsOrder }
	val pressedSequenceAchieved: Boolean get() { return pressedButtons == buttonsOrder }
}