/**************************************************************************
 *
 * Copyright (c) 2019 Jorge Nunes
 *
 **************************************************************************/

package com.varmateo.jeedemos.swing.demo02

import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingUtilities
import javax.swing.WindowConstants

import com.varmateo.jeedemos.swing.BoxPanel
import com.varmateo.jeedemos.swing.BoxPanelItem._
import com.varmateo.jeedemos.swing.BoxPanelOrientation


object Demo02App {

    val APP_TITLE = "Demo02"


    def apply(labels: Seq[String]): Demo02App = new Demo02App(labels)
}


final class Demo02App private (private val labels: Seq[String]) {


    def start(): Unit = SwingUtilities.invokeLater(() => setupGui())


    private def setupGui(): Unit = {

        val frame: JFrame = new JFrame(Demo02App.APP_TITLE)
        val labels: Seq[JLabel] = this.labels.map(new JLabel(_))
        val panel: JPanel = BoxPanel.create(
            items = labels.map(Regular(_)),
            orientation = BoxPanelOrientation.X_AXIS)

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
        frame.getContentPane().add(panel)
        frame.pack()
        frame.setVisible(true)
    }

}
