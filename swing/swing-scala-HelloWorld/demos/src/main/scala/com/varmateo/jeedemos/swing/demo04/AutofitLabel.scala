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

            println(s"**** Panel: ${getSize().width}x${getSize().height}; Text: ${currentTextBoxSize.width}x${currentTextBoxSize.height}; Font: ${this.currentFont.getSize2D}")

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

            val (desiredFont: Font, textBoxSize: Dimension) = findFittingFont(
                text = getText,
                bounds = getSize,
                baseFont = g2.getFont,
                context = g2.getFontRenderContext)

            this.currentTextBoxSize = textBoxSize

            desiredFont
        }


        private def findFittingFont(
            text: String,
            bounds: Dimension,
            baseFont: Font,
            context: FontRenderContext): (Font, Dimension) = {

            val boundsWidth: Int = bounds.width
            val boundsHeight: Int = bounds.height
            var lowerSize: Float = 4.0f
            var upperSize: Float = 288.0f
            var candidateSize: Float = baseFont.getSize2D
            var candidateFont: Font = baseFont
            var textSize: Dimension = sizeOfText(text, candidateFont, context)
            var isFontFound: Boolean = false

            do {
                if ( ((upperSize - lowerSize) / lowerSize) <= 0.01f ) {
                    isFontFound = true
                } else if ( isAcceptableSize(textSize, bounds) ) {
                    isFontFound = true
                } else {
                    if ( (textSize.width > boundsWidth) || (textSize.height > boundsHeight) ) {
                        upperSize = candidateSize
                    } else {
                        lowerSize = candidateSize
                    }
                    candidateSize = (upperSize + lowerSize) / 2.0f
                    candidateFont = baseFont.deriveFont(candidateSize)
                    textSize = sizeOfText(text, candidateFont, context)
                }

            } while ( !isFontFound )

            if ( (textSize.width > boundsWidth) || (textSize.height > boundsHeight) ) {
                candidateSize = lowerSize
                candidateFont = baseFont.deriveFont(candidateSize)
                textSize = sizeOfText(text, candidateFont, context)
            }

            (candidateFont, textSize)
        }


        private def isAcceptableSize(
            textSize: Dimension,
            bounds: Dimension): Boolean =
            ((textSize.width == bounds.width) && (textSize.height <= bounds.height)) ||
                ((textSize.width <= bounds.width) && (textSize.height == bounds.height))


        private def sizeOfText(
            text: String,
            font: Font,
            context: FontRenderContext): Dimension = {

            val bounds: Rectangle2D = font.getStringBounds(text, context)

            new Dimension(
                Math.ceil(bounds.getWidth).toInt,
                Math.ceil(bounds.getHeight).toInt)
        }
    }

}
