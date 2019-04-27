/**
 * 
 */

import Dom from "varmateo/dom/Dom";
import El from "varmateo/dom/El";
import Logger from "varmateo/logging/Logger";
import MessageFormat from "varmateo/text/MessageFormat";
import * as R from "ramda";


const log = Logger.createFor("Demo05");

const PERIOD_MILLIS = 100;
const ZEROS = "0000000000000000";


/**
 *
 */
function main() {

    log.info("Starting...");

    const elementId = "demo04Title";

    Dom.whenReady(() => {
        log.info("Document is now ready.");
        startPeriodicAction(PERIOD_MILLIS);
    });
}





/**
 * Int -> ()
 */
function startPeriodicAction(periodMillis) {

    const action = buildUpdateAction();

    setInterval(action, periodMillis);
    action();
}


/**
 *
 */
function buildUpdateAction() {

    const startTime = new Date();
    const dateElement = El.withId("currentTime");
    const durationElement = El.withId("elapsedTime");

    const updateDateElement = updateElement(dateElement, formatDate);
    const updateDurationElement = updateElement(durationElement, formatDuration(startTime));

    return () => onUpdate(updateDateElement, updateDurationElement);
}


/**
 * (Date -> ()) -> (Date -> ()) -> ()
 */
function onUpdate(updateDateElement, updateDurationElement) {

    const currentTime = new Date();

    updateDateElement(currentTime);
    updateDurationElement(currentTime);
}


/**
 * Element -> (x -> String) -> (x -> ())
 */
const updateElement = (element, transformer) => R.pipe(transformer, setText(element));


/**
 * Int -> Int -> String
 */
const padWithZeros = R.curry((desiredWidth, value) => {

    const valueStr = value.toString();
    const actualWidth = valueStr.length;

    if ( actualWidth < desiredWidth ) {
        return ZEROS.substring(0, desiredWidth - actualWidth) + valueStr;
    } else {
        return valueStr;
    }
});


/**
 * Int -> String
 */
const pad2 = padWithZeros(2);


/**
 * Date -> String
 */
const formatDate = (date) => MessageFormat.format(
    "{0}-{1}-{2} {3}:{4}:{5}.{6}",
    date.getFullYear(),
    pad2(date.getMonth() + 1),
    pad2(date.getDate()),
    pad2(date.getHours()),
    pad2(date.getMinutes()),
    pad2(date.getSeconds()),
    toInt(date.getMilliseconds() / 100));


/**
 * Date -> Date -> String
 */
const formatDuration = R.curry((startTime, endTime) => {

    const startTimeMillis = startTime.getTime();
    const endTimeMillis = endTime.getTime();
    const durationMillis = endTimeMillis - startTimeMillis;
    const durationSeconds = toInt(durationMillis / 1000);
    const durationPartialMillis = durationMillis - (durationSeconds * 1000);

    return MessageFormat.format(
        "{0}.{1}", durationSeconds, toInt(durationPartialMillis / 100));
});


/**
 * Float -> Int
 */
const toInt = (floatValue) => ~~floatValue;


/**
 * Element -> String -> ()
 */
const setText = R.curry((element, message) => element.textContent = message);


/**
 * Script entry point;
 */
main();
