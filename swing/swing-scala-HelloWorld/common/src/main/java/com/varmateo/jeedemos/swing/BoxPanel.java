/**************************************************************************
 *
 * Copyright (c) 2019 Jorge Nunes
 *
 **************************************************************************/

package com.varmateo.jeedemos.swing;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


/**
 * A container for laying out components in a row or in a column.
 */
public final class BoxPanel {


    private static final int DEFAULT_SPACING = 8;
    private static final int DEFAULT_MARGIN  = 16;


    private final BoxPanelOrientation _orientation;
    private final int                 _spacing;
    private final int                 _margin;
    private final JPanel              _box;

    private boolean _requiresStrut  = false;


    /**
     *
     */
    private BoxPanel(
            final BoxPanelOrientation orientation,
            final int                 spacing,
            final int                 margin) {

	_orientation = orientation;
	_spacing     = spacing;
	_margin      = margin;
	_box         = new JPanel();

	if ( margin != 0 ) {
	    _box.setBorder(new EmptyBorder(margin, margin, margin, margin));
	}

        final LayoutManager layoutManager = new BoxPanelLayout(_box, orientation);

        _box.setLayout(layoutManager);
    }


    /**
     *
     */
    public JPanel panel() {

        return _box;
    }


    /**
     *
     */
    public BoxPanel add(final Component component) {

	return add(component, BoxPanelFill.NO);
    }


    /**
     *
     */
    public BoxPanel add(
            final Component    component,
            final BoxPanelFill fill) {

	if ( _orientation == BoxPanelOrientation.X_AXIS ) {
	    addHorizontal(component, fill);
	} else {
	    addVertical(component, fill);
	}

	return this;
    }


    /**
     *
     */
    private void addHorizontal(
            final Component    component,
            final BoxPanelFill fill) {

        if ( _requiresStrut ) {
            final Component strut = Box.createHorizontalStrut(_spacing);
            addComponent(strut, BoxPanelFill.NO);
        } else {
            // For next component.
            _requiresStrut = true;
        }
        addComponent(component, fill);
    }


    /**
     *
     */
    private void addVertical(
            final Component    component,
            final BoxPanelFill fill) {

        if ( _requiresStrut ) {
            final Component strut = Box.createVerticalStrut(_spacing);
            addComponent(strut, BoxPanelFill.NO);
        } else {
            // For next component.
            _requiresStrut = true;
        }
        addComponent(component, fill);
    }


    /**
     *
     */
    private void addComponent(
            final Component component,
            final BoxPanelFill constraints) {

        _box.add(component, constraints);
    }


    /**
     *
     */
    public BoxPanel addFiller() {

	final Component filler = Box.createGlue();

	addComponent(filler, BoxPanelFill.FILLER);
	_requiresStrut = false;

        return this;
    }


    /**
     *
     */
    public BoxPanel removeAll() {

	_requiresStrut  = false;
	_box.removeAll();

        return this;
    }


    /**
     *
     */
    public static BoxPanel hBox() {

        return new BoxPanel(BoxPanelOrientation.X_AXIS, DEFAULT_SPACING, 0);
    }


    /**
     *
     */
    public static BoxPanel hBoxWithMargin() {

        return new BoxPanel(BoxPanelOrientation.X_AXIS, DEFAULT_SPACING, DEFAULT_MARGIN);
    }


    /**
     *
     */
    public static BoxPanel vBox() {

        return new BoxPanel(BoxPanelOrientation.Y_AXIS, DEFAULT_SPACING, 0);
    }


    /**
     *
     */
    public static BoxPanel vBoxWithMargin() {

        return new BoxPanel(BoxPanelOrientation.Y_AXIS, DEFAULT_SPACING, DEFAULT_MARGIN);
    }

}
