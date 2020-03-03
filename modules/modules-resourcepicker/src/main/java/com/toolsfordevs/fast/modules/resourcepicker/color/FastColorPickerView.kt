package com.toolsfordevs.fast.modules.resourcepicker.color

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.toolsfordevs.fast.core.FastColor
import com.toolsfordevs.fast.core.ext.*
import com.toolsfordevs.fast.core.util.ColorUtil
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.modules.androidx.ViewPager
import com.toolsfordevs.fast.modules.resourcepicker.Prefs
import com.toolsfordevs.fast.modules.resourcepicker.R
import com.toolsfordevs.fast.modules.resourcepicker.color.components.BrightnessSeekBar
import com.toolsfordevs.fast.modules.resourcepicker.color.components.HueSeekBar
import com.toolsfordevs.fast.modules.resourcepicker.color.components.RGBSeekBar
import com.toolsfordevs.fast.modules.resourcepicker.color.components.SaturationSeekBar
import com.toolsfordevs.fast.modules.resources.ColorResource
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.core.ui.ext.*
import com.toolsfordevs.fast.core.ui.widget.FastColorView
import com.toolsfordevs.fast.core.ui.widget.FastTabLayout
import com.toolsfordevs.fast.core.ui.widget.adapter.FastViewPagerAdapter
import kotlin.math.round

class FastColorPickerView(context: Context) : LinearLayout(context)
{
    private val hue: HueSeekBar = HueSeekBar(context)
    private val saturation: SaturationSeekBar = SaturationSeekBar(context)
    private val brightness: BrightnessSeekBar = BrightnessSeekBar(context)
    private val red = RGBSeekBar(context, 0, MaterialColor.RED_500)
    private val green = RGBSeekBar(context, 1, MaterialColor.LIGHTGREEN_500)
    private val blue = RGBSeekBar(context, 2, MaterialColor.LIGHTBLUE_500)
    private val alphaBar = RGBSeekBar(context, 3, MaterialColor.WHITE_100)

    private val hueLabel: TextView
    private val saturationLabel: TextView
    private val brightnessLabel: TextView
    private val redLabel: TextView
    private val blueLabel: TextView
    private val greenLabel: TextView
    private val alphaLabel: TextView

    private val editText: EditText = EditText(context)
    private val colorView = SquareColorView(context).apply { showTransparentGrid = false}

    private val viewPager:ViewPager

    private val currentMode
        get() = viewPager.currentItem

    private var listener: ((ColorResource) -> Unit)? = null

    fun setOnColorSelectedListener(callback: (ColorResource) -> Unit)
    {
        listener = callback
    }

