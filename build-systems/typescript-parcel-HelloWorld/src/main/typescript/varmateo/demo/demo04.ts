/**
 * Logs a message when DOM is ready to be manipulated.
 */
import * as dom from "../dom/dom.js";
import { Logger } from "../logging/logger.js";

const logger: Logger = Logger.createFor("Demo04");

logger.info("Starting...");
dom.whenReady(() => logger.info("Document is now ready!"));
