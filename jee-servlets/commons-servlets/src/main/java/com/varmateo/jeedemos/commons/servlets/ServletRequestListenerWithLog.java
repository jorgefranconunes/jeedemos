/**************************************************************************
 *
 * Copyright (c) 2013 Jorge Nunes, All Rights Reserved.
 *
 **************************************************************************/

package com.varmateo.jeedemos.commons.servlets;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

import com.varmateo.jeedemos.commons.logging.ServletLog;
import com.varmateo.jeedemos.commons.logging.ServletLogFactory;





/**************************************************************************
 *
 * A <code>ServletRequestListener</code> that just logs information
 * regarding the events.
 *
 **************************************************************************/

public final class ServletRequestListenerWithLog
    extends Object
    implements ServletRequestListener {





    private static final String ATTR_START_TIME =
        ServletRequestListenerWithLog.class.getName()+".startTimeNanos";

    private ServletLog _log = null;





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

    public ServletRequestListenerWithLog() {

        _log = ServletLogFactory.createFor(this);

        _log.info("Listener {0} created.",
                  ServletRequestListenerWithLog.class.getName());
    }





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

    @Override
    public void requestInitialized(final ServletRequestEvent event) {

        ServletRequest request = event.getServletRequest();

        if ( request instanceof HttpServletRequest ) {
            HttpServletRequest httpRequest = (HttpServletRequest)request;

            _log.info("Received \"{0}\" request ({1}).",
                      httpRequest.getMethod(),
                      request.getProtocol());
            _log.log(httpRequest);
        } else {
            _log.info("Received \"{0}\" request.",
                      request.getProtocol());
        }

        long startTime = System.nanoTime();
        request.setAttribute(ATTR_START_TIME, Long.valueOf(startTime));
    }





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

    @Override
    public void requestDestroyed(final ServletRequestEvent event) {

        ServletRequest request   = event.getServletRequest();
        long           startTime =
            ((Long)request.getAttribute(ATTR_START_TIME)).longValue();
        long           endTime   = System.nanoTime();
        long           delay     = endTime - startTime;

        if ( request instanceof HttpServletRequest ) {
            HttpServletRequest httpRequest = (HttpServletRequest)request;

            _log.info("Done with \"{0}\" request ({1}). Delay: {1} ns",
                      httpRequest.getMethod(),
                      request.getProtocol(),
                      Long.valueOf(delay));
        } else {
            _log.info("Done with \"{0}\" request. Delay: {1} ns",
                      request.getProtocol(),
                      Long.valueOf(delay));
        }
    }

}





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

