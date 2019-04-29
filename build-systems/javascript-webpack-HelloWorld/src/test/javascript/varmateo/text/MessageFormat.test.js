/**
 *
 */

import MessageFormat from "varmateo/text/MessageFormat"

import assert from "assert"


describe("MessageFormat", function() {

    it("Should return the same string", function() {
        const input = "Hello, world";

        const actualResult = MessageFormat.format(input);
        const expectedResult = input;

        assert.equal(actualResult, expectedResult);
    });

    it("Should replace format placeholders", function() {
        const fmt = "{0}, {1}!";
        const fmtArgs = [ "Hello", "world" ];

        const actualResult = MessageFormat.format(fmt, ...fmtArgs);
        const expectedResult = "Hello, world!";

        assert.equal(actualResult, expectedResult);
    });

    it("Should keep missing placeholders", function() {
        const fmt = "{0}, {1}!";
        const fmtArgs = [ "Hello" ];

        const actualResult = MessageFormat.format(fmt, ...fmtArgs);
        const expectedResult = "Hello, {1}!"

        assert.equal(actualResult, expectedResult);
    });
});
