package pl.mareklangiewicz.sandboxui

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupMenu
import androidx.core.view.isVisible
import splitties.dimensions.dip
import splitties.resources.drawable
import splitties.views.dsl.core.*
import splitties.views.onClick
import splitties.views.padding
import splitties.views.textColorResource

@SuppressLint("SetTextI18n")
class SandboxUi<Content: View>
    (override val ctx: Context, val content: Content, atitle: String = content::class.simpleName ?: "") : Ui {

    val title = label(atitle)
    val dots = label("\u25cf\u25cf")

    val header = horizontalLayout {
        add(title, lParams(0, weight = 1f))
        add(dots, lParams())
    }

    val frame = frameLayout {
        background = drawable(R.drawable.sandbox_ui_back)
        add(content, lParams(matchParent, matchParent))
    }

    override val root = verticalLayout {
        background = drawable(R.drawable.sandbox_ui_back)
        padding = dip(4)
        add(header, lParams(matchParent))
        add(frame, lParams(matchParent, matchParent))
    }

    private val actions = mutableListOf<Pair<String, Content.() -> Unit>>()

    fun action(name: String, block: Content.() -> Unit) {
        menu.menu.add(0, actions.size, actions.size, name)
        actions += name to block
    }

    private val menu = PopupMenu(ctx, dots).apply {
        setOnMenuItemClickListener { actions[it.order].second.invoke(content); true }
    }

    init {
        title.onClick { content.isVisible = !content.isVisible }
        dots.onClick { menu.show() }
    }
}

fun <Content: View> Context.sandbox(
    content: Content,
    title: String = content::class.simpleName ?: "",
    initBox: SandboxUi<Content>.() -> Unit = {}
) = SandboxUi(this, content, title).apply(initBox)

fun <Content: View> View.sandbox(
    content: Content,
    title: String = content::class.simpleName ?: "",
    initBox: SandboxUi<Content>.() -> Unit = {}
) = context.sandbox(content, title, initBox)

fun <Content: View> Ui.sandbox(
    content: Content,
    title: String = content::class.simpleName ?: "",
    initBox: SandboxUi<Content>.() -> Unit = {}
) = ctx.sandbox(content, title, initBox)

fun <Content: View> LinearLayout.addbox(
    content: Content,
    title: String = content::class.simpleName ?: "",
    initBox: SandboxUi<Content>.() -> Unit = {}
) = sandbox(content, title, initBox).also { add(it.root, lParams(matchParent)) }


private fun Ui.label(text: String) = textView {
    this.text = text
    textSize = 12f
    isSingleLine = true
    padding = dip(2)
    textColorResource = android.R.color.black
}
