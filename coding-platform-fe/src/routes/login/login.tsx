import React from 'react';

import LoginForm from './components/login-form/login-form';
import './login.scss';

const Login: React.FC = () => {
  return (
    <div styleName="login-wrap">
      <div styleName="login-main">
        <div styleName="login-left"></div>
        <div styleName="login-right">
          <h3 styleName="login-title">
            欢迎登录 <span>智能代码平台</span>
          </h3>
          <LoginForm></LoginForm>
        </div>
      </div>
    </div>
  );
};

export default Login;
