import React from 'react';
import { useQuery, useQueryClient } from '@tanstack/react-query';
import { useUnmount } from 'ahooks';

import { getMenuList } from '@/api/user.api';

import './system-setting.scss';

const SystemSetting: React.FC = () => {
  const queryClient = useQueryClient();
  const { error, data, isFetching } = useQuery({
    queryKey: ['reMenu'],
    queryFn: ({ signal }) => getMenuList({ signal }),
    initialData: [],
  });

  useUnmount(() => {
    queryClient.cancelQueries({ queryKey: ['reMenu'] });
  });

  if (isFetching) {
    return <div>Loading</div>;
  }

  if (error) {
    return <div>Error: {error.message}</div>;
  }

  return (
    <div>
      <div styleName="wrapper">系统设置</div>
      <div>{data ? 'data' : 'null'}</div>
    </div>
  );
};

export default SystemSetting;
