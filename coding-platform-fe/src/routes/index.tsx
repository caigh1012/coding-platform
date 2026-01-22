import { createBrowserRouter } from 'react-router';
import Home from './home/home';
import PageOne from './page-one/page-one';
import PageTwo from './page-two/page-two';

const router = createBrowserRouter(
  [
    {
      path: '/',
      element: <Home></Home>,
      children: [
        {
          path: 'pageone',
          element: <PageOne />,
        },
        {
          path: 'pagetwo',
          element: <PageTwo />,
        },
      ],
    },
    {
      path: '*',
      element: <div>404页面</div>,
    },
  ],
  { basename: baseHref },
);

export default router;
