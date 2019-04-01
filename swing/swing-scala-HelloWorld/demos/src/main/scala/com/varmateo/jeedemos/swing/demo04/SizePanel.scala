/**************************************************************************
 *
 * Copyright (c) 2019 Jorge Nunes
 *
 **************************************************************************/

package com.varmateo.jeedemos.swing.demo04

import java.awt.Dimension
import java.awt.Font
import javax.swing.JLabel
import javax.swing.JPanel

import com.varmateo.jeedemos.swing.BoxPanelOrientation
import com.varmateo.jeedemos.swing.BoxPanel
import com.varmateo.jeedemos.swing.BoxPanelItem.EmptyFiller
import com.varmateo.jeedemos.swing.BoxPanelItem.Regular


object SizePanel {


    def create(): SizePanel = new SizePanel
}


/**
 * 
 */
final class SizePanel private () {


    private val panelSizeLabel: JLabel = new JLabel("Panel: 0000x0000")
    private val textBoxSizeLabel: JLabel = new JLabel("Text box: 000x000")
    private val fontLabel: JLabel = new JLabel("Font: XXXXXX XXXpt")



    /**
     * 
     */
    val panel: JPanel = BoxPanel.create(
        items = Seq(
            Regular(panelSizeLabel),
            Regular(textBoxSizeLabel),
            Regular(fontLabel),
            EmptyFiller),
        orientation = BoxPanelOrientation.X_AXIS,
        margin = 0)


    /**
     * 
     */
    def update(details: AutofitLabelDetails): Unit = {

        val panelSize: Dimension = details.size
        val textBoxSize: Dimension = details.textBoxSize
        val font: Font = details.font

        val panelSizeContent: String = s"Panel: ${panelSize.width}x${panelSize.height}"
        this.panelSizeLabel.setText(panelSizeContent)

        val textBoxSizeContent: String = s"Text box: ${textBoxSize.width}x${textBoxSize.height}"
        this.textBoxSizeLabel.setText(textBoxSizeContent)

        val fontSize: String = "%.2f".format(font.getSize2D)
        val fontContent: String = s"Font: ${font.getName} ${fontSize}pt"
        this.fontLabel.setText(fontContent)
    }
}
