/**************************************************************************
 *
 * Copyright (c) 2019 Jorge Nunes
 *
 **************************************************************************/

package com.varmateo.jeedemos.swing.demo02


object Demo02Main {

    def main(args: Array[String]): Unit = {

        if ( args.size < 1 ) {
            println("Args: label ...")
            System.exit(1);
        }

        val app: Demo02App = Demo02App(args)

        app.start()
  }

}
