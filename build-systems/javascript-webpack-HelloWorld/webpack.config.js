/**
 *
 */

const path = require('path');
const CleanWebpackPlugin = require('clean-webpack-plugin');
const CopyWebpackPlugin = require('copy-webpack-plugin');


module.exports = {
    mode: "development",
    entry: path.resolve(__dirname, './src/index.js'),
    output: {
        filename: 'main.js',
        path: path.resolve(__dirname, 'dist')
    },
    plugins: [
        new CleanWebpackPlugin(['dist']),
        new CopyWebpackPlugin([
            {
                from: path.resolve(__dirname, "./assets"),
            }
        ]),
    ],
    devtool: "inline-source-map",
    devServer: {
        contentBase: path.resolve(__dirname, './dist'),
        port: 8080,
    },
};
