import { http } from './http';

/**
 * ai 聊天功能
 */
export function aiChat(inputMessage: string) {
  return http.post(
    '/ai/generatestream.json',
    { message: inputMessage },
    {
      headers: { responseType: 'stream' },
    },
  );
}
