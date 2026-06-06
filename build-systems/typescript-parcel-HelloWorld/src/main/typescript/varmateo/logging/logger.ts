import { format } from "../text/message-format"


// Format of logging messages.
// 0 - Time.
// 1 - Module name.
// 2 - Log level,
// 3 - Message.
const FORMAT: string = "{0} {1} {2} {3}";

// Log levels.
enum LogLevel {
    DEBUG = "DEBUG",
    INFO = "INFO",
    WARN = "WARNING",
}

const ZEROS: string = "00000000";

interface MessageDetail {
    logLevel: LogLevel,
    message: string,
    messageArgs: unknown[],
}

class Logger {
    private readonly _moduleName: string

    constructor(moduleName: string) {
        this._moduleName = moduleName;
    }

    static createFor(moduleName: string): Logger {
        return new Logger(moduleName);
    }

    debug(
	msg: string,
	...args: unknown[]
    ): void {
        this._logMessage({
            logLevel    : LogLevel.DEBUG,
            message     : msg,
            messageArgs : args,
        });
    }

    info(
	msg: string,
	...args: unknown[]
    ): void {
        this._logMessage({
            logLevel    : LogLevel.INFO,
            message     : msg,
            messageArgs : args,
        });
    }

    warn(
	msg: string,
	...args: unknown[]
    ): void {
        this._logMessage({
            logLevel    : LogLevel.WARN,
            message     : msg,
            messageArgs : args,
        });
    }

    infoObj(obj: object): void {
        this._logObject(LogLevel.INFO, obj);
    }

    private _logMessage(msgData: MessageDetail): void {
        const output = window.console;

        if ( output ) {
            const time         = new Date();
            const logLevel     = msgData.logLevel;
            const msg          = msgData.message;
            const msgArgs      = msgData.messageArgs;
            const moduleName   = this._moduleName;
            const formattedMsg = format(msg, ...msgArgs);
            const timeStr      = _formatTime(time);
            const logMsg       = format(FORMAT, timeStr, moduleName, logLevel, formattedMsg);

            output.log(logMsg);
        }
    }

    private _logObject(
	logLevel: LogLevel,
	obj: object,
    ) {
        Object.keys(obj)
            .sort()
            .forEach((fieldName: string)  => {
                const fieldValue: unknown = obj[fieldName as keyof object];
                this._logMessage({
                    logLevel    : logLevel,
                    message     : "\t{0} : {1}",
                    messageArgs : [ fieldName, fieldValue ],
                });
            });
    }

}

function _formatTime(time: Date): string {
    const hours = time.getHours();
    const minutes = time.getMinutes();
    const seconds = time.getSeconds();
    const millis = time.getMilliseconds();

    const hoursStr = _padWithZeros(hours, 2);
    const minutesStr = _padWithZeros(minutes, 2);
    const secondsStr = _padWithZeros(seconds, 2);
    const millisStr = _padWithZeros(millis, 3);

    return `${hoursStr}:${minutesStr}:${secondsStr}.${millisStr}`;
}

function _padWithZeros(
    value: number,
    width:number,
): string {
    const approximateResult: string = value.toString();

    return (approximateResult.length < width)
        ? ZEROS.substr(0, width - approximateResult.length) + approximateResult
        : approximateResult;
}

export {
    Logger,
}
