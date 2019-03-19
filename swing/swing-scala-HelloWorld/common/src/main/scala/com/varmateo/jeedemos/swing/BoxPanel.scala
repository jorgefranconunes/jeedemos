/**************************************************************************
 *
 * Copyright (c) 2019 Jorge Nunes
 *
 **************************************************************************/

package com.varmateo.jeedemos.swing

import java.awt.Component;
import java.awt.LayoutManager

import javax.swing.Box;
import javax.swing.JPanel
import javax.swing.border.EmptyBorder

import scala.annotation.tailrec

import com.varmateo.jeedemos.swing.BoxPanelItem._


/**
 * A factory for containers for laying out components in a row or in a
 * column.
 */
object BoxPanelX {


    private val DEFAULT_SPACING: Int = 8
    private val DEFAULT_MARGIN: Int = 16


    /**
     * 
     */
    def create(
        items: Iterable[BoxPanelItem],
        orientation: BoxPanelOrientation = BoxPanelOrientation.X_AXIS,
        spacing: Int = DEFAULT_SPACING,
        margin: Int = DEFAULT_MARGIN): JPanel = {

        val box: JPanel = new JPanel

	if ( margin != 0 ) {
	    box.setBorder(new EmptyBorder(margin, margin, margin, margin))
	}

        val layoutManager: LayoutManager = new BoxPanelLayout(box, orientation)
        box.setLayout(layoutManager)

        addAllToBox(box, items, orientation, spacing, false)

        box
    }


    @tailrec
    private def addAllToBox(
        box: JPanel,
        items: Iterable[BoxPanelItem],
        orientation: BoxPanelOrientation,
        spacing: Int,
        requiresStrut: Boolean) : Unit = {

        items.headOption match {
            case Some(item) => {
                val nextItemRequiresStrut = addToBox(
                    box, item, orientation, spacing, requiresStrut)

                addAllToBox(box, items.tail, orientation, spacing, nextItemRequiresStrut)
            }
            case None => // Nothing else to do.
        }
    }


    private def addToBox(
        box: JPanel,
        item: BoxPanelItem,
        orientation: BoxPanelOrientation,
        spacing: Int,
        requiresStrut: Boolean) : Boolean = {

        item match {
            case Regular(component) => {
                if ( requiresStrut ) {
                    val strut: Component = buildStrut(orientation, spacing)
                    box.add(strut, BoxPanelFill.NO)
                }
                box.add(component, BoxPanelFill.NO)
                true
            }
            case Filler(component) => {
                if ( requiresStrut ) {
                    val strut: Component = buildStrut(orientation, spacing)
                    box.add(strut, BoxPanelFill.NO)
                }
                box.add(component, BoxPanelFill.YES)
                true
            }
            case EmptyFiller => {
                val filler: Component = Box.createGlue()
                box.add(filler, BoxPanelFill.FILLER)
                false
            }
        }
    }


    private def buildStrut(
        orientation: BoxPanelOrientation,
        spacing: Int): Component = {

        if ( orientation == BoxPanelOrientation.X_AXIS )
            Box.createHorizontalStrut(spacing)
        else
            Box.createVerticalStrut(spacing)
    }

}
