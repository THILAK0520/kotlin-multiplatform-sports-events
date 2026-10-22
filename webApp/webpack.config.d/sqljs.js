//config.resolve = config.resolve || {};
//config.resolve.fallback = {
//    ...(config.resolve.fallback || {}),
//    path: require.resolve("path-browserify"),
//    fs: false,
//    crypto: require.resolve("crypto-browserify")
//};

config.resolve = config.resolve || {};
config.resolve.fallback = {
    ...(config.resolve.fallback || {}),
    path: require.resolve("path-browserify"),
    fs: false,
    crypto: require.resolve("crypto-browserify"),
    //buffer: require.resolve("buffer"),
    buffer: require.resolve("buffer"),
    stream: require.resolve("stream-browserify"),
    vm: require.resolve("vm-browserify")
};

config.plugins = config.plugins || [];
const webpack = require("webpack");
config.plugins.push(
    new webpack.ProvidePlugin({
        Buffer: ["buffer", "Buffer"]
    })
);

const CopyWebpackPlugin = require("copy-webpack-plugin");
config.plugins.push(
    new CopyWebpackPlugin({
        patterns: [
            {
                from: require.resolve("sql.js/dist/sql-wasm.js"),
                to: "sql-wasm.js"
            }
        ]
    })
);
