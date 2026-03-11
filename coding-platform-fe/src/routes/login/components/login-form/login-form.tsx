import React from 'react';
import { Button, Form, Input } from 'antd';
import { useNavigate } from 'react-router';
import { LockOutlined, UserOutlined } from '@ant-design/icons';

import { postPwdLogin } from '@/api/login.api';
import { useTokenStore } from '@/models/store';

import type { FormProps } from 'antd';

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
      if (res) {
        setToken(res.token);
        navigate('/', { replace: true });
      }
    });
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
        autoComplete="off">
        <Form.Item<FieldType>
          label="用户名"
          name="username"
          rules={[{ required: true, message: '请输入用户名' }]}>
          <Input
            prefix={<UserOutlined />}
            placeholder="请输入用户名"
          />
        </Form.Item>

        <Form.Item<FieldType>
          label="密码"
          name="password"
          rules={[{ required: true, message: '请输入密码' }]}>
          <Input.Password
            prefix={<LockOutlined />}
            placeholder="请输入密码"
          />
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
