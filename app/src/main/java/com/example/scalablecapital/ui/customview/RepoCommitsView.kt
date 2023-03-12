package com.example.scalablecapital.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.scalablecapital.R
import android.view.animation.AccelerateDecelerateInterpolator
import android.animation.LayoutTransition
import android.animation.ValueAnimator

/**
 * Custom view to represent commits activity per month.
 */
class RepoCommitsView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    ConstraintLayout(context, attrs) {

    private val commitsView: TextView
    private val monthView: TextView
    private val noCommitsView: TextView
    private val tabBarView: View

    var month: String? = null
    set(value) {
        field = value
        monthView.text = value
    }

    var commitsNumber: String? = null
        set(value) {
            field = value
            commitsView.text = value
        }

    var level: Float = -1f
        set(value) {
            field = value
            if (value != -1f) {
                noCommitsView.visibility = if (value == 0f) View.VISIBLE else View.INVISIBLE
                tabBarView.visibility = if (value == 0f) View.INVISIBLE else View.VISIBLE
                tabBarView.animate()
                    .scaleY(value)
                    .setInterpolator(AccelerateDecelerateInterpolator())
                    .duration = ANIMATION_DURATION
            }
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.commits_view, this, true)
        commitsView = findViewById(R.id.commits)
        monthView = findViewById(R.id.month)
        tabBarView = findViewById(R.id.tab_bar)
        noCommitsView = findViewById(R.id.no_commits)
        tabBarView.scaleY = 0f
    }

    companion object {
        const val ANIMATION_DURATION = 750L
    }
}