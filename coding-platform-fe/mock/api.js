const express = require('express');
const api = express.Router();
const getdata = require('./get-data.json');
const postData = require('./post-data.json');

api.get('/example', (req, res) => {
  res.send(getdata);
});

api.post('/classlist', (req, res) => {
  res.send(postData);
});

api.post('/login', (req, res) => {
  res.send(
    JSON.stringify({
      code: '0000',
      message: '请求成功',
      data: {
        token: 'abcd',
      },
    }),
  );
});

module.exports = api;
