/**************************************************************************
 *
 * Copyright (c) 2019 Jorge Nunes
 *
 **************************************************************************/

package com.varmateo.jeedemos.swing.demo04

import java.awt.Graphics
//import java.awt.Graphics2D
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.SwingConstants


object AutofitLabel {


    def create(text: String): AutofitLabel = new AutofitLabel(text)
}


/**
 * 
 */
final class AutofitLabel private (text: String) {


    private val label: JComponent = new AutofitLabelComponent(text)


    /**
     * 
     */
    def component: JComponent = label


    private final class AutofitLabelComponent (text: String)
            extends JLabel(text, SwingConstants.CENTER) {


        override def paintComponent(g: Graphics): Unit = {

            super.paintComponent(g)

            // val g2: Graphics2D = g.asInstanceOf[Graphics2D]

            // g.setFont(getFont())
            // g.setColor(java.awt.Color.BLACK)

            // g2.drawString(text, 0, getHeight)
        }
    }

}
