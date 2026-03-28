import { create } from 'zustand';
import { devtools, persist } from 'zustand/middleware';

/**
 * 初始化 token
 */
export const initialToken = { token: undefined };

export type TokenState = {
  token?: string;
  setToken: (token: string) => void;
  clearToken: () => Promise<string | undefined>;
};

export const useTokenStore = create<TokenState>()(
  devtools(
    persist(
      (set, get) => ({
        ...initialToken,
        setToken: (token) =>
          set(() => ({
            token,
          })),
        clearToken: async () => {
          set(initialToken);
          return get().token;
        },
      }),
      { name: 'token' },
    ),
    { enabled: Env === 'development', name: 'token store' },
  ),
);
