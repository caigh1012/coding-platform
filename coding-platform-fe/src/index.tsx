import React from 'react';
import { createRoot } from 'react-dom/client';
import App from './app';
import './styles/entry.scss';

const rootEl = document.getElementById('root') as HTMLElement;

createRoot(rootEl).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
);
