import { Modal } from 'antd';

import router from '@/routes';
import { useTokenStore } from '@/models/store';
import { initialToken } from '@/models/states/token-state';

import { BusinessCode } from './business-code';

/**
 * 此处是一个闭包，利用闭包防止多个接口错误时只报一个弹框
 */
const httpErrorModal = () => {
  let isOpen = false;

  return (message: string, code?: string) => {
    if (isOpen) return;
    isOpen = true;
    let modalCfg = {
      title: '错误',
      content: message,
      okText: '确认',
      keyboard: false,
      closable: false,
      afterClose: () => {
        isOpen = false;
      },
    };

    if (code === BusinessCode.Error) {
      Modal.error(modalCfg);
      return;
    }

    /**
     * 用户未登录，删除当前旧 token ，并跳转到 login Page
     */
    if (code === BusinessCode.UnLogin) {
      Object.assign(modalCfg, {
        okText: '重新登录',
        onOk: () => {
          useTokenStore.setState(initialToken);
          router.navigate('/login');
        },
        cancelText: null,
      });
      Modal.error(modalCfg);
      return;
    }
    /**
     * 用户未登录，删除当前旧 token ，并跳转到 login Page 重新登录
     */
    if (code === BusinessCode.TokenExpired) {
      Object.assign(modalCfg, {
        okText: '重新登录',
        onOk: () => {
          useTokenStore.setState(initialToken);
          router.navigate('/login');
        },
        cancelText: null,
      });
      Modal.error(modalCfg);
      return;
    }
  };
};

export const showErrorModal = httpErrorModal();
