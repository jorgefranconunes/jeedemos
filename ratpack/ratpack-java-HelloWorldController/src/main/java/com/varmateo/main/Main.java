/**
 *
 */
package com.varmateo.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class Main {


    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);


    public static void main(String[] args) {

        App app = new App();

        app.start();
        LOGGER.info("Application started...");
    }

}

