/**************************************************************************
 *
 * Copyright (c) 2019 Jorge Nunes
 *
 **************************************************************************/

package com.varmateo.jeedemos.swing.demo04

import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.event.ComponentListener
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JSeparator
import javax.swing.SwingConstants
import javax.swing.SwingUtilities
import javax.swing.WindowConstants

import com.varmateo.jeedemos.swing.BoxPanelOrientation
import com.varmateo.jeedemos.swing.BoxPanel
import com.varmateo.jeedemos.swing.BoxPanelItem
import com.varmateo.jeedemos.swing.BoxPanelItem.EmptyFiller
import com.varmateo.jeedemos.swing.BoxPanelItem.Filler
import com.varmateo.jeedemos.swing.BoxPanelItem.Regular


object Demo04App {

    private val APP_TITLE = "Demo04"


    def apply(): Demo04App = new Demo04App()
}


final class Demo04App private () {

    import Demo04App._


    private val sizePanel: SizePanel = SizePanel.create()
    private val autofitLabel: AutofitLabel = AutofitLabel.create()


    def start(): Unit = perform(() => setupGui())


    private def setupGui(): Unit = {

        val label: JComponent = setupLabel()
        val items: Seq[BoxPanelItem] = Seq(
            Filler(label),
            Regular(new JSeparator(SwingConstants.HORIZONTAL)),
            Regular(sizePanel.panel),
            Regular(createButtonPanel()))
        val panel: JPanel = BoxPanel.create(items, BoxPanelOrientation.Y_AXIS)

        val frame: JFrame = createMainFrame()
        frame.getContentPane().add(panel)
        frame.pack()
        frame.setVisible(true)
    }


    private def setupLabel(): JComponent = {

        val label: JLabel = autofitLabel.component

        label.setText("Resize this window!")

        val onResizeListener: ComponentListener = new ComponentAdapter {
            override def componentResized(event: ComponentEvent): Unit = onResize(autofitLabel)
        }

        label.addComponentListener(onResizeListener)

        label
    }


    private def createButtonPanel(): JComponent = {

        val button: JButton = new JButton("Exit")
        button.addActionListener(_ => onExit())

        val items: Seq[BoxPanelItem] = Seq(
            EmptyFiller,
            Regular(button))

        BoxPanel.create(
            items = items,
            orientation = BoxPanelOrientation.X_AXIS,
            margin = 0)
    }


    private def createMainFrame(): JFrame = {

        val frame: JFrame = new JFrame(APP_TITLE)
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE)

        val onCloseListener: WindowListener = new WindowAdapter {
            override def windowClosing(event: WindowEvent): Unit = onExit()
        }

        frame.addWindowListener(onCloseListener)

        frame
    }


    private def onResize(autofitLabel: AutofitLabel): Unit = {

        sizePanel.update(autofitLabel.details)
    }


    private def onExit(): Unit =
        System.exit(0)


    private def perform(action: () => Unit): Unit =
        SwingUtilities.invokeLater(() => action())

}
