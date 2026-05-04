import React from 'react';

// 此处是加载组件时就会加载
// const OtherComponent = loadable(() => import('@/components/other/other'));

import LazyComponent from '@/components/lazy/lazy';

import './login-log.scss';

const LoginLog: React.FC = () => {
  return (
    <div styleName="wrapper">
      <div styleName="login-box">登录日志</div>
      <LazyComponent
        load={() => import('@/components/other/other')}
        fallback="加载中…"
      />
    </div>
  );
};

export default LoginLog;