    private val seekBarListener = object : SeekBarListener()
    {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean)
        {
            if (fromUser)
            {
                if (currentMode == Mode.HSB)
                {
                    val hsv = floatArrayOf(hue.getValue(), saturation.getValue(), brightness.getValue())
                    color = ColorUtil.setAlpha(Color.HSVToColor(hsv), alphaBar.progress)
                }
                else
                {
                    color = Color.argb(alphaBar.progress, red.progress, green.progress, blue.progress)
                }

                onSeekBarChange(seekBar)
            }
        }
    }

    private val textWatcher = object : TextWatcher
    {
        override fun afterTextChanged(s: Editable?)
        {
            try {
                color = ColorUtil.hexToColor(s.toString())
            }
            catch(e: Exception)
            {
                color = Color.BLACK
            }

            onSeekBarChange(editText)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
        {
            // do nothing
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
        {
            // do nothing
        }
    }

    private var color: Int = 0xFFFF9925.toInt()

    init
    {
        orientation = VERTICAL
        setBackgroundColor(Color.WHITE)

        val frameLayout = frameLayout(context)

        frameLayout.addView(colorView, frameLayoutParamsMM().apply { gravity = Gravity.CENTER })
        frameLayout.setPadding(Dimens.dp(32))
        addView(frameLayout, linearLayoutParamsMWe(1f))

        val bottomSheet = vLinearLayout(context).apply {
            setBackgroundColor(FastColor.colorPrimary)
            setPaddingTop(Dimens.dp(4))
        }

        val hLayout = hLinearLayout(context).apply { setPaddingHorizontal(Dimens.dp(16)) }
        val dp56 = Dimens.dp(56)
        val dp32 = Dimens.dp(32)
        val dp16 = Dimens.dp(8)
        val dp2 = Dimens.dpF(2)

        val blackButton = SquareColorView(context).apply { setColor(Color.BLACK, FastColor.colorPrimaryDarker, dp2) }
        blackButton.setOnClickListener { frameLayout.setBackgroundColor(Color.BLACK); Prefs.customColorPickerBackground = 0 }
        hLayout.addView(blackButton, layoutParamsVV(dp32, dp32).apply { marginEnd = dp16 })

        val greyButton = SquareColorView(context).apply { setColor(Color.GRAY, FastColor.colorPrimaryDarker, dp2) }
        greyButton.setOnClickListener { frameLayout.setBackgroundColor(Color.GRAY); Prefs.customColorPickerBackground = 1 }
        hLayout.addView(greyButton, layoutParamsVV(dp32, dp32).apply { marginEnd = dp16 })

        val whiteButton = SquareColorView(context).apply { setColor(Color.WHITE, FastColor.colorPrimaryDarker, dp2) }
        whiteButton.setOnClickListener { frameLayout.setBackgroundColor(Color.WHITE); Prefs.customColorPickerBackground = 2 }
        hLayout.addView(whiteButton, layoutParamsVV(dp32, dp32).apply { marginEnd = dp16 })

        val transparentButton = SquareColorView(context).apply { setColor(Color.TRANSPARENT, FastColor.colorPrimaryDarker, dp2) }
        transparentButton.setOnClickListener { frameLayout.background = TransparentGridDrawable(); Prefs.customColorPickerBackground = 3 }
        hLayout.addView(transparentButton, layoutParamsVV(dp32, dp32).apply { marginEnd = dp16 })

        when (Prefs.customColorPickerBackground)
        {
            0 -> frameLayout.setBackgroundColor(Color.BLACK)
            1 -> frameLayout.setBackgroundColor(Color.GRAY)
            2 -> frameLayout.setBackgroundColor(Color.WHITE)
            else -> frameLayout.background = TransparentGridDrawable()
        }

        val hashtag = TextView(context).apply {
            setTextColor(0x55FFFFFF.toInt())
            gravity = Gravity.CENTER_VERTICAL or Gravity.END
            textSize = 16f
            text = "#"
            typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
        }
        hLayout.addView(hashtag, linearLayoutParamsWeM(1f))

        with(editText) {
            background = null
            setTextColor(Color.WHITE)
            gravity = Gravity.END or Gravity.CENTER_VERTICAL
            typeface = Typeface.MONOSPACE
            filters = arrayOf(InputFilter.LengthFilter(8))
            isAllCaps = true
        }

        hLayout.gravity = Gravity.CENTER_VERTICAL
        hLayout.addView(editText, linearLayoutParamsWW())
        bottomSheet.addView(hLayout, layoutParamsMW())

        viewPager = WrapHeightViewPager(context)
        val tabLayout = FastTabLayout(context)
        tabLayout.setupWithViewPager(viewPager)

        val rgbLayout = vLinearLayout(context, layoutParamsMW())
        val hsbLayout = vLinearLayout(context, layoutParamsMW())

        viewPager.adapter = ColorAdapter(hsbLayout, rgbLayout)
        bottomSheet.addView(tabLayout, layoutParamsWW().apply { marginStart = Dimens.dp(16) })
        bottomSheet.addView(viewPager, layoutParamsMW())

        hueLabel = makeLabelRow(hsbLayout, "Hue")
        hsbLayout.addView(hue, linearLayoutParamsMW())
        saturationLabel = makeLabelRow(hsbLayout, "Saturation")
        hsbLayout.addView(saturation, linearLayoutParamsMW())
        brightnessLabel = makeLabelRow(hsbLayout, "Brightness")
        hsbLayout.addView(brightness, linearLayoutParamsMW())

        redLabel = makeLabelRow(rgbLayout, "Red")
        rgbLayout.addView(red, linearLayoutParamsMW())
        greenLabel = makeLabelRow(rgbLayout, "Green")
        rgbLayout.addView(green, linearLayoutParamsMW())
        blueLabel = makeLabelRow(rgbLayout, "Blue")
        rgbLayout.addView(blue, linearLayoutParamsMW())

        alphaLabel = makeLabelRow(bottomSheet, "Alpha")
        alphaBar.setPaddingBottom(Dimens.dp(16))
        bottomSheet.addView(alphaBar, linearLayoutParamsMW())

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int)
            {
            }

            override fun onPageSelected(position: Int)
            {
                Prefs.customColorPickerMode = position

                if (position == Mode.HSB)
                {
                    hue.setColor(color, true)
                    saturation.setColor(color, true)
                    brightness.setColor(color, true)
                }
                else
                {
                    red.setColor(color, true)
                    green.setColor(color, true)
                    blue.setColor(color, true)
                }

                onSeekBarChange()
            }

            override fun onPageScrollStateChanged(state: Int)
            {
            }
        })
        viewPager.currentItem = Prefs.customColorPickerMode

        val buttonBar = hLinearLayout(context).apply {
            setBackgroundColor(FastColor.colorPrimary)
            gravity = Gravity.CENTER
        }

        val okButton = Button(context).apply {
            gravity = Gravity.CENTER
            setBackgroundResource(R.drawable.fast_selectable_item_background)
            setTextColor(Color.WHITE)
            text = "OK"
        }

        okButton.setOnClickListener { listener?.invoke(ColorResource(ColorUtil.colorHex(color), -1, color)) }

        bottomSheet.addView(View(context).apply { setBackgroundColor(MaterialColor.WHITE_12) }, layoutParamsMV(Dimens.dp(1)))
        buttonBar.addView(okButton, layoutParamsMM())
        bottomSheet.addView(buttonBar, layoutParamsMV(dp56))

        addView(bottomSheet, layoutParamsMW())


        hue.setColor(color, true)
        saturation.setColor(color, true)
        brightness.setColor(color, true)
        alphaBar.setColor(color, true)

        onSeekBarChange()

        hue.setOnSeekBarChangeListener(seekBarListener)
        saturation.setOnSeekBarChangeListener(seekBarListener)
        brightness.setOnSeekBarChangeListener(seekBarListener)
        red.setOnSeekBarChangeListener(seekBarListener)
        green.setOnSeekBarChangeListener(seekBarListener)
        blue.setOnSeekBarChangeListener(seekBarListener)
        alphaBar.setOnSeekBarChangeListener(seekBarListener)

    }

    override fun onAttachedToWindow()
    {
        super.onAttachedToWindow()
        editText.addTextChangedListener(textWatcher)
    }

    override fun onDetachedFromWindow()
    {
        super.onDetachedFromWindow()
        editText.removeTextChangedListener(textWatcher)
    }

    fun setColor(color:Int)
    {
        this.color = color
        onSeekBarChange()
        onSeekBarChange(editText)
    }

    private fun onSeekBarChange(source: View? = null)
    {
        val noAlphaColor = Color.rgb(Color.red(color), Color.green(color), Color.blue(color))

        if (currentMode == Mode.HSB)
        {
            hue.setColor(noAlphaColor, source == editText)
            saturation.setColor(noAlphaColor, source == editText)
            brightness.setColor(noAlphaColor, source == editText)

            hueLabel.text = "${hue.getValue().toInt()}°"

            val satValue = (saturation.getValue() * 100).toInt()
            saturationLabel.text = "$satValue%"

            val brightValue = (brightness.getValue() * 100).toInt()
            brightnessLabel.text = "$brightValue%"

        }
        else
        {
            red.setColor(noAlphaColor, source == editText)
            green.setColor(noAlphaColor, source == editText)
            blue.setColor(noAlphaColor, source == editText)

            val redValue = round(red.progress / 255f * 100).toInt()
            redLabel.text = "$redValue%"

            val greenValue = round(green.progress / 255f * 100).toInt()
            greenLabel.text = "$greenValue%"

            val blueValue = round(blue.progress / 255f * 100).toInt()
            blueLabel.text = "$blueValue%"

        }

        if (source != alphaBar && source == editText)
            alphaBar.setColor(color, true)

        val alphaValue = round(alphaBar.progress / 255f * 100).toInt()
        alphaLabel.text = "$alphaValue%"

        if (source != editText)
            editText.setText(ColorUtil.colorHex(color))

        colorView.setColor(color)
    }

    private fun makeLabelRow(inLayout: ViewGroup, leftLabel: String): TextView
    {
        val layout = hLinearLayout(context)

        layout.addView(makeLabel(leftLabel), layoutParamsWM())

        val textView = makeLabel("")
        textView.gravity = Gravity.END

        layout.addView(textView, layoutParamsMM())
        inLayout.addView(layout, layoutParamsMW())

        return textView
    }

    private fun makeLabel(label: String): TextView
    {
        return TextView(context).apply {
            text = label
            setPaddingStart(Dimens.dp(16))
            setPaddingEnd(Dimens.dp(16))
            setPaddingTop(Dimens.dp(8))
            setTextColor(Color.WHITE)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean
    {
        parent?.requestDisallowInterceptTouchEvent(true)

        if ((event!!.action and MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) parent?.requestDisallowInterceptTouchEvent(false)

        return super.onTouchEvent(event)
    }

    abstract class SeekBarListener : SeekBar.OnSeekBarChangeListener
    {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean)
        {

        }

        override fun onStartTrackingTouch(seekBar: SeekBar?)
        {
            // do nothing
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?)
        {
            // do nothing
        }
    }

    private class ColorAdapter(val view1:View, val view2:View) : FastViewPagerAdapter()
    {
        override fun getItem(position: Int): View
        {
            return when (position)
            {
                0 -> view1
                else -> view2
            }
        }

        override fun getCount(): Int
        {
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence
        {
            return when(position)
            {
                0 -> "HSB"
                else -> "RGB"
            }
        }
    }

    private class SquareColorView(context: Context) : FastColorView(context)
    {
        override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int)
        {
            super.onMeasure(heightMeasureSpec, heightMeasureSpec)
        }
    }

    /**
     * Because regular ViewPager doesn't handle wrap_content (surprise motherfucker...)
     */
    private class WrapHeightViewPager(context:Context) : ViewPager(context)
    {
        override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int)
        {
            super.onSizeChanged(w, h, oldw, oldh)

            // onMeasure trick won't work if this view is in viewpager
            // and not the first displayed view
            post {
                requestLayout()
            }
        }

        override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int)
        {
            var height = 0
            for (i in 0 until childCount)
            {
                val child = getChildAt(i)
                child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))
                val h = child.measuredHeight
                if (h > height) height = h
            }

            var spec = heightMeasureSpec

            if (height != 0)
                spec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
            else
                requestLayout()

            super.onMeasure(widthMeasureSpec, spec)
        }
    }

    private class TransparentGridDrawable : Drawable()
    {
        private val gridPaint = Paint()
        private var path = Path()

        init
        {
            gridPaint.style = Paint.Style.FILL
            gridPaint.color = 0xFFCCCCCC.toInt()
            gridPaint.isAntiAlias = true
        }

        override fun onBoundsChange(bounds: Rect?)
        {
            super.onBoundsChange(bounds)
            resetPath()
        }

        override fun draw(canvas: Canvas)
        {
            canvas.drawPath(path, gridPaint)
        }

        override fun setAlpha(alpha: Int)
        {
            gridPaint.alpha = alpha
        }

        override fun getOpacity(): Int
        {
            return PixelFormat.TRANSLUCENT
        }

        override fun setColorFilter(colorFilter: ColorFilter?)
        {

        }

        fun resetPath()
        {
            path = Path()

            val dp8 = Dimens.dp(8)
            val dp8f = Dimens.dpF(8)

            var isOdd = false

            for (i in 0 until bounds.width() step dp8)
            {
                for (j in 0 until bounds.height() step dp8 * 2)
                {
                    val start: Float = if (isOdd) j + dp8f else j.toFloat()
                    path.addRect(i.toFloat(), start, i.toFloat() + dp8f, start + dp8f, Path.Direction.CCW)
                }

                isOdd = !isOdd
            }
        }
    }

    private object Mode
    {
        const val HSB = 0
        const val RGB = 1
    }
}