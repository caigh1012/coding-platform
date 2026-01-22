// 为图片定义模块
declare module '*.svg';
declare module '*.png';
declare module '*.jpg';

// 为 webpack 的 DefinePlugin 插件注入变量类型定义
declare var VERSION: string; // 版本
declare var ENV: string; // 环境：development、production
declare var APIURL: string; // 接口地址
declare var baseHref: string; // base
