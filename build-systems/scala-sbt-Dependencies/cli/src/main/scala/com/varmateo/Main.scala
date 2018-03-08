package com.varmateo

import com.varmateo.yawg.api.YawgInfo


object Main {
  def main(args: Array[String]): Unit = {
    val yawgDetails = "${YawgInfo.PRODUCT_NAME} ${YawgInfo.VERSION}"
    Greeter.say(s"Hello, we are using $yawgDetails")
  }
}
