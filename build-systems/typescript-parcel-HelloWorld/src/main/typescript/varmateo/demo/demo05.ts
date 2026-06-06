/**
 * Updates the text content of an element.
 */

import * as dom from "../dom/dom";
import * as elements from "../dom/elements";
import { Logger } from "../logging/logger";

const logger: Logger = Logger.createFor("Demo05");

const SPAN_ID: string = "demo05Span";

function main(): void {
    logger.info("Starting...");
    dom.whenReady(startup);
}

function startup(): void {
    logger.info("Document is now ready.");
    updateElementContent(SPAN_ID, "Done!");
    logger.info("Element with ID '{0}' has been updated.", SPAN_ID);
}

function updateElementContent(
    elementId: string,
    message: string,
): void {
    const element: Element = elements.withId(elementId);
    element.textContent = message;
}

main();
