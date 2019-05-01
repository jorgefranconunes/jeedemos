/**
 * Utility functions for formatting text.
 */


/**
 * First argument is the format string. Remaining arguments are
 * formating arguments.
 */
function format(fmt, ...fmtArgs) {

    const getter  = (...args) => {
        const index = args[1];
        return (index < fmtArgs.length) ? fmtArgs[index] : `{${index}}`;
    }

    return fmt.replace(/\{(\d+)\}/g, getter);
}


export default {
    format,
}
