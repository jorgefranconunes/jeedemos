/**
 *
 */


/**
 * Utility functions for formatting text.
 */
export default class MessageFormat {


    /**
     * First argument is the format string. Remaining arguments are
     * formating arguments.
     */
    static format(fmt, ...fmtArgs) {

        const getter  = (...args) => fmtArgs[args[1]];

        return fmt.replace(/\{(\d+)\}/g, getter);
    }

}
