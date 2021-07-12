/**************************************************************************
 *
 * Copyright (c) 2019 Jorge Nunes
 *
 **************************************************************************/

package com.varmateo.jeedemos.swing.demo01

import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.SwingUtilities
import javax.swing.WindowConstants


object Demo01App {

    val APP_TITLE = "Demo01"


    def apply(label: String): Demo01App = new Demo01App(label)
}


final class Demo01App private (private val _label: String) {


    def start(): Unit = SwingUtilities.invokeLater(() => setupGui())


    private def setupGui(): Unit = {

        val frame: JFrame = new JFrame(Demo01App.APP_TITLE)
        val label: JLabel = new JLabel(_label)

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
        frame.getContentPane().add(label)
        frame.pack()
        frame.setVisible(true)
    }

}
