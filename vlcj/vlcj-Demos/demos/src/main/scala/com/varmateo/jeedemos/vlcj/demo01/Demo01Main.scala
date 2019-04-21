/**************************************************************************
 *
 * Copyright (c) 2019 Jorge Nunes
 *
 **************************************************************************/

package com.varmateo.jeedemos.vlcj.demo01


object Demo01Main {

    def main(args: Array[String]): Unit = {

        if ( args.size < 1 ) {
            println("Args: media-file-or-url")
            System.exit(1);
        }

        val app: Demo01App = Demo01App()

        app.start(args(0))
  }

}
