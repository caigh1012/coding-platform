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

module.exports = api;
