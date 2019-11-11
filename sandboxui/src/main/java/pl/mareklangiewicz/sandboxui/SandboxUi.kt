package pl.mareklangiewicz.sandboxui

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.PopupMenu
import androidx.core.view.isVisible
import splitties.dimensions.dip
import splitties.resources.color
import splitties.resources.drawable
import splitties.views.backgroundColor
import splitties.views.dsl.core.*
import splitties.views.onClick
import splitties.views.padding
import splitties.views.textColorResource

@SuppressLint("SetTextI18n")
class SandboxUi(override val ctx: Context, val content: View, name: String = content::class.simpleName ?: "") : Ui {

    private val nameView = label(name)
    private val eyeView = label("\u2609")
    private val dotsView = label("\u25cf\u25cf\u25cf")

    private val headerView = horizontalLayout {
        add(nameView, lParams(0, weight = 1f))
        add(eyeView, lParams())
        add(dotsView, lParams())
    }

    private val contentFrameView = frameLayout {
        foreground = drawable(R.drawable.sandbox_ui_border)
        add(content, lParams(matchParent, matchParent))
    }

    override val root = verticalLayout {
        backgroundColor = color(R.color.amber_500)
        foreground = drawable(R.drawable.sandbox_ui_border)
        padding = dip(4)
        add(headerView, lParams(matchParent))
        add(contentFrameView, lParams(matchParent, matchParent))
    }

    private val actions = mutableListOf<Pair<String, () -> Unit>>()

    fun action(name: String, block: () -> Unit) {
        menu.menu.add(0, actions.size, actions.size, name)
        actions += name to block
    }

    private val menu = PopupMenu(ctx, dotsView).apply {
        setOnMenuItemClickListener { actions[it.order].second(); true }
    }

    init {
        nameView.onClick { content.isVisible = !content.isVisible }
        eyeView.onClick { content.isVisible = !content.isVisible }
        dotsView.onClick { menu.show() }
    }
}

private fun Ui.label(text: String) =
    textView { this.text = text; padding = dip(4); textColorResource = android.R.color.black }
