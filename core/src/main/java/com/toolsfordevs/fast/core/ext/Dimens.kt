package com.toolsfordevs.fast.core.ext

import com.toolsfordevs.fast.core.util.Dimens

val Int.dp
    get() = Dimens.dp(this)
val Float.dp
    get() = Dimens.dpF(this)

val Int.sp
    get() = Dimens.sp(this)
val Float.sp
    get() = Dimens.spF(this)

val Int.pxToDp
    get() = Dimens.pxToDp(this)
val Float.pxToDp
    get() = Dimens.pxToDpF(this.toInt())

val Int.pxToSp
    get() = Dimens.pxToSp(this)
val Float.pxToSp
    get() = Dimens.pxToSpF(this.toInt())