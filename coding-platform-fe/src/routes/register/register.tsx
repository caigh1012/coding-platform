import React from 'react';
import { useNavigate } from 'react-router';

import './register.scss';

const Register: React.FC = () => {
  let navigate = useNavigate();

  function gotoLogin() {
    navigate('/login', { replace: true });
  }

  return (
    <div
      aria-hidden="true"
      onClick={() => gotoLogin()}>
      注册页面，去登录
    </div>
  );
};

export default Register;
