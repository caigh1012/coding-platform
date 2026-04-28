function paralleTask(tasks: SafeAny, paralleCount = 2): Promise<void> {
  return new Promise((resolve) => {
    if (tasks.length === 0) {
      resolve();
      return;
    }

    let nextIndex = 0;
    let finishCount = 0;

    function _run() {
      const task = tasks[nextIndex];
      nextIndex++;

      task().then(() => {
        finishCount++;
        if (nextIndex < tasks.length) {
          _run();
        } else if (finishCount === tasks.length) {
          resolve();
        }
      });
    }

    for (let i = 0; i < paralleCount && i < tasks.length; i++) {
      _run();
    }
  });
}

export { paralleTask };
