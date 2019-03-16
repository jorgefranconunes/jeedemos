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
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


/**
 * A container for laying out components in a row or in a column,
 * showing all components with the same size.
 */
public final class SameSizeBoxPanel {


    private static final int DEFAULT_SPACING = 8;
    private static final int DEFAULT_MARGIN  = 16;


    /**
     *
     */
    private SameSizeBoxPanel() {
        // Nothing to do.
    }


    /**
     *
     */
    public static JPanel create(final Consumer<Spec> configurator) {

        final Spec spec = new Spec();

        configurator.accept(spec);

	final BoxPanelOrientation orientation = spec._orientation;
	final int spacing = spec._spacing;
	final int margin  = spec._margin;
        final JPanel box = new JPanel();

	if ( margin != 0 ) {
	    box.setBorder(new EmptyBorder(margin, margin, margin, margin));
	}

        final LayoutManager layoutManager =
                (orientation == BoxPanelOrientation.X_AXIS)
                ? new GridLayout(1, 0, spacing, 0)
                : new GridLayout(0, 1, 0, spacing);

        box.setLayout(layoutManager);

        spec._components.forEach(box::add);

        return box;
    }


    /**
     *
     */
    public static final class Spec {


        private BoxPanelOrientation _orientation = BoxPanelOrientation.X_AXIS;
        private int _spacing = DEFAULT_SPACING;
        private int _margin = DEFAULT_MARGIN;
        private List<Component> _components = new ArrayList<>();


        private Spec() {
            // Nothing to do.
        }


        /**
         *
         */
        public Spec orientation(final BoxPanelOrientation orientation) {

            _orientation = orientation;

            return this;
        }


        /**
         *
         */
        public Spec spacing(final int spacing) {

            _spacing = spacing;

            return this;
        }


        /**
         *
         */
        public Spec margin(final int margin) {

            _margin = margin;

            return this;
        }


        /**
         *
         */
        public Spec component(
                final Component component,
                final Component... moreComponents) {

            _components.add(component);

            for ( final Component otherComponent : moreComponents ) {
                _components.add(otherComponent);
            }

            return this;
        }


        /**
         *
         */
        public Spec components(final Iterable<? extends Component> components) {

            components.forEach(_components::add);

            return this;
        }

    }

}
