import { wrap } from 'comlink';

import { FileUploadWorkerApi } from './worker/flie-upload.worker';

export function createFileWorker() {
  const fileWorker = new Worker(new URL('./worker/flie-upload.worker.ts', import.meta.url), {
    type: 'module',
  });
  const fileWorkerApi = wrap<FileUploadWorkerApi>(fileWorker);
  return { fileWorker, fileWorkerApi };
}
