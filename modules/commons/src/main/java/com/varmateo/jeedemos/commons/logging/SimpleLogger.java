/**************************************************************************
 *
 * Copyright (c) 2013 Jorge Nunes, All Rights Reserved.
 *
 **************************************************************************/

package com.varmateo.jeedemos.commons.logging;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.LogRecord;





/***************************************************************************
 *
 * Provides utility methods for logging. It wraps a Java
 * <code>Logger</code> and provides utility methods going beyond those
 * available from the Java <code>Logger</code>.
 *
 ***************************************************************************/

public final class SimpleLogger
    extends Object {





    /**
     * A <code>SimpleLogger</code> instance that does nothing.
     */
    public static final SimpleLogger NULL_LOGGER = create(null);

    private Logger _logger = null;





/***************************************************************************
 *
 *
 *
 ***************************************************************************/

    private SimpleLogger(final Logger logger) {

        _logger = logger;
    }





/***************************************************************************
 *
 * Creates a <code>SimpleLogger</code> that will use as output the
 * given <code>Logger</code> instance.
 *
 * <p>If the given logger is null then no logging will take place.</p>
 *
 * @param logger Will be used as output.
 *
 * @return A newly created <code>SimpleLogger</code>.
 *
 ***************************************************************************/

    public static SimpleLogger create(final Logger logger) {

        SimpleLogger result = new SimpleLogger(logger);

        return result;
    }





/***************************************************************************
 *
 * Creates a <code>SimpleLogger</code> assigning it as subsystem name
 * the class name of the given object.
 *
 * @param obj Object whose class name will be used as subsystem name
 * for the logger.
 *
 * @return A newly created <code>SimpleLogger</code> with an
 * underlying Java <code>Logger</code> using the given object class
 * name as sybsystem name.
 *
 ***************************************************************************/

    public static SimpleLogger createFor(final Object obj) {

        String       className  = obj.getClass().getName();
        Logger       javaLogger = Logger.getLogger(className);
        SimpleLogger logger     = create(javaLogger);

        return logger;
    }





/***************************************************************************
 *
 * Creates a <code>SimpleLogger</code> assigning it as subsystem name
 * the name of the given class.
 *
 * @param klass The class whose name will be used as subsystem name
 * for the logger.
 *
 * @return A newly created <code>SimpleLogger</code> with an
 * underlying Java <code>Logger</code> using the name of the given
 * class as sybsystem name.
 *
 ***************************************************************************/

    public static SimpleLogger createFor(final Class<?> klass) {

        String       className  = klass.getName();
        Logger       javaLogger = Logger.getLogger(className);
        SimpleLogger logger     = create(javaLogger);

        return logger;
    }





/***************************************************************************
 *
 * Retrieves the underlying Java <code>Logger</code> that is being
 * used as output.
 *
 * @return The Java <code>Logger</code> underlying this
 * <code>SimpleLogger</code>.
 *
 ***************************************************************************/

    public Logger getJavaLogger() {

        return _logger;
    }





/***************************************************************************
 *
 * @return The current log level.
 *
 ***************************************************************************/

    public Level getLevel() {

        Level result =
            (_logger==null) ? Level.OFF : _logger.getLevel();

        return result;
    }





/***************************************************************************
 *
 * Log a WARNING message.
 *
 * @param msg The message to be logged.
 *
 ***************************************************************************/

    public void warning(final String msg) {

        log(Level.WARNING, msg, null);
    }





/***************************************************************************
 *
 * Log a WARNING message.
 *
 * @param msg The log message format.
 *
 * @param fmtArgs Formating arguments used when generating the actual
 * message that is logged.
 *
 ***************************************************************************/

    public void warning(final String    msg,
                        final Object... fmtArgs) {

        log(Level.WARNING, msg, fmtArgs);
    }





/***************************************************************************
 *
 * Log a WARNING message.
 *
 * @param error The exception associated with the log message.
 *
 * @param msg The log message format.
 *
 * @param fmtArgs Formating arguments used when generating the actual
 * message that is logged.
 *
 ***************************************************************************/

    public void warning(final Throwable error,
                        final String    msg,
                        final Object... fmtArgs) {

        log(Level.WARNING, error, msg, fmtArgs);
    }





/***************************************************************************
 *
 * Log a INFO message.
 *
 * @param msg The message to be logged.
 *
 ***************************************************************************/

    public void info(final String msg) {

        log(Level.INFO, msg, null);
    }





/***************************************************************************
 *
 * Log a INFO message.
 *
 * @param msg The log message format.
 *
 * @param fmtArgs Formating arguments used when generating the actual
 * message that is logged.
 *
 ***************************************************************************/

    public void info(final String    msg,
                     final Object... fmtArgs) {

        log(Level.INFO, msg, fmtArgs);
    }





/***************************************************************************
 *
 * Log a DEBUG message.
 *
 * @param msg The message to be logged.
 *
 ***************************************************************************/

    public void debug(final String msg) {

        log(Level.FINE, msg, null);
    }





/***************************************************************************
 *
 * Log a DEBUG message.
 *
 * @param msg The log message format.
 *
 * @param fmtArgs Formating arguments used when generating the actual
 * message that is logged.
 *
 ***************************************************************************/

    public void debug(final String    msg,
                      final Object... fmtArgs) {

        log(Level.FINE, msg, fmtArgs);
    }





/***************************************************************************
 *
 *
 *
 ***************************************************************************/

    private void log(final Level    level,
                     final String   msg,
                     final Object[] fmtArgs) {

        if ( _logger != null ) {
            _logger.log(level, msg, fmtArgs);
        }
    }





/***************************************************************************
 *
 *
 *
 ***************************************************************************/

    private void log(final Level     level,
                     final Throwable error,
                     final String    msg,
                     final Object[]  fmtArgs) {

        if ( _logger != null ) {
            LogRecord logRecord = new LogRecord(level, msg);

            logRecord.setThrown(error);
            logRecord.setParameters(fmtArgs);
            _logger.log(logRecord);
        }
    }


}





/***************************************************************************
 *
 *
 *
 ***************************************************************************/

