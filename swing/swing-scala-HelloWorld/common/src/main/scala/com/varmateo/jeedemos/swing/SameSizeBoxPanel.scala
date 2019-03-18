/**************************************************************************
 *
 * Copyright (c) 2019 Jorge Nunes
 *
 **************************************************************************/

package com.varmateo.jeedemos.swing

import java.awt.Component
import java.awt.GridLayout
import java.awt.LayoutManager

import javax.swing.JPanel
import javax.swing.border.EmptyBorder


/**
 * A factory for containers for laying out components in a row or in a
 * column, showing all components with the same size.
 */
object SameSizeBoxPanel {


    private val DEFAULT_SPACING: Int = 8
    private val DEFAULT_MARGIN: Int = 16


    /**
     *
     */
    def create(
        components: Iterable[Component],
        orientation: BoxPanelOrientation = BoxPanelOrientation.X_AXIS,
        spacing: Int = DEFAULT_SPACING,
        margin: Int = DEFAULT_MARGIN) = {

        val box: JPanel  = new JPanel()

	if ( margin != 0 ) {
	    box.setBorder(new EmptyBorder(margin, margin, margin, margin))
	}

        val layoutManager: LayoutManager =
                if ( orientation == BoxPanelOrientation.X_AXIS)
                    new GridLayout(1, 0, spacing, 0)
                else
                    new GridLayout(0, 1, 0, spacing)

        box.setLayout(layoutManager)

        components.foreach(box.add(_))

        box
    }

}
