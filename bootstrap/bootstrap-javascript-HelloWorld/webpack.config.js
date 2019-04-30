/**
 *
 */

const fs = require("fs");
const path = require("path");

const CleanWebpackPlugin = require("clean-webpack-plugin");
const HtmlWebpackPlugin = require('html-webpack-plugin')


const buildModuleExports = () => (
    {
        mode: "development",
        entry: pathOf("./src/main/javascript/varmateo/bootstrap/demo.js"),
        output: {
            path: pathOf("dist"),
            filename: "[hash].js"
        },
        resolve: {
            modules: [
                pathOf("./src/main/javascript"),
                pathOf("./node_modules"),
            ],
        },
        module: {
            rules: [
                {
                    test: /\.css$/,
                    use: [
                        "file-loader",
                        "extract-loader",
                        {
                            loader: "css-loader",
                            // options: {
                            //     sourceMap: true,
                            // },
                        },
                    ],
                }
            ]
        },
        plugins: [
            new CleanWebpackPlugin(["dist"]),
            new HtmlWebpackPlugin({
                template: pathOf("src/main/html/index.html"),
                inject: "head",
            }),
        ],
        devtool: "inline-source-map",
        devServer: {
            contentBase: pathOf("./dist"),
            port: 8080,
        },
    }
);


const pathOf = (relPath) => path.resolve(__dirname, relPath)


module.exports = buildModuleExports()
