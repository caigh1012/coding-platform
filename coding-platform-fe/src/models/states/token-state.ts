import { create } from 'zustand';
import { devtools, persist } from 'zustand/middleware';

/**
 * 初始化 token
 */
const initialToken = { token: undefined };

export type TokenState = {
  token?: string;
  setToken: (token: string) => void;
  getToken: () => Promise<string | undefined>;
  clearToken: () => void;
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
        getToken: async () => {
          return get().token;
        },
        clearToken: () => set(initialToken),
      }),
      { name: 'token' },
    ),
    { enabled: ENV === 'development', name: 'token store' },
  ),
);
