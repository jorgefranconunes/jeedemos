/**************************************************************************
 *
 * Copyright (c) 2013 Jorge Nunes, All Rights Reserved.
 *
 **************************************************************************/

package com.varmateo.jeedemos.commons.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;





/**************************************************************************
 *
 * A simple servlet that always responds "Hello, world!" to GET
 * requests.
 *
 **************************************************************************/

public final class HelloWorldServlet
    extends HttpServlet {





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
        output.print("Hello, world!");
    }


}





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

