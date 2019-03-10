/**************************************************************************
 *
 * Copyright (c) 2019 Jorge Nunes
 *
 **************************************************************************/

package com.varmateo.jeedemos.swing;


/**
 * How a child component inside a {@code BoxPanel} is to be allocated
 * space.
 */
public enum BoxPanelFill {

    /**
     * The component is to ocuppy only its preferred size in the
     * layout direction.
     */
    NO,


    /**
     * The component is to expand to fill all available space in the
     * layout direction.
     */
    YES,


    /**
     * The component is to expand to fill all available space in the
     * layout direction. Other components with {@code
     * BoxPanelFill.YES} constraint will be pushed to their prefered
     * size.
     */
    FILLER,
}
