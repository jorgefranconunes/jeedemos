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

        const getter  = (...args) => {
            const index = args[1];
            return (index < fmtArgs.length) ? fmtArgs[index] : `{${index}}`;
        }

        return fmt.replace(/\{(\d+)\}/g, getter);
    }

}
