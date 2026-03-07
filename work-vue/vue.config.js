const proxy = require('http-proxy-middleware');

module.exports = {

    devServer: {
        port: 8081,
        https: false,
        hotOnly: false,
        compress: true,
        open: false,
        disableHostCheck: true,
        proxy: {
            "/api": {
                target: 'http://127.0.0.1:9902',
                changeOrigin: true,
                ws: true,
                pathRewrite: {'^/api': '/'}
            },
        }
    }
}