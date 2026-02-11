import { type AxiosRequestConfig, type AxiosResponse } from 'axios';
import { Modal } from 'antd';

import { SafeAny } from '@/helpers/safe-any';
import { useTokenStore } from '@/models/store';

import HttpClient from './http-client';
import { BusinessCode } from './business-code';

/**
 * 此处是一个闭包，利用闭包防止多个接口错误时只报一个弹框
 */
const showModal = () => {
  let isOpen = false;

  return (message: string) => {
    if (isOpen) return;
    isOpen = true;
    Modal.error({
      title: '错误',
      content: message,
      okText: '知道了',
      afterClose: () => {
        isOpen = false;
      },
    });
  };
};

const showModalFuc = showModal();

/**
 * 指定后台返回的 json 的数据结构
 */
interface IResponse<T = SafeAny> {
  code: string;
  data?: T;
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
    const { token } = useTokenStore.getState();
    config.headers = Object.assign(config.headers, { Authorization: token });
    config.url = '/api' + config.url;

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
        // 用于处理业务级别错误
      } else if (code === BusinessCode.Error) {
        showModalFuc(message);
        return;
      }
    }
    return;
  },
  // 处理 http 级别错误
  (error) => showModalFuc(error.message),
);

export { http };
