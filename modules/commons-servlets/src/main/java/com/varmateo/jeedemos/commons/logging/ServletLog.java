/**************************************************************************
 *
 * Copyright (c) 2013 Jorge Nunes, All Rights Reserved.
 *
 **************************************************************************/

package com.varmateo.jeedemos.commons.logging;

import com.varmateo.jeedemos.commons.logging.Log;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;





/***************************************************************************
 *
 * A simple logger with additional methods for logging servlet
 * specific information.
 *
 ***************************************************************************/

public interface ServletLog
    extends Log {





/***************************************************************************
 *
 * Logs information about the given <code>ServletConfig</code>
 *
 * @param config The data to be logged.
 *
 ***************************************************************************/

    void log(ServletConfig config);





/***************************************************************************
 *
 * Logs information about the given <code>HttpServletRequest</code>.
 *
 * @param request The data to be logged.
 *
 ***************************************************************************/

    void log(HttpServletRequest request);


}





/***************************************************************************
 *
 *
 *
 ***************************************************************************/

