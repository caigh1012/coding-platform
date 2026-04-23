import { saveAs } from 'file-saver';
import { type AxiosRequestConfig, type AxiosResponse } from 'axios';

import { useTokenStore } from '@/models/store';
import { FileDto } from '@/interfaces/common/file.interface';

import HttpClient from './http-client';
import { BusinessCode } from './business-code';
import { showErrorModal } from './error-modal';

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
  baseURL: apiUrl,
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
    config.url = apiPrefix + config.url;

    return config;
  },
  (error) => Promise.reject(error),
);

/**
 * 响应拦截器
 */
instance.interceptors.response.use(
  (response: AxiosResponse<IResponse>) => {
    if (response?.config?.url && response?.config?.url?.indexOf('.do') > -1) {
      const data = response?.config?.data;
      if (response?.data) {
        const requestData: FileDto | null | undefined = data ? JSON.parse(data) : null;
        if (requestData) {
          saveAs(response.request.response, requestData.fileId);
        }
      }
    }

    if (response.status >= 200 && response.status < 300) {
      const { code, data, message } = response.data;
      if (code === BusinessCode.Success) {
        return data;
      } else {
        // 用于处理业务级别错误
        showErrorModal(message, code);
        return;
      }
    }
  },
  // 处理 http 级别错误
  (error) => showErrorModal(error.message),
);

export { http };
