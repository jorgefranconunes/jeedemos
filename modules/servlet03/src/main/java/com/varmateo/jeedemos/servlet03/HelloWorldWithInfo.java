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

import com.varmateo.jeedemos.commons.logging.SimpleLogger;





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

public final class HelloWorldWithInfo
    extends HttpServlet {





    private SimpleLogger _logger = null;





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

    public HelloWorldWithInfo() {

        _logger = SimpleLogger.createFor(this);

        _logger.info("Servlet {0} created.",
                     HelloWorldWithInfo.class.getName());
    }





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

    @Override
    public void init(final ServletConfig config) {

        _logger.info("Servlet \"{0}\" initialized.", config.getServletName());
        logServletConfig(_logger, config);
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





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

    private void logServletConfig(final SimpleLogger  logger,
                                  final ServletConfig config) {

        ServletContext context = config.getServletContext();

        logger.info("Servlet name    : {0}", config.getServletName());
        logger.info("Context path    : {0}", context.getContextPath());

        ParamSet paramSet =
            new ParamSet() {
                public final Enumeration<String> keys() {
                    return config.getInitParameterNames();
                }
                public final String getValue(final String key) {
                    return config.getInitParameter(key);
                }
            };

        logger.info("Init parameters : {0}", String.valueOf(paramSet.size()));
        logParamSet(logger, paramSet);
    }





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

    private void logParamSet(final SimpleLogger logger,
                             final ParamSet     paramSet) {

        for ( Param param : paramSet ) {
            _logger.info("\t{0} : {1}", param.key(), param.value());
        }
    }





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

    private final class Param
        extends Object {

        private String _key   = null;
        private String _value = null;

        public Param(final String key,
                     final String value) {
            _key   = key;
            _value = value;
        }

        public String key() { return _key; }
        public String value() { return _value; }
    }





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

    private abstract class ParamSet
        extends Object
        implements Iterable<Param> {





        private List<Param> _paramList = null;





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

        public ParamSet() {

            // Nothing to do.
        }





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

        public abstract Enumeration<String> keys();





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

        public abstract String getValue(String key);





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

        public Iterator<Param> params() {

            initIfRequired();

            Iterator<Param> result = _paramList.iterator();

            return result;
        }





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

        public int size() {

            initIfRequired();

            int result = _paramList.size();

            return result;
        }





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

        public Iterator<Param> iterator() {

            Iterator<Param> result = params();

            return result;
        }





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

        private void initIfRequired() {

            if ( _paramList == null ) {
                buildParamList();
            }
        }





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

        private void buildParamList() {

            List<Param> paramList = new ArrayList<Param>();

            for ( Enumeration<String> keys = this.keys();
                  keys.hasMoreElements(); ) {
                String key   = keys.nextElement();
                String value = this.getValue(key);
                Param  param = new Param(key, value);
                paramList.add(param);
            }

            _paramList = paramList;
        }


    }


}





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

