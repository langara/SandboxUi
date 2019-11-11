package pl.mareklangiewicz.sandboxui

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import splitties.toast.toast
import splitties.views.backgroundColor
import splitties.views.dsl.core.*
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
            for (i in 1..7)
                add(textView { text = "Text 2 $i" }, lParams())
        }
        val box1 = SandboxUi(this, view1).apply {
            action("xxx") { toast("xxx") }
            action("yyy") { toast("yyy") }
        }
        val box2 = SandboxUi(this, view2).apply {
            action("change color") {
                view2.backgroundColor = getRandomColor()
            }
        }
        val boxes = horizontalLayout {
            add(box1.root, lParams(0, weight = 1f))
            add(box2.root, lParams(0, weight = 1f))
        }
        setContentView(boxes)
    }
}

private fun getRandomColor() = Color.rgb(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255))
