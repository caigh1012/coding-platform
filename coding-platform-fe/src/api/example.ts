import { http } from './http';
import { Example } from '@/interfaces/example.interface';

/**
 * 请求示例
 */
export function getExampleInfo() {
  return http.get<Example>('example');
}
