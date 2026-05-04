import { useEffect, useRef, useState } from 'react';

/**
 * 用于div出现在视图中
 * 显示的是原理
 * 实际的实现可以通过 ahooks 的 useInViewport 或 react-use 的 useIntersection
 */
export function useInViewOnce(options = { threshold: 0.1 }) {
  const ref = useRef(null);
  const [inView, setInView] = useState(false);

  useEffect(() => {
    const el = ref.current;
    if (!el || inView) return;

    const observer = new IntersectionObserver(([entry]) => {
      if (entry.isIntersecting) {
        setInView(true);
        observer.disconnect();
      }
    }, options);

    observer.observe(el);
    return () => observer.disconnect();
  }, [inView, options]);

  return [ref, inView];
}
