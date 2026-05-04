/* eslint-disable react-hooks/static-components */
import loadable, { DefaultComponent } from '@loadable/component';
import { useInViewport } from 'ahooks';
import { Props } from 'ahooks/lib/useControllableValue';
import { useRef } from 'react';

interface LazyProps {
  load: (props: Props) => Promise<DefaultComponent<Props>>;
  fallback: string | null;
}

const LazyComponent: React.FC<LazyProps> = (props: LazyProps) => {
  const { load, fallback } = props;
  const ref = useRef<HTMLDivElement | null>(null);
  const [inViewport] = useInViewport(ref);

  const Component = inViewport ? loadable(load) : null;

  return <div ref={ref}>{Component ? <Component /> : <div>{fallback}</div>}</div>;
};

export default LazyComponent;
