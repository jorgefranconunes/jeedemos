/**************************************************************************
 *
 * Copyright (c) 2013 Jorge Nunes, All Rights Reserved.
 *
 **************************************************************************/

package com.varmateo.jeedemos.servlet01;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

public final class HelloWorld
    extends HttpServlet {





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

    public void doGet(final HttpServletRequest  request,
                      final HttpServletResponse response)
        throws ServletException,
               IOException {

        response.setContentType("text/plain");

        PrintWriter output = response.getWriter();
        output.println("Hello, world!");
    }
}





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

