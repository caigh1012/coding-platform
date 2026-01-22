import React, { useEffect, useState } from 'react';
import logo from '@/assets/logo512.png';
import { Button, Space } from 'antd';
import { getExampleInfo } from '@/api/example';
import { Link, Outlet } from 'react-router';
import { comlinkWorker } from '@/helpers/worker';
import './home.scss';

const Home: React.FC = () => {
  const [num, setNum] = useState<number>(0);

  useEffect(() => {
    getExampleInfo().then((res) => {
      // console.log(res);
    });
  }, []);

  useEffect(() => {
    const fn = async () => {
      // console.log(`Counter: ${await comlinkWorker.counter}`);
      await comlinkWorker.inc();
      // console.log(`Counter: ${await comlinkWorker.counter}`);
    };
    fn();
  }, []);

  return (
    <div styleName="wrapper">
      <div styleName="logo">
        <img
          src={logo}
          alt=""
        />
      </div>
      <div styleName="content">
        <div styleName="content-status">{num}</div>
        <div style={{ textAlign: 'center' }}>
          <Space>
            <Button
              type="primary"
              onClick={() => setNum(num + 1)}>
              Plus 1
            </Button>
            <Button
              type="primary"
              onClick={() => setNum(num - 1)}>
              Minus 1
            </Button>
          </Space>
        </div>
        <div
          style={{ textAlign: 'center' }}
          styleName="content-route-box">
          <Space>
            <Link to="pageone">Page one</Link>
            <Link to="pagetwo">Page two</Link>
          </Space>
        </div>
        <div styleName="route-outlet-box">
          <Outlet />
        </div>
      </div>
    </div>
  );
};

export default Home;
