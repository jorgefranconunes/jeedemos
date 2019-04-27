/**
 *
 */

import MessageFormat from "varmateo/text/MessageFormat"

//const assert = require("assert");
import assert from "assert"


describe("MessageFormat", function() {

    it("Should return the same string", function() {
        const input = "Hello, world";

        const actualResult = MessageFormat.format(input);
        const expectedResult = input;

        assert.equal(actualResult, expectedResult);
    });

    it("Should replace format placeholder", function() {
        const fmt = "Hello, {0}!";
        const fmtArg = "world";

        const actualResult = MessageFormat.format(fmt, fmtArg);
        const expectedResult = "Hello, world!";

        assert.equal(actualResult, expectedResult);
    })
});
