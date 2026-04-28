import { wrap } from 'comlink';

import { ComlinkWorkerApi } from './worker/comlink.worker';
import { FileUploadWorkerApi } from './worker/flie-upload.worker';

export const comlinkWorker = wrap<ComlinkWorkerApi>(
  new Worker(new URL('./worker/comlink.worker', import.meta.url), {
    type: 'module',
  }),
);

export const fileUploadWorker = wrap<FileUploadWorkerApi>(
  new Worker(new URL('./worker/flie-upload.worker.ts', import.meta.url), {
    type: 'module',
  }),
);
