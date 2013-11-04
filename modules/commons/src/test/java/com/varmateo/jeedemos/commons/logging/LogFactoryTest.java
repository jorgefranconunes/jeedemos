/**************************************************************************
 *
 * Copyright (c) 2013 Jorge Nunes, All Rights Reserved.
 *
 **************************************************************************/

package com.varmateo.jeedemos.commons.logging;

import org.junit.Test;

import com.varmateo.jeedemos.commons.logging.Log;
import com.varmateo.jeedemos.commons.logging.LogFactory;





/***************************************************************************
 *
 *
 *
 ***************************************************************************/

public final class LogFactoryTest
    extends Object {





/***************************************************************************
 *
 *
 *
 ***************************************************************************/

    @Test
    public void empty() {

        Log log = LogFactory.createFor(this);

        log.debug("Just testing...");
    }


}





/***************************************************************************
 *
 *
 *
 ***************************************************************************/

