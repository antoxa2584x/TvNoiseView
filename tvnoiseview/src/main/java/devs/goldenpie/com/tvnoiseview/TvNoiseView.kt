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

    private var height2: Int = 0
    private var width8: Int = 0

    lateinit var pixels: IntArray

    init {
        Timer("", false).schedule(0, 50) {
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        val bitmap = Bitmap.createBitmap(width8, height2, Bitmap.Config.RGB_565)

        for (i in 0 until (height2 * width8)) {
            val pix = ((40.0 * Math.random() * (7 + Math.sin(((x / 50000 + time / 7).toDouble())))).toInt())
            pixels[i] = Color.argb(255, pix, pix, pix)
        }

        bitmap.setPixels(pixels, 0, width8, 0, 0, width8, height2)

        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, width / DIVIDER, height, false)

        val width4 = width / DIVIDER

        for (multiplier in 0 until DIVIDER)
            canvas?.drawBitmap(scaledBitmap, (width4 * multiplier).toFloat(), 0f, null)


        time = (time + 1) % scaledBitmap.height

//        scaledBitmap.recycled()
        bitmap.recycle()

        super.onDraw(canvas)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        height2 = height / 2
        width8 = width / (DIVIDER * 2)

        pixels = IntArray(width8 * (height2))
    }

    companion object {
        const val DIVIDER = 4
    }


}