/**************************************************************************
 *
 * Copyright (c) 2013 Jorge Nunes, All Rights Reserved.
 *
 **************************************************************************/

package com.varmateo.jeedemos.commons.servlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.varmateo.jeedemos.commons.logging.ServletLog;
import com.varmateo.jeedemos.commons.logging.ServletLogFactory;





/**************************************************************************
 *
 * A <code>ServletContextListener</code> that just logs information
 * regarding the events.
 *
 **************************************************************************/

public final class ServletContextListenerWithLog
    extends Object
    implements ServletContextListener {





    private ServletLog _log = null;





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

    public ServletContextListenerWithLog() {

        _log = ServletLogFactory.createFor(this);

        _log.info("Listener {0} created.",
                  ServletContextListenerWithLog.class.getName());
    }





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

    @Override
    public void contextInitialized(final ServletContextEvent event) {

        ServletContext context = event.getServletContext();

        _log.info("Context \"{0}\" is initialized.",
                  context.getServletContextName());
        _log.log(context);
    }





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

    @Override
    public void contextDestroyed(final ServletContextEvent event) {

        ServletContext context = event.getServletContext();

        _log.info("Context \"{0}\" is destroyed.",
                  context.getServletContextName());
    }

}





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

