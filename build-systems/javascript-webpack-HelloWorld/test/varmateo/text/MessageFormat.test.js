/**
 *
 */

const MessageFormat = require("varmateo/text/MessageFormat");

const assert = require("assert");


describe("MessageFormat", function() {
    describe("No format args", function() {
        it("should return the same string", function() {
            const input = "Hello, world";
            const result = MessageFormat.format(input);

            assert.equal(result, input);
        });
    });
});
