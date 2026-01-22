import { create } from 'zustand';

interface BearState {
  bears: number;
  food: string;
  feed: (food: string) => void;
}

export const useBearStore = create<BearState>()((set) => ({
  bears: 2,
  food: 'honey',
  feed: (food) => set(() => ({ food })),
}));
