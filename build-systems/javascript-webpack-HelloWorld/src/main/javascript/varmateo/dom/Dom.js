/**
 *
 */


/**
 * Utility functions for manipulating the DOM.
 */
export default class Dom {


    /**
     *
     */
    static whenReady(onDocumentReady) {

        const isReady = document.attachEvent
              ? (document.readyState === "complete")
              : (document.readyState !== "loading");

        if ( isReady ) {
            onDocumentReady();
        } else {
            document.addEventListener('DOMContentLoaded', onDocumentReady);
        }
    }

}
