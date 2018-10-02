package io.kimo.konami

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

import io.kimo.konamicode.KonamiCodeInstaller
import io.kimo.konamicode.enums.Direction
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
						}
				)
			}
		)

		KonamiCodeInstaller(this)
				.on(this)
				.install(kkv)
	}
}
