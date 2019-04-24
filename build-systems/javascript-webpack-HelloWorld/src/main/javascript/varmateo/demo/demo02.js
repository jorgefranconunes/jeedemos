/**
 * Uses logging to print to the console.
 */

import Logger from "varmateo/logging/Logger";


const log = Logger.createFor("Demo02");

log.info("Hello, {0}!!", "world");
