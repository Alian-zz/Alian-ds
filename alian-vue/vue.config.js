

module.exports = {
    baseUrl: './',
    assetsDir: 'static',
    productionSourceMap: false,

    devServer: {
        proxy: {
            '/api':{
                target:'http://192.168.72.1:4433',
                changeOrigin:false,
                pathRewrite:{
                    '/api':''
                }
            }
        },
        https: true,
    }






}

