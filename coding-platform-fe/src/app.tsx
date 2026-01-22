import React from 'react';
import router from './routes';
import { RouterProvider } from 'react-router';
import './styles/entry.scss';

const App: React.FC = () => {
  return <RouterProvider router={router} />;
};

export default App;
