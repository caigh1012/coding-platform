/* eslint-disable no-console */
// 处理 Linux 系统的 husky 钩子不生效问题
const process = require('process');
const { exec } = require('child_process');
if (process.platform === 'linux') {
  exec('cd .husky && sh chmod.sh', (error, stdout) => {
    if (error) {
      console.error(`执行sh脚本时出错: ${error}`);
      return;
    }
    console.log(`执行sh脚本的输出: ${stdout}`);
  });
}
