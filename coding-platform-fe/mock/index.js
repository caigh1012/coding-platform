const express = require('express');
const app = express();
const cors = require('cors');
const api = require('./api');

// 配置允许跨域
app.use(cors());

app.use('/api', api);

app.listen(8080, function () {
  console.log('Express server runing at http://127.0.0.1:8080');
});
