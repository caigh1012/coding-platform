import React from 'react';
import { createContext } from 'react';

/**
 * 暂时保留
 */

const defaultConfig = {
  data: 'default',
};

export interface IConfig {
  data: string;
}

export const configContext = createContext<IConfig>(defaultConfig);

export default function wrapApp(App: React.ReactNode): React.ReactNode {
  const config = defaultConfig;

  return <configContext.Provider value={config}>{App}</configContext.Provider>;
}
