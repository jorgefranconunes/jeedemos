/**
 *
 */

const fs = require("fs");
const path = require("path");

const CleanWebpackPlugin = require("clean-webpack-plugin");
const CopyWebpackPlugin = require("copy-webpack-plugin");


const buildModuleExports = () => (
    {
        mode: "development",
        entry: [
            pathOf("./src/main/html/index.html"),
        ],
        output: {
            path: pathOf("dist")
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
                    test: pathOf("./src/main/html/index.html"),
                    use: [
                        {
                            loader: "file-loader",
                            options: {
                                name: "[name].[ext]"
                            },
                        },
                        "extract-loader",
                        {
                            loader: "html-loader",
                            options: {
                                attrs: ["img:src", "link:href"],
                                root: pathOf("./node_modules"),
                            }
                        }
                    ],
                },
                {
                    test: /\.css$/,
                    use: [
                        "file-loader",
                        "extract-loader",
                        "css-loader",
                    ],
                }
            ]
        },
        plugins: [
            new CleanWebpackPlugin(["dist"]),
            new CopyWebpackPlugin([
                {
                    from: pathOf("./src/main/assets"),
                }
            ]),
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
