import { redirect } from 'react-router';

import { useTokenStore } from '@/models/store';

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
