module.exports = {
    devServer: {
        proxy: { //设置代理
            '/api': {
                target: 'http://localhost:8888',
                changeOrigin: true// 将主机标头的原点更改为目标URL
            }
        }
    }
}