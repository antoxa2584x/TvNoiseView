package devs.goldenpie.com.tvnoiseview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import java.util.*
import kotlin.concurrent.schedule


class TvNoiseView : View {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var time = 0

    private var height8: Int = 0
    private var width8: Int = 0
    private var height2: Int = 0
    private var width2: Int = 0

    init {
        Timer("", false).schedule(50, 20) {
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        val compare = Bitmap.createBitmap(width8, height8, Bitmap.Config.RGB_565)

        for (y in 0 until height8) {
            for (x in 0 until width8) {
                val pix = ((40.0 * Math.random() * (7 + Math.sin(((x / 50000 + time / 7).toDouble())))).toInt())
                compare.setPixel(x, y, Color.argb(255, pix, pix, pix))
            }
        }

        val scaledBitmap = Bitmap.createScaledBitmap(compare, width2, height2, false)

        canvas?.let {
            it.drawBitmap(scaledBitmap, 0f, 0f, null)
            it.drawBitmap(scaledBitmap, width2.toFloat(), 0f, null)
            it.drawBitmap(scaledBitmap, 0f, height2.toFloat(), null)
            it.drawBitmap(scaledBitmap, width2.toFloat(), height2.toFloat(), null)
        }

        time = (time + 1) % compare.height

        scaledBitmap.recycle()
        compare.recycle()

        super.onDraw(canvas)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        height8 = height / 8
        width8 = width / 8

        height2 = height / 2
        width2 = width / 2
    }


}