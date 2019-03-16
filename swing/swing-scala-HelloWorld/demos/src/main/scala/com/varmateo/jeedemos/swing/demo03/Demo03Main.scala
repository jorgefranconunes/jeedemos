/**************************************************************************
 *
 * Copyright (c) 2019 Jorge Nunes
 *
 **************************************************************************/

package com.varmateo.jeedemos.swing.demo03


object Demo03Main {

    def main(args: Array[String]): Unit = {

        if ( args.size < 1 ) {
            println("Args: label ...")
            System.exit(1);
        }

        val app: Demo03App = Demo03App(args)

        app.start()
  }

}
