package com.toolsfordevs.fast.core.util

import android.graphics.Color
import com.toolsfordevs.fast.core.annotation.Keep
import java.util.*

@Keep
object ColorUtil
{
    private fun colorToHSL(color: Int, hsl: FloatArray = FloatArray(3)): FloatArray
    {
        val r = Color.red(color) / 255f
        val g = Color.green(color) / 255f
        val b = Color.blue(color) / 255f

        val max = Math.max(r, Math.max(g, b))
        val min = Math.min(r, Math.min(g, b))
        hsl[2] = (max + min) / 2

        if (max == min)
        {
            hsl[1] = 0f
            hsl[0] = hsl[1]

        }
        else
        {
            val d = max - min

            hsl[1] = if (hsl[2] > 0.5f) d / (2f - max - min) else d / (max + min)
            when (max)
            {
                r -> hsl[0] = (g - b) / d + (if (g < b) 6 else 0)
                g -> hsl[0] = (b - r) / d + 2
                b -> hsl[0] = (r - g) / d + 4
            }
            hsl[0] /= 6f
        }
        return hsl
    }

    private fun hslToColor(hsl: FloatArray): Int
    {
        val r: Float
        val g: Float
        val b: Float

        val h = hsl[0]
        val s = hsl[1]
        val l = hsl[2]

        if (s == 0f)
        {
            b = l
            g = b
            r = g
        }
        else
        {
            val q = if (l < 0.5f) l * (1 + s) else l + s - l * s
            val p = 2 * l - q
            r = hue2rgb(p, q, h + 1f / 3)
            g = hue2rgb(p, q, h)
            b = hue2rgb(p, q, h - 1f / 3)
        }

        return Color.rgb((r * 255).toInt(), (g * 255).toInt(), (b * 255).toInt())
    }

    private fun hue2rgb(p: Float, q: Float, t: Float): Float
    {
        var valueT = t
        if (valueT < 0) valueT += 1f
        if (valueT > 1) valueT -= 1f
        if (valueT < 1f / 6) return p + (q - p) * 6f * valueT
        if (valueT < 1f / 2) return q
        return if (valueT < 2f / 3) p + (q - p) * (2f / 3 - valueT) * 6f else p
    }

    /*fun lightenColor(color: Int, value: Float): Int
    {
        val hsl = colorToHSL(color)
        hsl[2] += value
        hsl[2] = Math.max(0f, Math.min(hsl[2], 1f))
        return hslToColor(hsl)
    }

    fun darkenColor(color: Int, value: Float): Int
    {
        val hsl = colorToHSL(color)
        hsl[2] -= value
        hsl[2] = Math.max(0f, Math.min(hsl[2], 1f))
        return hslToColor(hsl)
    }*/

    /*

    fun lighten(color: Int, fraction: Float): Int
    {
        var red = Color.red(color)
        var green = Color.green(color)
        var blue = Color.blue(color)
        val alpha = Color.alpha(color)

        red = lightenColor(red, fraction)
        green = lightenColor(green, fraction)
        blue = lightenColor(blue, fraction)

        return Color.argb(alpha, red, green, blue)
    }

    fun darken(color: Int, fraction: Float): Int
    {
        var red = Color.red(color)
        var green = Color.green(color)
        var blue = Color.blue(color)
        val alpha = Color.alpha(color)

        red = darkenColor(red, fraction)
        green = darkenColor(green, fraction)
        blue = darkenColor(blue, fraction)

        return Color.argb(alpha, red, green, blue)
    }

    private fun darkenColor(color: Int, fraction: Float): Int
    {
        return Math.max(color - color * fraction, 0f).toInt()
    }

    private fun lightenColor(color: Int, fraction: Float): Int
    {
        return Math.min(color + color * fraction, 255f).toInt()
    }*/

    fun isColorDark(color: Int): Boolean
    {
        val darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255

        return darkness > 0.5
    }

    /**
     * @param fraction a value between 0 and 1
     */
    fun darken(color: Int, fraction: Float): Int
    {
        var red = Color.red(color)
        var green = Color.green(color)
        var blue = Color.blue(color)
        val alpha = Color.alpha(color)

//        red = Math.min(0xFF, (red * (1 - fraction)).toInt())
//        green = Math.min(0xFF, (green * (1 - fraction)).toInt())
//        blue = Math.min(0xFF, (blue * (1 - fraction)).toInt())

        red = (red * (1 - fraction)).toInt()
        green = (green * (1 - fraction)).toInt()
        blue = (blue * (1 - fraction)).toInt()

        return Color.argb(alpha, red, green, blue)
    }

    /**
     * @param fraction a value between 0 and 1
     */
    fun lighten(color: Int, fraction: Float): Int
    {
        var red = Color.red(color)
        var green = Color.green(color)
        var blue = Color.blue(color)
        val alpha = Color.alpha(color)

        if (red == 0 && green == 0 && blue == 0)
        {
            red = (0xFF * fraction).toInt()
            green = red
            blue = red
        }
        else
        {
            val hues = intArrayOf(red, green, blue)

            for (i in 0 until 3)
            {
                if (hues[i] > 2)
                {
                    val diff = 0xFF - hues[i]
                    hues[i] = hues[i] + (diff * fraction).toInt()
                    //hues[i] = Math.min(0xFF, (hues[i] / fraction).toInt())
                }
                else
                {
                    hues[i] = (0xFF * fraction).toInt()
                }
            }

            red = hues[0]
            green = hues[1]
            blue = hues[2]
        }

        return Color.argb(alpha, red, green, blue)
    }

    fun colorHex(color: Int): String
    {
        val a = Color.alpha(color)
        val r = Color.red(color)
        val g = Color.green(color)
        val b = Color.blue(color)
        return String.format(Locale.getDefault(), "%02X%02X%02X%02X", a, r, g, b)
    }

    fun hexToColor(hex:String):Int
    {
        return Color.parseColor("#$hex")
    }

    /**
     * @param alpha between 0f and 1f
     */
    fun setAlpha(color:Int, alpha:Float):Int
    {
        val r = Color.red(color) / 255f
        val g = Color.green(color) / 255f
        val b = Color.blue(color) / 255f

        return (alpha * 255.0f + 0.5f).toInt() shl 24 or ((r * 255.0f + 0.5f).toInt() shl 16) or ((g * 255.0f + 0.5f).toInt() shl 8) or (b * 255.0f + 0.5f).toInt()
    }

    /**
     * @param alpha between 0 and 255
     */
    fun setAlpha(color:Int, alpha:Int):Int
    {
        val r = Color.red(color) / 255f
        val g = Color.green(color) / 255f
        val b = Color.blue(color) / 255f

        return (alpha + 0.5f).toInt() shl 24 or ((r * 255.0f + 0.5f).toInt() shl 16) or ((g * 255.0f + 0.5f).toInt() shl 8) or (b * 255.0f + 0.5f).toInt()
    }
}