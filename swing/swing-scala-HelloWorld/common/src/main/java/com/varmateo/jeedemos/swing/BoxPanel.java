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
    private final boolean             _sameSize;
    private final JPanel              _box;

    private boolean _requiresStrut  = false;
    private int     _componentCount = 0;


    /**
     *
     */
    private BoxPanel(
            final BoxPanelOrientation orientation,
            final int                 spacing,
            final int                 margin,
            final boolean             sameSize) {

	_orientation = orientation;
	_spacing     = spacing;
	_margin      = margin;
	_sameSize    = sameSize;
	_box         = new JPanel();

	if ( margin != 0 ) {
	    _box.setBorder(new EmptyBorder(margin, margin, margin, margin));
	}

        final LayoutManager layoutManager;

	if ( sameSize ) {
	    if ( orientation == BoxPanelOrientation.X_AXIS ) {
		layoutManager = new GridLayout(1, 0, spacing, 0);
	    } else {
		layoutManager = new GridLayout(0, 1, 0, spacing);
	    }
	} else {
            layoutManager = new BoxPanelLayout(_box, orientation);
	}

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

	++_componentCount;

	return this;
    }


    /**
     *
     */
    private void addHorizontal(
            final Component    component,
            final BoxPanelFill fill) {

	if ( _sameSize ) {
	    addComponent(component, null);
	} else {
	    if ( _requiresStrut ) {
		final Component strut = Box.createHorizontalStrut(_spacing);
		addComponent(strut, BoxPanelFill.NO);
	    } else {
		// For next component.
		_requiresStrut = true;
	    }
	    addComponent(component, fill);
	}
    }


    /**
     *
     */
    private void addVertical(
            final Component    component,
            final BoxPanelFill fill) {

	if ( _sameSize ) {
	    addComponent(component, null);
	} else {
	    if ( _requiresStrut ) {
		final Component strut = Box.createVerticalStrut(_spacing);
		addComponent(strut, BoxPanelFill.NO);
	    } else {
		// For next component.
		_requiresStrut = true;
	    }
	    addComponent(component, fill);
	}
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
	_componentCount = 0;

	_box.removeAll();

        return this;
    }


    /**
     *
     */
    public static BoxPanel hBox() {

        return new BoxPanel(BoxPanelOrientation.X_AXIS, DEFAULT_SPACING, 0, false);
    }


    /**
     *
     */
    public static BoxPanel hBoxSameSize() {

        return new BoxPanel(BoxPanelOrientation.X_AXIS, DEFAULT_SPACING, 0, true);
    }


    /**
     *
     */
    public static BoxPanel hBoxWithMargin() {

        return new BoxPanel(BoxPanelOrientation.X_AXIS, DEFAULT_SPACING, DEFAULT_MARGIN, false);
    }


    /**
     *
     */
    public static BoxPanel hBoxSameSizeWithMargin() {

        return new BoxPanel(BoxPanelOrientation.X_AXIS, DEFAULT_SPACING, DEFAULT_MARGIN, true);
    }


    /**
     *
     */
    public static BoxPanel vBox() {

        return new BoxPanel(BoxPanelOrientation.Y_AXIS, DEFAULT_SPACING, 0, false);
    }


    /**
     *
     */
    public static BoxPanel vBoxSameSize() {

	return new BoxPanel(BoxPanelOrientation.Y_AXIS, DEFAULT_SPACING, 0, true);
    }


    /**
     *
     */
    public static BoxPanel vBoxWithMargin() {

        return new BoxPanel(BoxPanelOrientation.Y_AXIS, DEFAULT_SPACING, DEFAULT_MARGIN, false);
    }


    /**
     *
     */
    public static BoxPanel vBoxSameSizeWithMargin() {

        return new BoxPanel(BoxPanelOrientation.Y_AXIS, DEFAULT_SPACING, DEFAULT_MARGIN, true);
    }

}
