import { HttpStatusCode, type AxiosRequestConfig, type AxiosResponse } from 'axios';
import { SafeAny } from '@/helpers/safe-any';
import HttpClient from './http-client';
import { HttpEnumCode } from './http-status-code';
import { message } from 'antd';

/**
 * 指定后台返回的 json 的数据结构
 */
interface IResponse<T = SafeAny> {
  code: string;
  data: T;
  message: string;
}

const config: AxiosRequestConfig = {
  baseURL: APIURL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json;charset=utf-8',
  },
};

const http = new HttpClient(config);

const instance = http.getInstance();

/**
 * 请求拦截器
 */
instance.interceptors.request.use(
  (config) => {
    config.url = 'api/' + config.url;
    return config;
  },
  (error) => Promise.reject(error),
);

/**
 * 响应拦截器
 */
instance.interceptors.response.use(
  (response: AxiosResponse<IResponse>) => {
    if (response.status >= 200 && response.status < 300) {
      const { code, data, message } = response.data;
      if (code === HttpEnumCode.Success) {
        return data;
      } else {
        Promise.reject(message); // 用于处理业务错误
      }
    }
  },
  (error) => {
    message.error('接口错误：' + error);
  }, // 处理http错误
);

export { http };
