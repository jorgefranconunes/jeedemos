/**************************************************************************
 *
 * Copyright (c) 2019 Jorge Nunes
 *
 **************************************************************************/

package com.varmateo.jeedemos.swing.demo05

import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.event.ComponentListener
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JSeparator
import javax.swing.SwingConstants
import javax.swing.SwingUtilities
import javax.swing.Timer
import javax.swing.WindowConstants

import com.varmateo.jeedemos.swing.BoxPanelOrientation
import com.varmateo.jeedemos.swing.BoxPanel
import com.varmateo.jeedemos.swing.BoxPanelItem
import com.varmateo.jeedemos.swing.BoxPanelItem.EmptyFiller
import com.varmateo.jeedemos.swing.BoxPanelItem.Filler
import com.varmateo.jeedemos.swing.BoxPanelItem.Regular
import com.varmateo.jeedemos.swing.demo04.AutofitLabel
import com.varmateo.jeedemos.swing.demo04.SizePanel


object Demo05App {

    private val APP_TITLE = "Demo05"

    private val UPDATE_PERIOD: Int = 100 // milliseconds

    private val TIME_FORMATTER: DateTimeFormatter =
        new DateTimeFormatterBuilder()
            .appendPattern("HH:mm:ss.SSS")
            .toFormatter()


    def apply(): Demo05App = new Demo05App()
}


final class Demo05App private () {

    import Demo05App._


    private val sizePanel: SizePanel = SizePanel.create()
    private val autofitLabel: AutofitLabel = AutofitLabel.create()
    private val timer: Timer = new Timer(UPDATE_PERIOD, _ => onTimerTick())


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

        timer.start()
    }


    private def setupLabel(): JComponent = {

        updateLabelContents()

        val onResizeListener: ComponentListener = new ComponentAdapter {
            override def componentResized(event: ComponentEvent): Unit = onResize(autofitLabel)
        }

        val label: JLabel = autofitLabel.component

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


    private def updateLabelContents(): Unit = {

        val currentTime: LocalDateTime = LocalDateTime.now()
        val contents: String = TIME_FORMATTER.format(currentTime)

        autofitLabel.component.setText(contents);

    }

    private def onResize(autofitLabel: AutofitLabel): Unit = sizePanel.update(autofitLabel.details)


    private def onExit(): Unit = System.exit(0)


    private def onTimerTick(): Unit = updateLabelContents()


    private def perform(action: () => Unit): Unit =
        SwingUtilities.invokeLater(() => action())

}
