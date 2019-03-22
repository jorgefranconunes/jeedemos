/**************************************************************************
 *
 * Copyright (c) 2019 Jorge Nunes
 *
 **************************************************************************/

package com.varmateo.jeedemos.swing.demo04

import java.awt.Dimension
import javax.swing.JLabel
import javax.swing.JPanel

import com.varmateo.jeedemos.swing.BoxPanelOrientation
import com.varmateo.jeedemos.swing.BoxPanel
import com.varmateo.jeedemos.swing.BoxPanelItem.EmptyFiller
import com.varmateo.jeedemos.swing.BoxPanelItem.Regular


object SizePanel {


    def create: SizePanel = new SizePanel
}


/**
 * 
 */
final class SizePanel private () {


    private val label: JLabel = new JLabel("Size:")

    private val content: JLabel = new JLabel()


    /**
     * 
     */
    val panel: JPanel = BoxPanel.create(
        items = Seq(Regular(label), Regular(content), EmptyFiller),
        orientation = BoxPanelOrientation.X_AXIS,
        margin = 0)


    /**
     * 
     */
    def update(dim: Dimension): Unit = {

        val textContent: String = s"${dim.width} x ${dim.height}"

        this.content.setText(textContent)
    }
}
