import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router';

import { getCaptcha } from '@/api/login.api';

import LoginForm from './components/login-form/login-form';
import './login.scss';

const Login: React.FC = () => {
  const [captcha, setCaptcha] = useState('');
  let navigate = useNavigate();

  function gotoRegister() {
    navigate('/register', { replace: true });
  }

  useEffect(() => {
    getCaptcha().then((res) => {
      if (res) {
        setCaptcha(res.captcha);
        // console.log(res);
      }
    });
  }, []);

  return (
    <div styleName="login-wrap">
      <div styleName="login-main">
        <div styleName="login-left"></div>
        <div styleName="login-right">
          <h3 styleName="login-title">
            <span>
              欢迎登录 <span>智能代码平台</span>
            </span>
            <span
              aria-hidden="true"
              onClick={() => gotoRegister()}>
              没有帐号？ 点此注册
            </span>
          </h3>
          <LoginForm></LoginForm>
          <img
            src={'data:image/png;base64,' + captcha}
            alt=""></img>
        </div>
      </div>
    </div>
  );
};

export default Login;
