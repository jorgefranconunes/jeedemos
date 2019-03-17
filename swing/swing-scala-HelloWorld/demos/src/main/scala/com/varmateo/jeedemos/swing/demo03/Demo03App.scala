/**************************************************************************
 *
 * Copyright (c) 2019 Jorge Nunes
 *
 **************************************************************************/

package com.varmateo.jeedemos.swing.demo03

import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.SwingUtilities

import com.varmateo.jeedemos.swing.BoxPanelOrientation
import com.varmateo.jeedemos.swing.SameSizeBoxPanel


object Demo03App {

    val APP_TITLE = "Demo03"


    def apply(labels: Seq[String]): Demo03App = new Demo03App(labels)
}


final class Demo03App private (private val labels: Seq[String]) {


    def start(): Unit = SwingUtilities.invokeLater(() => setupGui())


    private def setupGui(): Unit = {

        val buttons: Seq[JButton] = this.labels.map(new JButton(_))
        val panel: JPanel = SameSizeBoxPanel.create(
            components = buttons,
            orientation = BoxPanelOrientation.X_AXIS)

        val frame: JFrame = new JFrame(Demo03App.APP_TITLE)
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        frame.getContentPane().add(panel)
        frame.pack()
        frame.setVisible(true)
    }

}
