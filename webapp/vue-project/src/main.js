import './assets/main.css'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
/*  每个 Vue 应用都是通过 createApp 函数创建一个新的 应用实例： 根组件选项 */
const app = createApp(App)
//
app.use(router)
app.use(ElementPlus)
//挂载应用
app.mount('#app')
