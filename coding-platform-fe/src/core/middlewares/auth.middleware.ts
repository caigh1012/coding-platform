import { useTokenStore } from '@/models/store';
import { redirect } from 'react-router';

/**
 * 认证 中间件
 */
async function authMiddleware() {
  const { token } = useTokenStore.getState();

  if (!token) {
    throw redirect('/login');
  }
}

export default authMiddleware;
