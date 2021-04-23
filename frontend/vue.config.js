module.exports = {
  lintOnSave: false,
  devServer: {
    proxy: { // 配置跨域
      '/api': {
        target: 'http://ec2-18-206-59-173.compute-1.amazonaws.com/',
        changOrigin: true, // 允许跨域
        pathRewrite: {
          '^/api': '',
        },
      },
    },
  },
};
