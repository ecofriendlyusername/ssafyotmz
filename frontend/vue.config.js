const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 3000
  }
})
devServer: {
  port: 3001 // 포트번호 변경
}
