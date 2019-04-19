/**************************************************************************
 *
 * Copyright (c) 2019 Jorge Nunes
 *
 **************************************************************************/

package com.varmateo.jeedemos.swing

import java.awt.Dimension
import java.awt.Font
import java.awt.Graphics2D
import java.awt.font.FontRenderContext
import java.awt.geom.Rectangle2D


/**
 * @param text The text for which the font applies.
 *
 * @param bounds Original bounds the rendered text satisfies.
 *
 * @param font The font that when used to render the text will have
 * the largest size possible still contained in bounds.
 *
 * @param size The actual size of the rendered text with the font.
 */
final case class FontFitResult(
    text: String,
    bounds: Dimension,
    font: Font,
    size: Dimension)


object FontFitter {

    def findFittingFont(
        text: String,
        bounds: Dimension,
        g2: Graphics2D): FontFitResult = {

        val baseFont: Font = g2.getFont
        val context: FontRenderContext = g2.getFontRenderContext
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

        FontFitResult(text, bounds, candidateFont, textSize)
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
