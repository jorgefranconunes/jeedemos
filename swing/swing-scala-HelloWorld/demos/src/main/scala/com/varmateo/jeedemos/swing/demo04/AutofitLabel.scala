/**************************************************************************
 *
 * Copyright (c) 2019 Jorge Nunes
 *
 **************************************************************************/

package com.varmateo.jeedemos.swing.demo04

import java.awt.Dimension
import java.awt.Font
//import java.awt.Insets
import java.awt.font.FontRenderContext
import java.awt.geom.Rectangle2D
import java.awt.Graphics
import java.awt.Graphics2D
//import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.SwingConstants


object AutofitLabel {


    def create(): AutofitLabel = new AutofitLabel()
}


/**
 * 
 */
final class AutofitLabel private () {


    private val labelComponent: AutofitLabelComponent = new AutofitLabelComponent


    /**
     * 
     */
    val component: JLabel = labelComponent


    /**
     * 
     */
    def textBoxSize: Dimension = labelComponent.textBoxSize


    private final class AutofitLabelComponent
            extends JLabel {


        private var isFirstPaint: Boolean = true
        private var isTextChanged: Boolean = false
        private var isFontChanged: Boolean = false
        private var previousSize: Dimension = new Dimension(0, 0)
        private var currentTextBoxSize: Dimension = new Dimension(0, 0)


        setHorizontalAlignment(SwingConstants.CENTER)
        setVerticalAlignment(SwingConstants.CENTER)


        /**
         * 
         */
        override def paintComponent(g: Graphics): Unit = {

            performRefitIfNeeded(g)

            super.paintComponent(g)

            // val g2: Graphics2D = g.asInstanceOf[Graphics2D]

            // g.setFont(getFont())
            // g.setColor(java.awt.Color.BLACK)

            // g2.drawString(text, 0, getHeight)
        }


        /**
         * 
         */
        override def setText(text: String): Unit = {

            this.isTextChanged = true
            super.setText(text)
        }


        /**
         * 
         */
        override def setFont(font: Font): Unit = {

            this.isFontChanged = true
            super.setFont(font)
        }


        def textBoxSize: Dimension = this.currentTextBoxSize


        // private def innerSize: Dimension = {

        //     val insets: Insets = getInsets
        //     val size: Dimension = getSize

        //     new Dimension(
        //         size.width - insets.left - insets.right,
        //         size.height - insets.top - insets.bottom)

        // }


        private def performRefitIfNeeded(g: Graphics): Unit = {

            if ( isRefitNeeded() ) {
                val g2: Graphics2D = g.asInstanceOf[Graphics2D]
                performRefit(g2)
            }
        }


        private def isRefitNeeded(): Boolean = {

            val currentSize: Dimension = getSize
            val isSizeChanged: Boolean = currentSize != this.previousSize
            val result: Boolean =
                this.isFirstPaint || this.isTextChanged || this.isFontChanged || isSizeChanged

            this.isFirstPaint = false
            this.isTextChanged = false
            this.isFontChanged = false
            this.previousSize = currentSize

            result
        }


        private def performRefit(g2: Graphics2D): Unit = {

            val currentText: String = getText
            val currentTextSize: Dimension = sizeOfText(currentText, g2)
            val currentSize: Dimension = getSize //innerSize

            val fontScaleX: Double = currentSize.width.toDouble / currentTextSize.width.toDouble
            val fontScaleY: Double = currentSize.height.toDouble / currentTextSize.height.toDouble
            val fontScale: Double = Math.min(fontScaleX, fontScaleY)


            if ( fontScale != 1.0 ) {
                val currentFont: Font = g2.getFont
                val scaledFontSize: Double = currentFont.getSize2D * fontScale
                println(s"*** fontScale = ${fontScale}, scaledFontSize = ${scaledFontSize}")
                val scaledFont: Font = currentFont.deriveFont(scaledFontSize.toFloat)
                g2.setFont(scaledFont)
                this.currentTextBoxSize = sizeOfText(currentText, g2)
            } else {
                println(s"*** fontScale = ${fontScale}")
                this.currentTextBoxSize = currentTextSize
            }
        }



        private def sizeOfText(
            text: String,
            g2: Graphics2D): Dimension = {

            val font: Font = g2.getFont()
            val context: FontRenderContext = g2.getFontRenderContext()
            val bounds: Rectangle2D = font.getStringBounds(text, context)

            new Dimension(
                Math.ceil(bounds.getWidth).toInt,
                Math.ceil(bounds.getHeight).toInt)
        }
    }

}
