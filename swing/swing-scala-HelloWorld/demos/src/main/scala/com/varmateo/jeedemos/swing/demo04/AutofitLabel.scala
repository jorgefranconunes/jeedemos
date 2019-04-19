/**************************************************************************
 *
 * Copyright (c) 2019 Jorge Nunes
 *
 **************************************************************************/

package com.varmateo.jeedemos.swing.demo04

import java.awt.Dimension
import java.awt.Font
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JLabel
import javax.swing.SwingConstants

import com.varmateo.jeedemos.swing.FontFitResult
import com.varmateo.jeedemos.swing.FontFitter


case class AutofitLabelDetails(
    size: Dimension,
    font: Font,
    textBoxSize: Dimension)


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
    def details: AutofitLabelDetails = labelComponent.details


    private final class AutofitLabelComponent
            extends JLabel {


        private var isFirstPaint: Boolean = true
        private var isTextChanged: Boolean = false
        private var isFontChanged: Boolean = false

        private var previousSize: Dimension = new Dimension(0, 0)
        private var currentFont: Font = getFont
        private var currentTextBoxSize: Dimension = new Dimension(0, 0)


        setHorizontalAlignment(SwingConstants.CENTER)
        setVerticalAlignment(SwingConstants.CENTER)


        /**
         * 
         */
        override def paintComponent(g: Graphics): Unit = {

            val fittingFont: Font = currentFittingFont(g)
            g.setFont(fittingFont)

            super.paintComponent(g)
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


        /**
         * 
         */
        def details: AutofitLabelDetails = AutofitLabelDetails(
            size = getSize,
            font = this.currentFont,
            textBoxSize = this.currentTextBoxSize)


        private def currentFittingFont(g: Graphics): Font = {

            if ( isRefitNeeded() ) {
                val g2: Graphics2D = g.asInstanceOf[Graphics2D]
                this.currentFont = fittingFont(g2)
            }

            this.currentFont
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


        private def fittingFont(g2: Graphics2D): Font = {

            val FontFitResult(_, _, desiredFont: Font, textBoxSize: Dimension) =
                FontFitter.findFittingFont(
                    text = getText,
                    bounds = getSize,
                    g2 = g2)

            this.currentTextBoxSize = textBoxSize

            desiredFont
        }

    }

}
