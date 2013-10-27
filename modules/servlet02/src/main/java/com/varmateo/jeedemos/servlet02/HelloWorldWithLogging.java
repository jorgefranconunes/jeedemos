/**************************************************************************
 *
 * Copyright (c) 2013 Jorge Nunes, All Rights Reserved.
 *
 **************************************************************************/

package com.varmateo.jeedemos.servlet02;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.varmateo.jeedemos.commons.logging.SimpleLogger;





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

public final class HelloWorldWithLogging
    extends HttpServlet {





    private SimpleLogger _logger = null;





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

    public HelloWorldWithLogging() {

        _logger = SimpleLogger.createFor(this);

        _logger.info("Servlet instance created.");
    }





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

    @Override
    public void init(final ServletConfig config) {

        String servletName = config.getServletName();

        _logger.info("Servlet \"{0}\" is being initialized.", servletName);
    }





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

    @Override
    public void doGet(final HttpServletRequest  request,
                      final HttpServletResponse response)
        throws ServletException,
               IOException {

        response.setContentType("text/plain");

        PrintWriter output = response.getWriter();
        output.println("Hello, world!");
    }





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

    @Override
    public void service(final HttpServletRequest  request,
                        final HttpServletResponse response)
        throws ServletException,
               IOException {

        _logger.info("Servicing \"{0}\" for \"{1}\"",
                     request.getMethod(),
                     request.getRequestURL());

        super.service(request, response);
    }


}





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

