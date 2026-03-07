let path = require('path');
const ExtractTextPlugin = require("extract-text-webpack-plugin")

module.exports =
    {
    mode: 'development',
    // loader 配置
    module: {
        rules: [
            {
                test: /\.css$/,
                //use 由下至上  css to js  append to html
                use: [
                    "style-loader",
                    "css-loader"
                ]
            },
            {
                test: /\.sass$/,
                loaders: ['style', 'css', 'sass']
            },
            {
                test: /\.vue$/,
                loader: 'vue-loader',
                options: {
                    extractCSS: true
                }
            }


        ]
    },
    plugins: [new ExtractTextPlugin("style.css")]
}