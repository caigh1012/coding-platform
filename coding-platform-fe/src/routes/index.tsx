import { lazy } from 'react';
import { createBrowserRouter } from 'react-router';
import authMiddleware from '@/core/middlewares/auth.middleware';

const Login = lazy(() => import('./login/login'));
const Layouts = lazy(() => import('../layouts/layouts'));
const Home = lazy(() => import('./home/home'));
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
