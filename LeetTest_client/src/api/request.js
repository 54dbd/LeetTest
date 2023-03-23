//该文档用于axios二次封装
import axios from "axios";
import nprogress from "nprogress";
import "nprogress/nprogress.css";

import { getToken } from "@/utils/token";
import { ElMessage } from "element-plus";

//1.利用axios对象的方法create插件一个axios对象实例
//2.requests其实就是axios
const requests = axios.create({
  //基础路径：发送请求时，路径中会出现csky
  baseURL: "http://localhost:8001/csky",
  //超时事件
  timeout: 100000,
});
//请求拦截器
requests.interceptors.request.use(
  (config) => {
    //配置对象,在发送请求之前做些什么
    //配置用户登录信息
    if (getToken()) {
      config.headers.TOKEN = getToken();
    }
    nprogress.start();
    return config;
  },
  (error) => {
    ElMessage.error(error);
  }
);
//响应拦截器
requests.interceptors.response.use(
  (response) => {
    // 2xx 范围内的状态码都会触发该函数。
    // 对响应数据做点什么
    nprogress.done();
    return response;
  },
  (error) => {
    ElMessage.error(error);
  }
);

//暴露axios
export default requests;
