package io.kimo.konamicode

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import io.kimo.konamicode.layout.KonamiKodeView

class KonamiCodeInstaller
/**
 * Konami Code's builder
 * @param context
 */
(private var context: Context) {
	private var rootView: ViewGroup? = null

	/**
	 * on - installs into an activity
	 * @param activity
	 */
	fun on(activity: Activity): KonamiCodeInstaller {
		rootView = activity.findViewById<View>(android.R.id.content) as ViewGroup
		return this
	}

	/**
	 * into - installs into a fragment
	 * @param fragment
	 */
	fun on(fragment: Fragment): KonamiCodeInstaller {
		rootView = fragment.view?.rootView as ViewGroup?
		return this
	}

	/**
	 * into - installs into a view
	 * @param view
	 */
	fun on(view: View): KonamiCodeInstaller {
		rootView = view.rootView as ViewGroup
		return this
	}

	/**
	 * install - installs all Konami Code components into the target
	 */
	fun install(kcl: KonamiKodeView) {
		val currentView = rootView?.getChildAt(0)
		rootView?.removeView(currentView)

		//match parent params
		val layoutParams = FrameLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT
		)

		val gestureDelegate = FrameLayout(context)
		gestureDelegate.addView(currentView, layoutParams)

		//necessary view that passes all touch events up to the parent viewgroup
		gestureDelegate.setOnTouchListener { v, event -> true }

		kcl.addView(gestureDelegate)

		rootView?.addView(kcl, layoutParams)
	}
}
