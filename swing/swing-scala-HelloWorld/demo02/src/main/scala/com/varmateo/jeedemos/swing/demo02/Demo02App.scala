/**************************************************************************
 *
 * Copyright (c) 2019 Jorge Nunes
 *
 **************************************************************************/

package com.varmateo.jeedemos.swing.demo02

import javax.swing.JComponent
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.SwingUtilities

import com.varmateo.jeedemos.swing.BoxPanel


object Demo02App {

    val APP_TITLE = "Demo02"


    def apply(labels: Seq[String]): Demo02App = new Demo02App(labels)
}


final class Demo02App private (private val labels: Seq[String]) {


    def start(): Unit = SwingUtilities.invokeLater(() => setupGui())


    private def setupGui(): Unit = {

        val frame: JFrame = new JFrame(Demo02App.APP_TITLE)
        val labelsPanel: JComponent = buildLabelsPanel(this.labels)

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        frame.getContentPane().add(labelsPanel)
        frame.pack()
        frame.setVisible(true)
    }


    private def buildLabelsPanel(labels: Seq[String]): JComponent = {

        val box: BoxPanel = BoxPanel.vBoxWithMargin()

        labels.map(new JLabel(_))
            .foreach(box.add(_))

        box.panel
    }

}
