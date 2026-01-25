import { type AxiosRequestConfig, type AxiosResponse } from 'axios';
import { SafeAny } from '@/helpers/safe-any';
import HttpClient from './http-client';
import { BusinessCode } from './business-code';
import { Modal } from 'antd';

/**
 * 指定后台返回的 json 的数据结构
 */
interface IResponse<T = SafeAny> {
  code: string;
  data: T;
  message: string;
}

/**
 * 基础后台配置
 */
const config: AxiosRequestConfig = {
  baseURL: APIURL,
  timeout: 10000, // 10 秒超时
  headers: {
    'Content-Type': 'application/json;charset=utf-8',
  },
};

const http = new HttpClient(config);

/**
 * 实例对象
 */
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
      if (code === BusinessCode.Success) {
        return data;
        // 用于处理业务错误
      } else if (code === BusinessCode.Error) {
        Modal.error({
          title: '请求业务异常',
          content: message,
          okText: '知道了',
        });
        return;
      }
    }
  },
  // 处理 http 级别错误
  (error) => {
    Modal.error({
      title: '服务器异常，请稍后再试',
      content: error.message,
      okText: '知道了',
    });
  },
);

export { http };
