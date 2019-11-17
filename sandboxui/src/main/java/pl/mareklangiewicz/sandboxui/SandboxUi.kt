package pl.mareklangiewicz.sandboxui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.core.view.isVisible
import com.google.android.flexbox.FlexboxLayout
import pl.mareklangiewicz.views.dsl.flexbox.flexboxLayout
import pl.mareklangiewicz.views.dsl.flexbox.lParams
import splitties.dimensions.dip
import splitties.resources.drawable
import splitties.views.*
import splitties.views.dsl.core.*

@SuppressLint("SetTextI18n")
class SandboxUi(override val ctx: Context, atitle: String = "") : Ui {

    val title = label(atitle)
    val actions = horizontalLayout()
    val dots = label("\u25cf\u25cf")
    val info = label("")
    val options = verticalLayout {
        background = drawable(R.drawable.sandbox_options_background)
        isVisible = false
        add(info, lParams())
    }

    val header = horizontalLayout {
        add(title, lParams(0, weight = 1f))
        add(actions, lParams())
        add(dots, lParams())
    }

    val inbox = flexboxLayout { background = drawable(R.drawable.sandbox_inbox_background) }

    val frame = frameLayout {
        add(inbox, lParams(matchParent, matchParent))
        add(options, lParams(gravity = gravityTopEnd))
    }

    override val root = verticalLayout {
        background = drawable(R.drawable.sandbox_ui_background)
        padding = dip(4)
        add(header, lParams(matchParent))
        add(frame, lParams(matchParent, matchParent))
    }

    fun action(name: String, block: () -> Unit) = actions.apply {
        add(label(name).apply { onClick(block) }, lParams())
    }

    fun option(name: String, block: () -> Unit) = options.apply {
        add(label(name).apply { onClick(block) }, lParams())
    }

    operator fun <V: View> V.unaryPlus() = apply { inbox.add(this, inbox.lParams()) }

    fun on(
        width: Int = inbox.wrapContent,
        height: Int = inbox.wrapContent,
        initParams: FlexboxLayout.LayoutParams.() -> Unit = {}
    ) = inbox.lParams(width, height, initParams)

    infix fun FlexboxLayout.LayoutParams.lay(view: View) { inbox.add(view, this) }

    init {
        title.onClick { inbox.isVisible = !inbox.isVisible; options.isVisible = options.isVisible && inbox.isVisible }
        dots.onClick { options.isVisible = !options.isVisible; inbox.isVisible = inbox.isVisible || options.isVisible }
        inbox.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            info.text = "size: ${right-left}, ${bottom-top}"
        }
    }
}

fun Context.sandbox(title: String = "", initBox: SandboxUi.() -> Unit = {}) = SandboxUi(this, title).apply(initBox)
fun View.sandbox(title: String = "", initBox: SandboxUi.() -> Unit = {}) = context.sandbox(title, initBox)
fun Ui.sandbox(title: String = "", initBox: SandboxUi.() -> Unit = {}) = ctx.sandbox(title, initBox)

private fun Ui.label(text: String) = textView {
    this.text = text
    textSize = 12f
    isSingleLine = true
    padding = dip(2)
    setTextColor(Color.BLACK)
}

