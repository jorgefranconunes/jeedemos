/**
 *
 */

const path = require('path');
const nodeExternals = require("webpack-node-externals");

module.exports = {
    output: {
        // use absolute paths in sourcemaps (important for debugging via IDE)
        devtoolModuleFilenameTemplate: '[absolute-resource-path]',
        devtoolFallbackModuleFilenameTemplate: '[absolute-resource-path]?[hash]'
    },
    target: "node",
    externals: [nodeExternals()],
    devtool: "inline-cheap-module-source-map",
    resolve: {
        modules: [
            path.resolve(__dirname, "./src"),
            "node_modules",
        ],
    },
};
