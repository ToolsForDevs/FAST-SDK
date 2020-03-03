package com.toolsfordevs.fast.core.util

import android.os.Build
import com.toolsfordevs.fast.core.annotation.Keep

@Keep
class AndroidVersion
{
    companion object
    {
        const val PIE = Build.VERSION_CODES.P
        const val OREO_MR1 = Build.VERSION_CODES.O_MR1
        const val OREO = Build.VERSION_CODES.O
        const val NOUGAT_MR1 = Build.VERSION_CODES.N_MR1
        const val NOUGAT = Build.VERSION_CODES.N
        const val MARSHMALLOW = Build.VERSION_CODES.M
        const val LOLLIPOP_MR1 = Build.VERSION_CODES.LOLLIPOP_MR1
        const val LOLLIPOP = Build.VERSION_CODES.LOLLIPOP

        fun isMinApi(api:Int): Boolean
        {
            return Build.VERSION.SDK_INT >= api
        }

        /**
         * Q - 29
         */
        fun isMinAndroid10():Boolean
        {
            return isMinApi(PIE)
        }

        /**
         * P - 28
         */
        fun isMinPie():Boolean
        {
            return isMinApi(PIE)
        }

        /**
         * O1 - 27
         */
        fun isMinOreoMR1():Boolean
        {
            return isMinApi(OREO_MR1)
        }

        /**
         * O - 26
         */
        fun isMinOreo():Boolean
        {
            return isMinApi(OREO)
        }

        /**
         * N1 - 25
         */
        fun isMinNougatMR1():Boolean
        {
            return isMinApi(NOUGAT_MR1)
        }

        /**
         * N - 24
         */

        fun isMinNougat():Boolean
        {
            return isMinApi(NOUGAT)
        }

        /**
         * M - 23
         */
        fun isMinMarshmallow():Boolean
        {
            return isMinApi(MARSHMALLOW)
        }

        /**
         * L1 - 22
         */
        fun isMinLollipopMR1():Boolean
        {
            return isMinApi(LOLLIPOP_MR1)
        }

        /**
         * L - 21
         */
        fun isMinLollipop():Boolean
        {
            return isMinApi(LOLLIPOP)
        }
    }
}