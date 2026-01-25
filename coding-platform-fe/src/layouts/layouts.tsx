import React, { useState } from 'react';
import { LaptopOutlined, NotificationOutlined, UserOutlined } from '@ant-design/icons';
import type { MenuProps } from 'antd';
import { Layout, Menu, Avatar } from 'antd';
import logo from '@/assets/logo.svg';
import './layouts.scss';

const { Header, Content, Sider } = Layout;

import { Outlet } from 'react-router';

const items2: MenuProps['items'] = [UserOutlined, LaptopOutlined, NotificationOutlined].map((icon, index) => {
  const key = String(index + 1);

  return {
    key: `sub${key}`,
    icon: React.createElement(icon),
    label: `subnav ${key}`,
    children: Array.from({ length: 4 }).map((_, j) => {
      const subKey = index * 4 + j + 1;
      return {
        key: subKey,
        label: `option${subKey}`,
      };
    }),
  };
});

const Layouts: React.FC = () => {
  const [collapsed, setCollapsed] = useState(false);
  return (
    <Layout>
      <Header>
        <div styleName="header-left">
          <div styleName="logo">
            <img
              src={logo}
              alt="logo"
            />
          </div>
          <h2 styleName="title">智能代码平台</h2>
        </div>
        <div>
          <Avatar icon={<UserOutlined />}></Avatar>
        </div>
      </Header>
      <Layout>
        <Sider
          collapsible
          collapsed={collapsed}
          onCollapse={(value) => setCollapsed(value)}
          width={200}>
          <Menu
            mode="inline"
            defaultSelectedKeys={['1']}
            defaultOpenKeys={['sub1']}
            style={{ height: '100%', borderInlineEnd: 0 }}
            items={items2}
          />
        </Sider>
        <Content>
          <Outlet></Outlet>
        </Content>
      </Layout>
    </Layout>
  );
};

export default Layouts;
