import React, { useState } from 'react';
import { UserOutlined } from '@ant-design/icons';
import { Layout, Menu, Avatar } from 'antd';
import { Outlet, useNavigate } from 'react-router';
import { ItemType } from 'antd/es/menu/interface';
import { useMap, useMount } from 'ahooks';

import logo from '@/assets/logo.svg';
import { getMenuList } from '@/api/user.api';
import { getMenuTree } from '@/helpers/menu';
import { MenuItem } from '@/interfaces/user/user-menu.interface';

import './layouts.scss';

const { Header, Content, Sider } = Layout;
const Layouts: React.FC = () => {
  let navigate = useNavigate();
  const [collapsed, setCollapsed] = useState(false);
  const [_menuMap, { set: setMenuItem, get }] = useMap<number, MenuItem>([]);
  const [menu, setMenu] = useState<Array<ItemType>>([]);

  useMount(() => {
    getMenuList().then((res) => {
      if (res) {
        res.forEach((item) => {
          setMenuItem(item.menu_id, item);
        });
        setMenu(getMenuTree(res));
      }
    });
  });

  function go(key: string) {
    const node = get(Number(key));
    if (node) {
      navigate(node.path);
    }
  }

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
            style={{ height: '100%', borderInlineEnd: 0 }}
            items={menu}
            onClick={(item) => go(item.key)}
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
