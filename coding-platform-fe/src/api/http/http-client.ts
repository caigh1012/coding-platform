import axios from 'axios';

import { SafeAny } from '@/helpers/safe-any';

import type { AxiosRequestConfig, AxiosInstance } from 'axios';

class HttpClient {
  private instance: AxiosInstance;

  constructor(config: AxiosRequestConfig) {
    this.instance = axios.create(config);
  }

  getInstance() {
    return this.instance;
  }

  get<T>(url: string, config?: AxiosRequestConfig): Promise<T | undefined> {
    return this.instance.get(url, config);
  }

  post<T>(url: string, data?: SafeAny, config?: AxiosRequestConfig): Promise<T | undefined> {
    return this.instance.post(url, data, config);
  }
}

export default HttpClient;
