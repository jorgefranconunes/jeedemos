/**************************************************************************
 *
 * Copyright (c) 2013 Jorge Nunes, All Rights Reserved.
 *
 **************************************************************************/

package com.varmateo.jeedemos.commons.logging;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.varmateo.jeedemos.commons.logging.Log;
import com.varmateo.jeedemos.commons.logging.LogWrapper;





/***************************************************************************
 *
 * A <code>ServletLog</code> implementation that delegates all logging
 * to a wrapped <code>Log</code>.
 *
 ***************************************************************************/

class ServletLogImpl
    extends LogWrapper
    implements ServletLog {





/***************************************************************************
 *
 *
 *
 ***************************************************************************/

    public ServletLogImpl(final Log logger) {

        super(logger);
    }





/***************************************************************************
 *
 * {@inheritDoc}
 *
 ***************************************************************************/

    @Override
    public void log(final ServletContext context) {

        logServletContext(this, context);
    }





/***************************************************************************
 *
 * {@inheritDoc}
 *
 ***************************************************************************/

    @Override
    public void log(final ServletConfig config) {

        logServletConfig(this, config);
    }





/***************************************************************************
 *
 * {@inheritDoc}
 *
 ***************************************************************************/

    @Override
    public void log(final HttpServletRequest request) {

        logServletRequest(this, request);
    }





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

    private void logServletContext(final Log            log,
                                   final ServletContext context) {

        log.info("Context name : {0}", context.getServletContextName());
        log.info("Context path : {0}", context.getContextPath());
        log.info("Spec version : {0}.{1} - {2}.{3}",
                 context.getMajorVersion(),
                 context.getMinorVersion(),
                 context.getEffectiveMajorVersion(),
                 context.getEffectiveMinorVersion());
        log.info("Server info  : {0}", context.getServerInfo());

        ParamSet paramSet =
            new ParamSet() {
                public final Enumeration<String> keys() {
                    return context.getInitParameterNames();
                }
                public final String getValue(final String key) {
                    return context.getInitParameter(key);
                }
            };

        log.info("Init parameters : {0}",
                    String.valueOf(paramSet.size()));
        logParamSet(log, paramSet);
    }





/***************************************************************************
 *
 * Logs information about the given <code>ServletConfig</code>
 *
 * @param config The data to be logged.
 *
 ***************************************************************************/

    private void logServletConfig(final Log           log,
                                  final ServletConfig config) {

        log.info("Servlet name    : {0}", config.getServletName());

        ParamSet paramSet =
            new ParamSet() {
                public final Enumeration<String> keys() {
                    return config.getInitParameterNames();
                }
                public final String getValue(final String key) {
                    return config.getInitParameter(key);
                }
            };

        log.info("Init parameters : {0}",
                    String.valueOf(paramSet.size()));
        logParamSet(log, paramSet);
    }





/***************************************************************************
 *
 * Logs information about the given <code>HttpServletRequest</code>.
 *
 * @param request The data to be logged.
 *
 ***************************************************************************/

    private void logServletRequest(final Log                log,
                                   final HttpServletRequest request) {

        log.info("Method       : {0}", request.getMethod());
        log.info("URI          : {0}", request.getRequestURI());
        log.info("Protocol     : {0}", request.getProtocol());

        logRequestHeaders(log, request);
        logRequestParameters(log, request);
    }





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

    private void logRequestHeaders(final Log                log,
                                   final HttpServletRequest request) {

        ParamSet headerSet =
            new ParamSet() {
                public final Enumeration<String> keys() {
                    return request.getHeaderNames();
                }
                public final String getValue(final String key) {
                    return request.getHeader(key);
                }
            };

        log.info("HTTP headers : {0}", String.valueOf(headerSet.size()));
        logParamSet(log, headerSet);
    }





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

    private void logRequestParameters(final Log                log,
                                      final HttpServletRequest request) {

        ParamSet paramSet =
            new ParamSet() {
                public final Enumeration<String> keys() {
                    return request.getParameterNames();
                }
                public final String getValue(final String key) {
                    return request.getParameter(key);
                }
            };

        log.info("Parameters   : {0}", String.valueOf(paramSet.size()));
        logParamSet(log, paramSet);
    }





/**************************************************************************
 *
 * 
 *
 **************************************************************************/

    private void logParamSet(final Log      log,
                             final ParamSet paramSet) {

        for ( Param param : paramSet ) {
            log.info("\t{0} : {1}", param.key(), param.value());
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





/***************************************************************************
 *
 *
 *
 ***************************************************************************/

