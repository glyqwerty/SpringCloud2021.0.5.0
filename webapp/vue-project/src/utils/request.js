import axios from 'axios'

// 1. 创建实例时配置基础信息
const instance = axios.create({
  baseURL: '/prod-api', // 建议将 baseURL 放在这里
  timeout: 60000,
  headers: {
    'Content-Type': 'application/json;charset=utf-8'
  }
})

// 2. 拦截器应该绑定在 instance 上，而不是全局 axios 上
instance.interceptors.request.use(function (config) {
  // get请求映射params参数
  // if (config.method === 'get' && config.params) {
  //   let url = config.url + '?' + tansParams(config.params);
  //   url = url.slice(0, -1);
  //   config.params = {};
  //   config.url = url;
  // }
  return config
}, function (error) {
  return Promise.reject(error);
})

instance.interceptors.response.use(function (response) {
  return response.data  
}, function (error) {
  return Promise.reject(error);
})

// 3. 导出实例
export default instance