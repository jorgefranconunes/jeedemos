package com.varmateo

import com.varmateo.say;
import com.varmateo.yawg.api.YawgInfo;


fun main(args: Array<String>) {

    val yawgDetails = YawgInfo.PRODUCT_NAME + " " + YawgInfo.VERSION
    val message = String.format("Hello, we are using %s", yawgDetails)

    say(message)
}
