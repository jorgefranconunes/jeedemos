package com.varmateo

import com.varmateo.yawg.api.YawgInfo


object Main {

  def main(args: Array[String]): Unit = {

    val yawgDetails = s"${YawgInfo.PRODUCT_NAME} ${YawgInfo.VERSION}"
    val message = s"Hello, we are using $yawgDetails."

    Greeter.say(message)
  }

}
