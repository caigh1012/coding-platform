import React from 'react';
import type { FormProps } from 'antd';
import { Button, Form, Input } from 'antd';
import { postPwdLogin } from '@/api/login.api';
import { useTokenStore } from '@/models/store';
import { replace } from 'react-router';
import { useNavigate } from 'react-router';

import './login-form.scss';

type FieldType = {
  username: string;
  password: string;
};

const LoginForm = () => {
  const { setToken } = useTokenStore();
  let navigate = useNavigate();
  const onFinish: FormProps<FieldType>['onFinish'] = (values) => {
    postPwdLogin(values).then((res) => {
      // console.log(res);
      // console.log(res.token);
      // setToken(res.token);
      // navigate('/', { replace: true });
    });
  };

  const onFinishFailed: FormProps<FieldType>['onFinishFailed'] = (errorInfo) => {
    // console.log('Failed:', errorInfo);
  };

  return (
    <div>
      <Form
        name="basic"
        labelCol={{ span: 6 }}
        wrapperCol={{ span: 18 }}
        style={{ maxWidth: 600, minWidth: 360 }}
        initialValues={{ remember: true }}
        onFinish={onFinish}
        onFinishFailed={onFinishFailed}
        autoComplete="off">
        <Form.Item<FieldType>
          label="用户名"
          name="username"
          rules={[{ required: true, message: '请输入用户名' }]}>
          <Input />
        </Form.Item>

        <Form.Item<FieldType>
          label="密码"
          name="password"
          rules={[{ required: true, message: '请输入密码' }]}>
          <Input.Password />
        </Form.Item>

        <Form.Item label={null}>
          <Button
            block
            type="primary"
            htmlType="submit">
            登录
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
};

export default LoginForm;
