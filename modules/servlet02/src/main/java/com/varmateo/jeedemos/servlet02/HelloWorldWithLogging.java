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

import com.varmateo.jeedemos.commons.logging.Log;
import com.varmateo.jeedemos.commons.logging.LogFactory;





/**************************************************************************
 *
 * A simple servlet that produces log messages for some life-cycle
 * events and for all requests.
 *
 **************************************************************************/

public final class HelloWorldWithLogging
    extends HttpServlet {





    private Log _log = null;





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

    public HelloWorldWithLogging() {

        _log = LogFactory.createFor(this);

        _log.info("Servlet instance created.");
    }





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

    @Override
    public void init(final ServletConfig config) {

        String servletName = config.getServletName();

        _log.info("Servlet \"{0}\" is being initialized.", servletName);
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

        _log.info("Servicing \"{0}\" for \"{1}\"",
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

