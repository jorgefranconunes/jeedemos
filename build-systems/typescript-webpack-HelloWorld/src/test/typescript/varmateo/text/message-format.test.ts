import { format } from "#varmateo/text/message-format.js";

import { expect } from "chai";

suite("message-format", function() {
    test("Given no placeholders, when format, then result is the same string", function() {
        // GIVEN
        const input = "Hello, world";

        // WHEN
        const actualResult = format(input);

        // THEN
        const expectedResult = input;

        expect(actualResult)
            .to.equal(expectedResult);
    });

    test("Given placeholders, when format, then result has placeholders replaced", function() {
        // GIVEN
        const fmt = "{0}, {1}!";
        const fmtArgs = [ "Hello", "world" ];

        // WHEN
        const actualResult = format(fmt, ...fmtArgs);

        // THEN
        const expectedResult = "Hello, world!";

        expect(actualResult)
            .to.equal(expectedResult);
    });

    test("Given extra placeholders, when format, then extra placeholders are kept", function() {
        // GIVEN
        const fmt = "{0}, {1}!";
        const fmtArgs = [ "Hello" ];

        // WHEN
        const actualResult = format(fmt, ...fmtArgs);

        // THEN
        const expectedResult = "Hello, {1}!"

        expect(actualResult)
            .to.equal(expectedResult);
    });
});

