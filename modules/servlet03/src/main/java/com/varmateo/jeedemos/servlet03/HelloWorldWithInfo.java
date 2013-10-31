/**************************************************************************
 *
 * Copyright (c) 2013 Jorge Nunes, All Rights Reserved.
 *
 **************************************************************************/

package com.varmateo.jeedemos.servlet03;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.varmateo.jeedemos.commons.logging.ServletLog;
import com.varmateo.jeedemos.commons.logging.ServletLogFactory;





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

public final class HelloWorldWithInfo
    extends HttpServlet {





    private ServletLog _log = null;





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

    public HelloWorldWithInfo() {

        _log = ServletLogFactory.createFor(this);

        _log.info("Servlet {0} created.", HelloWorldWithInfo.class.getName());
    }





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

    @Override
    public void init(final ServletConfig config) {

        _log.info("Servlet \"{0}\" initialized.", config.getServletName());
        _log.log(config);
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
        _log.log(request);

        super.service(request, response);
    }


}





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

