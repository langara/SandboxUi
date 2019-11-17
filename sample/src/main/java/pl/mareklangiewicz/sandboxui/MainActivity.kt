package pl.mareklangiewicz.sandboxui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import pl.mareklangiewicz.views.dsl.flexbox.lParams
import splitties.toast.toast
import splitties.views.backgroundColor
import splitties.views.dsl.core.*
import kotlin.math.absoluteValue
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val view1 = verticalLayout {
            for (i in 1..5)
                add(textView { text = "Text 1 $i" }, lParams())
        }

        val view2 = verticalLayout {
            for (i in 1..7) add(textView { text = "Text 2 $i" }, lParams(matchParent))
            val b = sandbox {
                val v = + textView { text = randomHash()}
                action("RND") { v.text = randomHash() }
            }
            add(b.root, lParams(matchParent))
        }

        val box1 = sandbox {
            + view1
            option("toast xxx") { toast("xxx") }
            option("toast yyy") { toast("yyy") }
            option("add random hash") { view1.add(view1.randomHashTextView(), view1.lParams(view1.matchParent)) }
        }

        val box2 = sandbox {
            on { flexGrow = 1f } lay view2
            on { flexGrow = 1f } lay sandbox {  }.root
            action("COL") { view2.backgroundColor = getRandomColor() }
        }

        val boxes = horizontalLayout {
            add(box1.root, lParams(0, weight = 1f))
            add(box2.root, lParams(0, weight = 1f))
        }

        setContentView(boxes)
    }
}

private fun getRandomColor() = Color.rgb(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255))

private fun randomHash() = Random.nextInt().hashCode().absoluteValue.toString(16)

private fun View.randomHashTextView() = textView { text = randomHash() }
