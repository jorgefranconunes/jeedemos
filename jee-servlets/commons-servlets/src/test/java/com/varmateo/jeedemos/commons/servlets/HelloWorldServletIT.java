/**************************************************************************
 *
 * Copyright (c) 2013 Jorge Nunes, All Rights Reserved.
 *
 **************************************************************************/

package com.varmateo.jeedemos.commons.servlets;

import java.io.IOException;

import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.HttpClient;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.varmateo.jeedemos.commons.servlets.HelloWorldServlet;





/**************************************************************************
 *
 * Intgration tests for HelloWorldServlet.
 *
 **************************************************************************/

public final class HelloWorldServletIT
    extends Object {





    private static final String ROOT_URI = "/HelloWorld";

    private static int    _port        = 0;
    private static Server _jettyServer = null;





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

    @BeforeClass
    public static void setup()
        throws Exception {

        HelloWorldServlet servlet = new HelloWorldServlet();
        ServletHolder servletHolder = new ServletHolder("HelloWorld", servlet);

        ServletHandler servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(servletHolder, ROOT_URI);

        // Create the server with a port automatically selected.
        Server jettyServer = new Server(0);
        jettyServer.setHandler(servletHandler);

        jettyServer.start();

        _jettyServer = jettyServer;
        _port        = ((ServerConnector)jettyServer.getConnectors()[0]).getLocalPort();
    }





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

    @AfterClass
    public static void teardown()
        throws Exception {

        if ( _jettyServer != null ) {
            _jettyServer.stop();
            _jettyServer = null;
        }
    }





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

    private String buildUrl(final String uri) {

        StringBuilder buffer = new StringBuilder();

        buffer.append("http://localhost:");
        buffer.append(_port);

        if ( uri.startsWith("/") ) {
            buffer.append(uri);
        } else {
            buffer.append("/").append(uri);
        }

        String result = buffer.toString();

        return result;
    }





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

    @Test
    public void checkString()
        throws Exception {

        HttpClient client = new HttpClient();
        client.start();

        String          url      = buildUrl(ROOT_URI);
        ContentResponse response = client.GET(url);
        String          result   = response.getContentAsString();

        client.stop();

        assertEquals("Hello, world!", result);
    }


}





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

