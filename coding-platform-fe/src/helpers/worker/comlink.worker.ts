import { expose } from 'comlink';

export interface ComlinkWorkerApi {
  counter: number;
  inc: () => number;
}

class ComlinkWorker implements ComlinkWorkerApi {
  counter: number = 1;

  inc() {
    return this.counter++;
  }
}

expose(new ComlinkWorker());
