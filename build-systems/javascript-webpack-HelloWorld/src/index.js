/**
 *
 */

import Logger from "varmateo/logging/Logger";


(function main() {
    const log = Logger.createFor("Demo");

    log.info("Hello, {0}!", "world");
})();
