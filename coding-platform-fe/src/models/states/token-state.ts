import { create } from 'zustand';
import { devtools, persist } from 'zustand/middleware';

/**
 * 初始化 token
 */
const initialToken = { token: undefined };

export type TokenState = {
  token?: string;
  setToken: (token: string) => void;
  clear: () => void;
};

export const useTokenStore = create<TokenState>()(
  devtools(
    persist(
      (set) => ({
        ...initialToken,
        setToken: (token) =>
          set(() => ({
            token,
          })),
        clear: () => set(initialToken),
      }),
      { name: 'token' },
    ),
    { enabled: ENV === 'development', name: 'token store' },
  ),
);
