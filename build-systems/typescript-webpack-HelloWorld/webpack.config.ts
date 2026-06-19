import { fileURLToPath } from "url";
import HtmlWebpackPlugin from 'html-webpack-plugin';
import path from "node:path";
import webpack from "webpack";

// in case you run into any TypeScript error when configuring `devServer`
//import "webpack-dev-server";

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const buildConfig = (): webpack.Configuration =>
    ({
	mode: "development",
	entry: "./src/main/typescript/varmateo/demo/demo02.ts",
	module: {
	    rules: [
		{
		    test: /\.tsx?$/,
		    use: "ts-loader",
		    exclude: /node_modules/,
		},
	    ],
	},
	resolve: {
	    extensions: [".tsx", ".ts", "..."],
	    tsconfig: true,
	},
	output: {
	    clean: true,
	    filename: "demo02.js",
	    path: pathOf("dist-build"),
	    publicPath: "",
	},
	plugins: [
	    new HtmlWebpackPlugin({
		filename: "demo02.html",
		inject: false,
		jsFile: "./demo02.js",
		template: pathOf("src", "main", "assets", "demo02.html"),
	    }),
	],
	devtool: "inline-source-map",
	devServer: {
	    port: 1234,
	    hot: false,
	    liveReload: true,
	},
    });

const pathOf = (path1: string, ...rest: string[]): string =>
    path.resolve(__dirname, path1, ...rest);

const config: webpack.Configuration = buildConfig()

export default config;
