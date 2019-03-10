/**************************************************************************
 *
 * Copyright (c) 2019 Jorge Nunes
 *
 **************************************************************************/

package com.varmateo.jeedemos.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.awt.Rectangle;

import java.util.HashMap;
import java.util.Map;


/**
 *
 */
final class BoxPanelLayout
    implements LayoutManager2 {


    private final Container _target;
    private final BoxPanelOrientation _orientation;
    private final Map<Component, BoxPanelFill> _constraintsByComponent;

    private SizeRequirement _xTotal = null;
    private SizeRequirement _yTotal = null;


    /**
     *
     */
    public BoxPanelLayout(
            final Container target,
            final BoxPanelOrientation orientation) {

	_target = target;
	_orientation = orientation;
        _constraintsByComponent = new HashMap<>();
    }


    /**
     *
     */
    @Override
    public void addLayoutComponent(
            final Component component,
            final Object    constraint) {

        final BoxPanelFill actualConstraint;

        if ( constraint == null ) {
            actualConstraint = BoxPanelFill.NO;
        } else if ( constraint instanceof BoxPanelFill ) {
            actualConstraint = (BoxPanelFill) constraint;
        } else {
            actualConstraint = BoxPanelFill.NO;
        }

	_constraintsByComponent.put(component, actualConstraint);
    }


    /**
     *
     */
    @Override
    public void addLayoutComponent(
            final String    name,
            final Component component) {

	// Nothing to do. This component will be considered as having
	// a BoxPanelFill.NO constraint.
    }


    /**
     *
     */
    @Override
    public float getLayoutAlignmentX(final Container target) {

	checkContainer(target);

	return 0.5f;
    }


    /**
     *
     */
    @Override
    public float getLayoutAlignmentY(final Container target) {

	checkContainer(target);

	return 0.5f;
    }


    /**
     *
     */
    @Override
    public void invalidateLayout(final Container target) {

	checkContainer(target);

	_xTotal = null;
	_yTotal = null;
    }


    /**
     *
     */
    @Override
    public void layoutContainer(final Container target) {

	checkContainer(target);

	// We will call "space" to the size of a component along the
	// layout axis.

	final int childrenCount   = target.getComponentCount();
	int minRubberSpace  = 0;
	int prefRubberSpace = 0;
	int prefFixedSpace  = 0;
	int minFixedSpace   = 0;
	int fillerCount     = 0;

	for ( int i=0; i<childrenCount; ++i ) {
	    final Component    component  = target.getComponent(i);
	    final int          minSpace   = getMinimumSpace(component);
	    final int          prefSpace  = getPreferredSpace(component);
	    final BoxPanelFill constraint = getConstraint(component);

	    if ( constraint == BoxPanelFill.YES ) {
		minRubberSpace += minSpace;
		prefRubberSpace += prefSpace;
	    } else if ( constraint == BoxPanelFill.NO ) {
		minFixedSpace += minSpace;
		prefFixedSpace += prefSpace;
	    } else {
		++fillerCount;
	    }
	}

	final Dimension size          = target.getSize();
	final Insets    insets        = target.getInsets();
	final Dimension alocatedSize  = new Dimension(
                size.width - insets.left - insets.right,
                size.height - insets.top - insets.bottom);
	final int parallelSpace       =
	    (_orientation==BoxPanelOrientation.X_AXIS) ? alocatedSize.width : alocatedSize.height;
	final int orthogonalSpace     =
	    (_orientation==BoxPanelOrientation.X_AXIS) ? alocatedSize.height : alocatedSize.width;

	final Rectangle[] bounds;

	if ( parallelSpace >= (minRubberSpace + prefFixedSpace) ) {
	    bounds = doLayout0(target, parallelSpace, orthogonalSpace, prefFixedSpace, prefRubberSpace, fillerCount);
	} else if ( parallelSpace >= (minRubberSpace + minFixedSpace) ) {
	    bounds = doLayout1(target, parallelSpace, orthogonalSpace, prefFixedSpace, minRubberSpace);
	} else if ( parallelSpace >= minFixedSpace ) {
	    bounds = doLayout2(target, parallelSpace, orthogonalSpace, minFixedSpace, prefRubberSpace);
	} else {
	    bounds = doLayout3(target, parallelSpace, orthogonalSpace, prefFixedSpace);
	}

	positionChildren(target, bounds);
    }


    /**
     *
     */
    private void positionChildren(
            final Container   target,
            final Rectangle[] bounds) {

	final Insets insets = target.getInsets();

	for (int i=0, count=target.getComponentCount(); i<count; ++i ) {
	    final Component component  = target.getComponent(i);
	    final Rectangle boundsInfo = bounds[i];
	    final long      xOffsetL   = (long)insets.left + (long)boundsInfo.x;
	    final long      yOffsetL   = (long)insets.top + (long)boundsInfo.y;
	    final int       xOffset    = (int)Math.min(xOffsetL, Integer.MAX_VALUE);
	    final int       yOffset    = (int)Math.min(yOffsetL, Integer.MAX_VALUE);

	    component.setBounds(xOffset, yOffset, boundsInfo.width, boundsInfo.height);
	}
    }



    /**
     * The fixed components will have their preferred size. The
     * remaining space is divided between the rubber components in the
     * proportion of their respective preferred size.
     */
    private Rectangle[] doLayout0(
            final Container target,
            final int       parallelSpace,
            final int       orthogonalSpace,
            final int       prefFixedSpace,
            final int       prefRubberSpace,
            final int       fillerCount) {

	final int   childrenCount        = target.getComponentCount();
	final int   availableRubberSpace = parallelSpace - prefFixedSpace;
	final float rubberFactor;
	final int   fillerSpace;

	if ( (fillerCount==0) || (availableRubberSpace<=prefRubberSpace) ) {
	    rubberFactor = (float)availableRubberSpace / (float)prefRubberSpace;
	    fillerSpace  = 0;
	} else {
	    rubberFactor = 1.0F;
	    fillerSpace  = (availableRubberSpace-prefRubberSpace) / fillerCount;
	}

	final Rectangle[] bounds = new Rectangle[childrenCount];
	int offset = 0;

	for ( int i=0; i<childrenCount; ++i ) {
	    final Component    component  = target.getComponent(i);
	    final BoxPanelFill constraint = getConstraint(component);
	    final int          space;

	    if ( constraint == BoxPanelFill.NO ) {
		space = getPreferredSpace(component);
	    } else if ( constraint == BoxPanelFill.YES ) {
		space = (int)(rubberFactor * getPreferredSpace(component));
	    } else {
		space = fillerSpace;
	    }

	    if ( _orientation == BoxPanelOrientation.X_AXIS ) {
		bounds[i] = new Rectangle(offset, 0, space, orthogonalSpace);
	    } else {
		bounds[i] = new Rectangle(0, offset, orthogonalSpace, space);
	    }

	    offset += space;
	}

	return bounds;
    }



    /**
     * The rubber components will have their minimum size. The
     * remaining space is divided between the fixed components in the
     * proportion of their respective preferred size.
     */
    private Rectangle[] doLayout1(
            final Container target,
            final int       parallelSpace,
            final int       orthogonalSpace,
            final int       prefFixedSpace,
            final int       minRubberSpace) {

	final int   childrenCount       = target.getComponentCount();
	final int   availableFixedSpace = parallelSpace - minRubberSpace;
	final float fixedFactor         = (float)availableFixedSpace / (float)prefFixedSpace;

	final Rectangle[] bounds = new Rectangle[childrenCount];
	int offset = 0;

	for ( int i=0; i<childrenCount; ++i ) {
	    final Component    component  = target.getComponent(i);
	    final BoxPanelFill constraint = getConstraint(component);
	    final int          space;

	    if ( constraint == BoxPanelFill.NO ) {
		space = (int)(fixedFactor * getPreferredSpace(component));
	    } else if ( constraint == BoxPanelFill.YES ) {
		space = getMinimumSpace(component);
	    } else {
		space = 0;
	    }

	    if ( _orientation == BoxPanelOrientation.X_AXIS ) {
		bounds[i] = new Rectangle(offset, 0, space, orthogonalSpace);
	    } else {
		bounds[i] = new Rectangle(0, offset, orthogonalSpace, space);
	    }

	    offset += space;
	}

	return bounds;
    }


    /**
     * The fixed components will have their minimum size. The
     * remaining space is divided between the rubber components in the
     * proportion of their respective preferred size.
     */
    private Rectangle[] doLayout2(
            final Container target,
            final int       parallelSpace,
            final int       orthogonalSpace,
            final int       minFixedSpace,
            final int       prefRubberSpace) {

	final int   childrenCount        = target.getComponentCount();
	final int   availableRubberSpace = parallelSpace - minFixedSpace;
	final float rubberFactor         = (float)availableRubberSpace / (float)prefRubberSpace;

	final Rectangle[] bounds = new Rectangle[childrenCount];
	int offset = 0;

	for ( int i=0; i<childrenCount; ++i ) {
	    final Component    component  = target.getComponent(i);
	    final BoxPanelFill constraint = getConstraint(component);
	    final int          space;

	    if ( constraint == BoxPanelFill.NO ) {
		space = getMinimumSpace(component);
	    } else if ( constraint == BoxPanelFill.YES ) {
		space = (int)(rubberFactor * getPreferredSpace(component));
	    } else {
		space = 0;
	    }

	    if ( _orientation == BoxPanelOrientation.X_AXIS ) {
		bounds[i] = new Rectangle(offset, 0, space, orthogonalSpace);
	    } else {
		bounds[i] = new Rectangle(0, offset, orthogonalSpace, space);
	    }

	    offset += space;
	}

	return bounds;
    }


    /**
     * No space is allocated for the rubber components. The available
     * space is divided between the fixed components in the proportion
     * of their respective preferred size.
     */
    private Rectangle[] doLayout3(
            final Container target,
            final int       parallelSpace,
            final int       orthogonalSpace,
            final int       prefFixedSpace) {

	final int   childrenCount       = target.getComponentCount();
	final int   availableFixedSpace = parallelSpace;
	final float fixedFactor         = (float)availableFixedSpace / (float)prefFixedSpace;

	final Rectangle[] bounds = new Rectangle[childrenCount];
	int offset = 0;

	for ( int i=0; i<childrenCount; ++i ) {
	    final Component    component  = target.getComponent(i);
	    final BoxPanelFill constraint = getConstraint(component);
	    final int          space;

	    if ( constraint == BoxPanelFill.NO ) {
		space = (int)(fixedFactor * getPreferredSpace(component));
	    } else {
		space = 0;
	    }

	    if ( _orientation == BoxPanelOrientation.X_AXIS ) {
		bounds[i] = new Rectangle(offset, 0, space, orthogonalSpace);
	    } else {
		bounds[i] = new Rectangle(0, offset, orthogonalSpace, space);
	    }

	    offset += space;
	}

	return bounds;
    }


    /**
     *
     */
    private int getMinimumSpace(final Component component) {

	final Dimension size = component.getMinimumSize();

        return getSpace(size);
    }


    /**
     *
     */
    private int getPreferredSpace(final Component component) {

	final Dimension size = component.getPreferredSize();

        return getSpace(size);
    }


    /**
     *
     */
    private int getSpace(final Dimension size) {

        return (_orientation==BoxPanelOrientation.X_AXIS) ? size.width : size.height;
    }


    /**
     *
     */
    private BoxPanelFill getConstraint(final Component component) {

        return _constraintsByComponent.get(component);
    }


    /**
     *
     */
    @Override
    public Dimension minimumLayoutSize(final Container target) {

	checkContainer(target);
	checkRequests();

        return sizeWithInsets(target, _xTotal._minimum, _yTotal._minimum);
    }


    /**
     *
     */
    @Override
    public Dimension preferredLayoutSize(final Container target) {

	checkContainer(target);
	checkRequests();

        return sizeWithInsets(target, _xTotal._preferred, _yTotal._preferred);
    }


    /**
     *
     */
    public Dimension maximumLayoutSize(final Container target) {

	checkContainer(target);
	checkRequests();

        return sizeWithInsets(target, _xTotal._maximum, _yTotal._maximum);
    }


    /**
     *
     */
    private Dimension sizeWithInsets(
            final Container target,
            final int       xSize,
            final int       ySize) {

	final Insets insets  = target.getInsets();
	final long   widthL  = (long)xSize + (long)insets.left + (long)insets.right;
	final long   heightL = (long)ySize + (long)insets.top + (long)insets.bottom;
	final int    width   = (int)Math.min(widthL, Integer.MAX_VALUE);
	final int    height  = (int)Math.min(heightL, Integer.MAX_VALUE);

        return new Dimension(width, height);
    }


    /**
     *
     */
    @Override
    public void removeLayoutComponent(final Component component) {

	_constraintsByComponent.remove(component);
    }


    /**
     *
     */
    private void checkContainer(final Container target) {

	if ( target != _target ) {
	    throw new IllegalArgumentException("BoxPanelLayout can not be shared");
	}
    }


    /**
     *
     */
    private void checkRequests() {

	if ( (_xTotal!=null) && (_yTotal!=null) ) {
	    // Values have alreay been calculated. Nothing to be done.
	    return;
	}

	final SizeRequirement xTotal = new SizeRequirement();
	final SizeRequirement yTotal = new SizeRequirement();

	for ( int i=0, count=_target.getComponentCount(); i<count; ++i ) {
	    final Component component = _target.getComponent(i);

	    if ( !component.isVisible() ) {
		continue;
	    }

	    final Dimension min  = component.getMinimumSize();
	    final Dimension pref = component.getPreferredSize();
	    final Dimension max  = component.getMaximumSize();

	    if ( _orientation == BoxPanelOrientation.X_AXIS ) {
		xTotal.add(min.width, pref.width, max.width);
		yTotal.update(min.height, pref.height, max.height);
	    } else {
		xTotal.update(min.width, pref.width, max.width);
		yTotal.add(min.height, pref.height, max.height);
	    }
	}

	_xTotal = xTotal;
	_yTotal = yTotal;
    }


    /**
     *
     */
    private static final class SizeRequirement {


	public int _minimum   = 0;
	public int _preferred = 0;
	public int _maximum   = 0;


        /**
         *
         */
	public void add(
                final int min,
                final int pref,
                final int max) {

	    long minL  = (long)_minimum + (long)min;
	    long prefL = (long)_preferred + (long)pref;
	    long maxL  = (long)_maximum + (long)max;

	    _minimum   = (int)Math.min(minL, Integer.MAX_VALUE);
	    _preferred = (int)Math.min(prefL, Integer.MAX_VALUE);
	    _maximum   = (int)Math.min(maxL, Integer.MAX_VALUE);
	}


        /**
         *
         */
	public void update(
                final int min,
                final int pref,
                final int max) {

	    _minimum   = Math.max(_minimum, min);
	    _preferred = Math.max(_preferred, pref);
	    _maximum   = Math.max(_maximum, max);
	}

    }

}
