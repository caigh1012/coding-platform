import { wrap } from 'comlink';
import { ComlinkWorkerApi } from './worker/comlink.worker';

export const comlinkWorker = wrap<ComlinkWorkerApi>(
  new Worker(new URL('./worker/comlink.worker', import.meta.url), {
    type: 'module',
  }),
);
