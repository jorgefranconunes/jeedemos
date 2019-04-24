/**
 *
 */

import MessageFormat from "varmateo/text/MessageFormat"


// Format of logging messages.
// 0 - Time.
// 1 - Module name.
// 2 - Log level,
// 3 - Message.
const FORMAT = "{0} {1} {2} {3}";

// Log levels.
const LEVEL_DEBUG = "DEBUG";
const LEVEL_INFO = "INFO";
const LEVEL_WARN = "WARNING"

const ZEROS = "00000000";


/**
 *
 */
export default class Logger {




    /**
     *
     */
    constructor(moduleName) {

        this._moduleName = moduleName;
    }


    /**
     *
     */
    static createFor(moduleName) {

        return new Logger(moduleName);
    }


    /**
     *
     */
    debug(msg, ...args) {

        this._logMessage({
            logLevel    : LEVEL_DEBUG,
            message     : msg,
            messageArgs : args,
        });
    }


    /**
     *
     */
    info(msg, ...args) {

        this._logMessage({
            logLevel    : LEVEL_INFO,
            message     : msg,
            messageArgs : args,
        });
    }


    /**
     *
     */
    warn(msg, ...args) {

        this._logMessage({
            logLevel    : LEVEL_WARN,
            message     : msg,
            messageArgs : args,
        });
    }


    /**
     *
     */
    infoObj(object) {

        this._logObject(LEVEL_INFO, object);
    }


    _logMessage(msgData) {

        const output = window.console;

        if ( output ) {
            const time         = new Date();
            const logLevel     = msgData.logLevel;
            const msg          = msgData.message;
            const msgArgs      = msgData.messageArgs;
            const moduleName   = this._moduleName;
            const formattedMsg = MessageFormat.format(msg, msgArgs);
            const timeStr      = _formatTime(time);
            const logMsg       =
                  MessageFormat.format(FORMAT, timeStr, moduleName, logLevel, formattedMsg);

            output.log(logMsg);
        }
    }


    _logObject(logLevel, object) {

        Object.keys(object)
            .sort()
            .forEach(fieldName  => {
                const fieldValue = object[fieldName];
                this._logMessage({
                    logLevel    : logLevel,
                    message     : "\t{0} : {1}",
                    messageArgs : [ fieldName, fieldValue ],
                });
            });
    }

}


function _formatTime(time) {

    const hours = time.getHours();
    const minutes = time.getMinutes();
    const seconds = time.getSeconds();
    const millis = time.getMilliseconds();

    const hoursStr = _padWithZeros(hours, 2);
    const minutesStr = _padWithZeros(minutes, 2);
    const secondsStr = _padWithZeros(seconds, 2);
    const millisStr = _padWithZeros(millis, 3);

    return hoursStr + ":" + minutesStr + ":" + secondsStr + "." + millisStr;
}


function _padWithZeros(value, width) {

    const approximateResult = value.toString();

    return (approximateResult.length < width)
        ? ZEROS.substr(0, width-approximateResult.length) + approximateResult
        : approximateResult;
}
