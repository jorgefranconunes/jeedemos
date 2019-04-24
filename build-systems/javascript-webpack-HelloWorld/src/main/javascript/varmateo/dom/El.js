/**
 *
 */


/**
 * Utility functions for manipulating DOM elements.
 */
export default class El {


    /**
     *
     */
    static withId(elementId) {

        const selector = `#${elementId}`;
        const element = document.querySelector(selector);

        if ( element == null ) {
            const msg = `No element with ID "${elementId}"`;
            throw msg;
        }

        return element;
    }

}
