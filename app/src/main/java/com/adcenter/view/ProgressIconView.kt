package com.adcenter.view

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.adcenter.R
import com.adcenter.extensions.gone
import com.adcenter.extensions.visible
import com.adcenter.extensions.withStyledAttributes
import kotlinx.android.synthetic.main.view_progress_icon.view.*

class ProgressIconView : InflateFrameLayout {

    constructor(c: Context) : super(c)
    constructor(c: Context, attrs: AttributeSet) : super(c, attrs)
    constructor(c: Context, attrs: AttributeSet, defStyleAttr: Int) : super(c, attrs, defStyleAttr)

    override
    val viewLayout: Int
        get() = R.layout.view_progress_icon

    override fun onCreateView(context: Context, attrs: AttributeSet?) {
        withStyledAttributes(
            attributeSet = attrs,
            styleArray = R.styleable.ProgressIconView
        ) {
            val icon = getDrawable(R.styleable.ProgressIconView_icon)
            val showIcon = getBoolean(R.styleable.ProgressIconView_showIcon, false)
            val showProgress = getBoolean(R.styleable.ProgressIconView_showProgress, false)
            val iconColor = getColor(R.styleable.ProgressIconView_iconColorFilter, Color.BLACK)
            val progressColor =
                getColor(R.styleable.ProgressIconView_progressColorFilter, Color.BLACK)

            setIconColor(iconColor)
            setProgressColor(progressColor)

            icon?.let { setIcon(it) }

            when {
                showIcon -> showIcon()
                showProgress -> showProgress()
                else -> hide()
            }
        }
    }

    val isIconVisible: Boolean
        get() = icon.visibility == View.VISIBLE

    fun setIcon(@DrawableRes resId: Int) {
        icon.setImageResource(resId)
    }

    fun setIcon(drawable: Drawable) {
        icon.setImageDrawable(drawable)
    }

    fun setIconColor(@ColorInt color: Int) {
        icon.setColorFilter(color, PorterDuff.Mode.SRC_IN)
    }

    fun setProgressColor(@ColorInt color: Int) {
        progress.indeterminateDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
    }

    fun showIcon() {
        progress.gone()
        icon.visible()
    }

    fun hideIcon() {
        icon.gone()
    }

    fun showProgress() {
        icon.gone()
        progress.visible()
    }

    fun hideProgress() {
        progress.gone()
    }

    fun hide() {
        hideIcon()
        hideProgress()
    }
}