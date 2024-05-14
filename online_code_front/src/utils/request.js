import axios from 'axios';

export const baseURL = 'http://127.0.0.1:7999';

const request = axios.create({
  baseURL: baseURL
});

// 创建一个函数来获取token
const getToken = () => {
  return localStorage.getItem('token');
};

// 在每次请求时动态设置token
request.interceptors.request.use(config => {
  config.headers.token = getToken();
  return config;
}, error => {
  return Promise.reject(error);
});

export default request;



