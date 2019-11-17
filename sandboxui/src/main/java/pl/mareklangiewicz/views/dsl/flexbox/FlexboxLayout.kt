package pl.mareklangiewicz.views.dsl.flexbox

import android.content.Context
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StyleRes
import com.google.android.flexbox.FlexboxLayout
import splitties.views.dsl.core.NO_THEME
import splitties.views.dsl.core.Ui
import splitties.views.dsl.core.view
import splitties.views.dsl.core.wrapContent
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract


// FlexboxLayout

inline fun Context.flexboxLayout(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: FlexboxLayout.() -> Unit = {}
) = view(::FlexboxLayout, id, theme, initView)

inline fun View.flexboxLayout(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: FlexboxLayout.() -> Unit = {}
) = context.flexboxLayout(id, theme, initView)

inline fun Ui.flexboxLayout(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: FlexboxLayout.() -> Unit = {}
) = ctx.flexboxLayout(id, theme, initView)


inline fun FlexboxLayout.lParams(
    width: Int = wrapContent,
    height: Int = wrapContent,
    initParams: FlexboxLayout.LayoutParams.() -> Unit = {}
) = FlexboxLayout.LayoutParams(width, height).apply(initParams)

