import React, { useState } from 'react';
import { Button, Space } from 'antd';

import logo from '@/assets/logo512.png';

import './home.scss';

const Home: React.FC = () => {
  const [num, setNum] = useState<number>(0);

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
      </div>
    </div>
  );
};

export default Home;
