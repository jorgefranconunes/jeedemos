/**
 * Generates a log message. It will appear in the console.
 */
import { Logger } from "../logging/logger";

const logger: Logger = Logger.createFor("Demo03");

logger.info("Hello, {0}!", "world");
