import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueDevTools(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
    server: {
    proxy: {
      // 匹配所有以 /bill 开头的请求
      '/prod-api': {
        target: 'http://localhost:9300', // 真实的后端地址
        changeOrigin: true, // 必须设为 true，伪装成目标服务器的请求
        // 如果你的后端接口本身就有 /bill 前缀，则不需要 rewrite
        // 如果后端没有 /bill 前缀，可以取消下面这行的注释来去掉前缀：
        rewrite: path => path.replace(/^\/prod-api/, '')
      }
    }
  }
})
