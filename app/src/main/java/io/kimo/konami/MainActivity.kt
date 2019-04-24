package io.kimo.konami

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

import io.kimo.konamicode.KonamiCodeInstaller
import io.kimo.konamicode.enums.Button
import io.kimo.konamicode.enums.Direction
import io.kimo.konamicode.enums.ErrorType
import io.kimo.konamicode.layout.KonamiKodeView
import io.kimo.konamicode.layout.KonamiKodeViewMdl

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val kkv = KonamiKodeView(this)

		kkv.model = KonamiKodeViewMdl(
			onFinish = {
				Toast.makeText(this, "Correct code", Toast.LENGTH_SHORT).show()
				kkv.model = KonamiKodeViewMdl(
						directionsOrder = listOf(Direction.UP, Direction.DOWN, Direction.UP, Direction.DOWN),
						buttonsOrder = listOf(),
						onFinish = {
							Toast.makeText(this, "Correct code again!", Toast.LENGTH_SHORT).show()
						},
						onError = { errorType: ErrorType, directions: List<Direction>, buttons: List<Button> ->
							Toast.makeText(this, "2 ${errorType.name} | ${directions.map { it.name }} | ${buttons.map { it.name }}", Toast.LENGTH_LONG).show()
						}
				)
			},
			onError = { errorType: ErrorType, directions: List<Direction>, buttons: List<Button> ->
				Toast.makeText(this, "1 ${errorType.name} | ${directions.map { it.name }} | ${buttons.map { it.name }}", Toast.LENGTH_LONG).show()
			}
		)

		KonamiCodeInstaller(this)
				.on(this)
				.install(kkv)
	}
}
