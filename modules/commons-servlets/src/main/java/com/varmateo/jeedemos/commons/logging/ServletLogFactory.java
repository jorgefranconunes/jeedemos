/**************************************************************************
 *
 * Copyright (c) 2013 Jorge Nunes, All Rights Reserved.
 *
 **************************************************************************/

package com.varmateo.jeedemos.commons.logging;

import com.varmateo.jeedemos.commons.logging.Log;
import com.varmateo.jeedemos.commons.logging.LogFactory;
import com.varmateo.jeedemos.commons.logging.ServletLog;
import com.varmateo.jeedemos.commons.logging.ServletLogImpl;





/***************************************************************************
 *
 * Provides utility methods for creating <code>ServletLog</code>
 * instances.
 *
 ***************************************************************************/

public final class ServletLogFactory
    extends Object {





/***************************************************************************
 *
 * No instances of this class are to be created.
 *
 ***************************************************************************/

    private ServletLogFactory() {

        // Nothing to do.
    }





/***************************************************************************
 *
 * Creates a <code>ServletLog</code> assigning it as subsystem name
 * the class name of the given object.
 *
 * @param obj Object whose class name will be used as subsystem name
 * for the logger.
 *
 * @return A newly created <code>ServletLog</code>.
 *
 ***************************************************************************/

    public static ServletLog createFor(final Object obj) {

        Log        log        = LogFactory.createFor(obj);
        ServletLog servletLog = new ServletLogImpl(log);

        return servletLog;
    }


}





/***************************************************************************
 *
 *
 *
 ***************************************************************************/

