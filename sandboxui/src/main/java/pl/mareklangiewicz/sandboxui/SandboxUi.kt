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
class SandboxUi<Content: View>(
    override val ctx: Context,
    val content: Content,
    name: String = content::class.simpleName ?: ""
) : Ui {

    private val nameView = label(name)
    private val eyeView = label("\u2609")
    private val dotsView = label("\u25cf\u25cf\u25cf")

    private val headerView = horizontalLayout {
        add(nameView, lParams(0, weight = 1f))
        add(eyeView, lParams())
        add(dotsView, lParams())
    }

    private val contentFrameView = frameLayout {
        background = drawable(R.drawable.sandbox_ui_back)
        add(content, lParams(matchParent, matchParent))
    }

    override val root = verticalLayout {
        background = drawable(R.drawable.sandbox_ui_back)
        padding = dip(4)
        add(headerView, lParams(matchParent))
        add(contentFrameView, lParams(matchParent, matchParent))
    }

    private val actions = mutableListOf<Pair<String, Content.() -> Unit>>()

    fun action(name: String, block: Content.() -> Unit) {
        menu.menu.add(0, actions.size, actions.size, name)
        actions += name to block
    }

    private val menu = PopupMenu(ctx, dotsView).apply {
        setOnMenuItemClickListener { actions[it.order].second.invoke(content); true }
    }

    init {
        nameView.onClick { content.isVisible = !content.isVisible }
        eyeView.onClick { content.isVisible = !content.isVisible }
        dotsView.onClick { menu.show() }
    }
}

fun <Content: View> Context.sandbox(
    content: Content,
    name: String = content::class.simpleName ?: "",
    initBox: SandboxUi<Content>.() -> Unit = {}
) = SandboxUi(this, content, name).apply(initBox)

fun <Content: View> View.sandbox(
    content: Content,
    name: String = content::class.simpleName ?: "",
    initBox: SandboxUi<Content>.() -> Unit = {}
) = context.sandbox(content, name, initBox)

fun <Content: View> Ui.sandbox(
    content: Content,
    name: String = content::class.simpleName ?: "",
    initBox: SandboxUi<Content>.() -> Unit = {}
) = ctx.sandbox(content, name, initBox)

fun <Content: View> LinearLayout.addbox(
    content: Content,
    name: String = content::class.simpleName ?: "",
    initBox: SandboxUi<Content>.() -> Unit = {}
) = sandbox(content, name, initBox).also { add(it.root, lParams(matchParent)) }


private fun Ui.label(text: String) = textView {
    this.text = text
    isSingleLine = true
    padding = dip(4)
    textColorResource = android.R.color.black
}
