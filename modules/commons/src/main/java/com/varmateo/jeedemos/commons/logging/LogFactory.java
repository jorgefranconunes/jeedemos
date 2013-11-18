/**************************************************************************
 *
 * Copyright (c) 2013 Jorge Nunes, All Rights Reserved.
 *
 **************************************************************************/

package com.varmateo.jeedemos.commons.logging;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;

import com.varmateo.jeedemos.commons.logging.LoggerLog;





/***************************************************************************
 *
 * Provides utility methods for creating <code>Log</code> instances.
 *
 ***************************************************************************/

public final class LogFactory
    extends Object {





    private static boolean _isFirstTime = true;





/***************************************************************************
 *
 * No instances of this class are to be created.
 *
 ***************************************************************************/

    private LogFactory() {

        // Nothing to do.
    }





/***************************************************************************
 *
 *
 *
 ***************************************************************************/

    private static void initializeIfRequired() {

        if ( _isFirstTime ) {
            _isFirstTime = false;

            try {
                LogManager.getLogManager().readConfiguration();
            } catch ( java.io.IOException e ) {
                // Never mind...
            }
        }
    }





/***************************************************************************
 *
 * Creates a <code>Log</code> that will use as output the given
 * <code>Logger</code> instance.
 *
 * <p>If the given logger is null then no logging will take place.</p>
 *
 * @param logger Will be used as output.
 *
 * @return A newly created <code>Log</code>.
 *
 ***************************************************************************/

    public static Log create(final Logger logger) {

        initializeIfRequired();

        LoggerLog result = new LoggerLog(logger);

        return result;
    }





/***************************************************************************
 *
 * Creates a <code>Log</code> assigning it as subsystem name the class
 * name of the given object.
 *
 * @param obj Object whose class name will be used as subsystem name
 * for the logger.
 *
 * @return A newly created <code>Log</code> with an underlying Java
 * <code>Logger</code> using the given object class name as sybsystem
 * name.
 *
 ***************************************************************************/

    public static Log createFor(final Object obj) {

        initializeIfRequired();

        String className  = obj.getClass().getName();
        Logger javaLogger = Logger.getLogger(className);
        Log    logger     = create(javaLogger);

        return logger;
    }





/***************************************************************************
 *
 * Creates a <code>Log</code> assigning it as subsystem name the name
 * of the given class.
 *
 * @param klass The class whose name will be used as subsystem name
 * for the logger.
 *
 * @return A newly created <code>Log</code> with an underlying Java
 * <code>Logger</code> using the name of the given class as sybsystem
 * name.
 *
 ***************************************************************************/

    public static Log createFor(final Class<?> klass) {

        initializeIfRequired();

        String className  = klass.getName();
        Logger javaLogger = Logger.getLogger(className);
        Log    logger     = create(javaLogger);

        return logger;
    }


}





/***************************************************************************
 *
 *
 *
 ***************************************************************************/

