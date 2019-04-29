/**
 *
 */

const path = require('path');
const nodeExternals = require("webpack-node-externals");
const WebpackShellPlugin = require('webpack-shell-plugin');


module.exports = {
    mode: "development",
    target: "node",
    externals: [nodeExternals()],
    entry: path.resolve(__dirname, './src/test/javascript/index-test.js'),
    output: {
        filename: 'main-test.js',
        path: path.resolve(__dirname, 'dist')
    },
    resolve: {
        modules: [
            path.resolve(__dirname, "./src/main/javascript"),
            path.resolve(__dirname, "./src/test/javascript"),
            "node_modules",
        ],
    },
    devtool: "inline-source-map",
    plugins: [
        new WebpackShellPlugin({
            onBuildExit: "mocha ./dist/main-test.js"
        }),
    ],
};
