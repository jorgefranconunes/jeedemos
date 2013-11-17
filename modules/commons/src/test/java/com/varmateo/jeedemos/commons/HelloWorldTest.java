/**************************************************************************
 *
 * Copyright (c) 2013 Jorge Nunes, All Rights Reserved.
 *
 **************************************************************************/

package com.varmateo.jeedemos.commons;

import static org.junit.Assert.*;
import org.junit.Test;





/***************************************************************************
 *
 *
 *
 ***************************************************************************/

public final class HelloWorldTest
    extends Object {





    private static final String PROP_FAIL_TEST =
        HelloWorldTest.class.getName() + ".failTest";





/***************************************************************************
 *
 *
 *
 ***************************************************************************/

    @Test
    public void failIfRequested() {

        String propFailTest = System.getProperty(PROP_FAIL_TEST);

        if ( "true".equals(propFailTest) ) {
            fail("Failed test as requested");
        }
    }


}





/***************************************************************************
 *
 *
 *
 ***************************************************************************/

