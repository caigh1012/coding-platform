import React, { useEffect } from 'react';

import './other.scss';

const Other: React.FC = () => {
  useEffect(() => {
    // console.log('init');
    return () => {
      // console.log('卸载');
    };
  }, []);
  return <div styleName="other-box">other</div>;
};

export default Other;
