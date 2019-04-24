/**
 *
 */

const fs = require("fs");
const path = require('path');
const R = require("ramda");

const CleanWebpackPlugin = require('clean-webpack-plugin');
const CopyWebpackPlugin = require('copy-webpack-plugin');


const buildModuleExports = () => (
    {
        mode: "development",
        entry: buildEntryMap(pathOf("./src/main/javascript/varmateo/demo"), /demo.*\.js$/),
        output: {
            filename: '[name].js',
            path: pathOf('dist')
        },
        resolve: {
            modules: [
                pathOf("./src/main/javascript"),
                "node_modules",
            ],
        },
        plugins: [
            new CleanWebpackPlugin(['dist']),
            new CopyWebpackPlugin([
                {
                    from: pathOf("./src/main/assets"),
                }
            ]),
        ],
        devtool: "inline-source-map",
        devServer: {
            contentBase: pathOf('./dist'),
            port: 8080,
        },
    }
);


const pathOf = (relPath) => path.resolve(__dirname, relPath)


/**
 * Searches for files under `dirPath`, and builds a map where keys are
 * the file basename (minus extension) and values are corresponding
 * paths.
 */
function buildEntryMap(dirPath, nameRegexp) {

    const findAllFiles = fs.readdirSync;
    const filterFiles  = R.filter(R.test(nameRegexp));
    const fullPath     = R.map(name => path.resolve(dirPath, name));
    const buildEntries = R.map((entryPath) => R.objOf(path.basename(entryPath, ".js"), entryPath));
    const mergeEntries = R.reduce(R.merge, {});

    const entryMap = R.pipe(
        findAllFiles,
        filterFiles,
        fullPath,
        buildEntries,
        mergeEntries)

    return entryMap(dirPath);
}


module.exports = buildModuleExports()
