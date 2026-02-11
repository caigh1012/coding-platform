import { lazy } from 'react';
import { createBrowserRouter } from 'react-router';

import authMiddleware from '@/core/middlewares/auth.middleware';

const Layouts = lazy(() => import('../layouts/layouts'));
const Login = lazy(() => import('./login/login'));
const Home = lazy(() => import('./home/home'));
const FileDownload = lazy(() => import('./file/file-download/file-download'));
const FileUpload = lazy(() => import('./file/file-upload/file-upload'));
const AiAgent = lazy(() => import('./ai/agent/agent'));
const AiToops = lazy(() => import('./ai/tools/tools'));
const UserList = lazy(() => import('./pms-mgr/user-list/user-list'));
const Pms = lazy(() => import('./pms-mgr/pms/pms'));
const LoginLog = lazy(() => import('./login-log/login-log'));
const SystemSetting = lazy(() => import('./system-setting/system-setting'));
const NotFound = lazy(() => import('./not-found/not-found'));

const router = createBrowserRouter(
  [
    {
      path: '/',
      element: <Layouts></Layouts>,
      middleware: [authMiddleware],
      children: [
        {
          index: true,
          element: <Home></Home>,
        },
        {
          path: 'file',
          children: [
            {
              path: 'download',
              element: <FileDownload></FileDownload>,
            },
            {
              path: 'upload',
              element: <FileUpload></FileUpload>,
            },
          ],
        },
        {
          path: 'ai',
          children: [
            {
              path: 'agent',
              element: <AiAgent></AiAgent>,
            },
            {
              path: 'tools',
              element: <AiToops></AiToops>,
            },
          ],
        },
        {
          path: 'pmsmgr',
          children: [
            {
              path: 'userlist',
              element: <UserList></UserList>,
            },
            {
              path: 'pms',
              element: <Pms></Pms>,
            },
          ],
        },
        {
          path: 'loginlog',
          element: <LoginLog></LoginLog>,
        },
        {
          path: 'systemsetting',
          element: <SystemSetting></SystemSetting>,
        },
      ],
    },
    {
      path: 'login',
      element: <Login></Login>,
    },
    {
      path: '*',
      element: <NotFound></NotFound>,
    },
  ],
  { basename: baseHref },
);

export default router;
