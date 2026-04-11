import React, { useState } from 'react';
import { NotificationOutlined, UserOutlined } from '@ant-design/icons';
import { Layout, Menu, message, Avatar, Dropdown, Badge, Space, Modal } from 'antd';
import { Outlet, useNavigate } from 'react-router';
import { ItemType } from 'antd/es/menu/interface';
import { useMap, useMount, useRequest, useSessionStorageState } from 'ahooks';

import logo from '@/assets/logo192.png';
import { getMenuList } from '@/api/user.api';
import { getMenuTree } from '@/helpers/menu';
import { MenuItem } from '@/interfaces/user/user-menu.interface';
import { logout } from '@/api/login.api';
import { useTokenStore } from '@/models/store';

import type { MenuProps } from 'antd';

import './layouts.scss';

const { Header, Content, Sider } = Layout;

const items = [
  {
    key: '1',
    label: <span>个人中心</span>,
  },
  {
    key: '2',
    label: <span>系统设置</span>,
  },
  {
    key: '3',
    label: <span>退出登录</span>,
  },
];

const Layouts: React.FC = () => {
  const { clearToken } = useTokenStore();
  const [collapsed, setCollapsed] = useState(false);
  const [_menuMap, { set: setMenuItem, get }] = useMap<number, MenuItem>([]);
  const [menu, setMenu] = useState<Array<ItemType>>([]);
  const [open, setOpen] = useState(false);
  const [selectedKey, setSelectedKey] = useSessionStorageState('MenuSelectedKey', { defaultValue: ['1'] });
  const [stateOpenKeys, setStateOpenKeys] = useSessionStorageState<string[]>('MenuOpenKey', { defaultValue: [] });
  let navigate = useNavigate();

  /**
   * useRequest 建议只使用 get 请求且无参数返回时使用
   */
  const { loading, run } = useRequest(logout, {
    manual: true,
    onSuccess: () => {
      clearToken().then((token) => {
        if (!token) {
          setOpen(false);
          message.success('退出登录成功！');
          navigate('/login');
        }
      });
    },
  });

  const handleMenuClick: MenuProps['onClick'] = (e) => {
    if (e.key === '3') {
      setOpen(true);
    }
  };

  function confirmLogout() {
    run();
  }

  function onSelectChange(selectedKeys: string[]) {
    setSelectedKey(selectedKeys);
  }

  function onOpenChange(openKeys: string[]) {
    setStateOpenKeys(openKeys);
  }

  function goto(key: string) {
    const node = get(Number(key));
    if (node) {
      navigate(node.path);
    }
  }

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

  return (
    <>
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
            <Space align="center">
              <Badge dot>
                <NotificationOutlined style={{ fontSize: 16, color: '#fff' }} />
              </Badge>
              <Dropdown
                menu={{ items, onClick: handleMenuClick }}
                placement="bottomLeft">
                <Avatar icon={<UserOutlined />}></Avatar>
              </Dropdown>
            </Space>
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
              openKeys={stateOpenKeys}
              selectedKeys={selectedKey}
              style={{ height: '100%', borderInlineEnd: 0 }}
              items={menu}
              onOpenChange={(item) => onOpenChange(item)}
              onSelect={(item) => onSelectChange(item.selectedKeys)}
              onClick={(item) => goto(item.key)}
            />
          </Sider>
          <Content>
            <Outlet></Outlet>
          </Content>
        </Layout>
      </Layout>
      <Modal
        title="退出登录确认"
        okText="确认退出"
        cancelText="取消"
        open={open}
        onOk={confirmLogout}
        onCancel={() => setOpen(false)}
        confirmLoading={loading}>
        <div style={{ height: '40px' }}>确认是否退出登录？</div>
      </Modal>
    </>
  );
};

export default Layouts;
