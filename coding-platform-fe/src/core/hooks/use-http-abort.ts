import { useUnmount } from 'ahooks';

import { controller } from '@/api/http';

/**
 * http abort
 */
export function useHttpAbort() {
  useUnmount(() => {
    controller.abort();
  });
}
