/**
 * Logs a message when DOM is ready to be manipulated.
 */

import Dom from "varmateo/dom/Dom"
import Logger from "varmateo/logging/Logger";


const log = Logger.createFor("Demo03");

log.info("Starting...");

Dom.whenReady(() => log.info("Document is now ready!"));
