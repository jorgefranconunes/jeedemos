/**
 * 
 */

import Dom from "varmateo/dom/Dom"
import El from "varmateo/dom/El"
import Logger from "varmateo/logging/Logger";


const log = Logger.createFor("Demo04");


/**
 *
 */
function main() {

    log.info("Starting...");

    const elementId = "demo04Title";

    Dom.whenReady(() => {
        log.info("Document is now ready.");
        showMessage(elementId, "Hello, world!!!");
    });
}


/**
 *
 */
function showMessage(elementId, message) {

    const element = El.withId(elementId);

    element.textContent = message;
}


/**
 * Script entry point;
 */
main();
