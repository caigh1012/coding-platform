import React, { useState } from 'react';
import { useMount } from 'ahooks';
import { Button, Form, Input } from 'antd';
import { useNavigate } from 'react-router';
import { LockOutlined, UserOutlined } from '@ant-design/icons';

import { postPwdLogin } from '@/api/login.api';
import { useTokenStore } from '@/models/store';
import { getGraphCaptcha } from '@/api/captcha.api';

import type { FormProps } from 'antd';

import './login-form.scss';

type FieldType = {
  username: string;
  password: string;
  captchaId: string;
  captchaCode: string;
};

const LoginForm = () => {
  const { setToken } = useTokenStore();
  const [form] = Form.useForm();
  const [captcha, setCaptcha] = useState('');
  const [captchaId, setCaptchaId] = useState('');

  let navigate = useNavigate();

  const onFinish: FormProps<FieldType>['onFinish'] = (values) => {
    postPwdLogin({
      ...values,
      captchaId,
    }).then((res) => {
      if (res) {
        setToken(res.token);
        navigate('/', { replace: true });
      }
    });
  };

  useMount(() => {
    getGraphCaptcha().then((res) => {
      if (res) {
        setCaptcha(res.captcha);
        setCaptchaId(res.captchaId);
      }
    });
  });

  return (
    <div>
      <Form
        name="basic"
        form={form}
        labelCol={{ span: 6 }}
        wrapperCol={{ span: 18 }}
        style={{ maxWidth: 600, minWidth: 360 }}
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
          label="密  码"
          name="password"
          rules={[{ required: true, message: '请输入密码' }]}>
          <Input.Password
            prefix={<LockOutlined />}
            placeholder="请输入密码"
          />
        </Form.Item>

        <Form.Item<FieldType>
          label="验证码"
          name="captchaCode"
          rules={[{ required: true, message: '请输入图形验证码' }]}>
          <Input placeholder="请输入图形验证码" />
        </Form.Item>

        <Form.Item>
          <img
            src={'data:image/png;base64,' + captcha}
            alt=""></img>
        </Form.Item>

        <Form.Item>
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
