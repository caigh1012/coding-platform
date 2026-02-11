import { ItemType } from 'antd/es/menu/interface';

import { MenuItem } from '@/interfaces/user/user-menu.interface';
import { SafeAny } from '@/helpers/safe-any';

/**
 * 将 Menu List 结构转换成 MenuTree
 * @param menuList
 * @returns Array<ItemType>
 */
export function menuConvertTree(menuList: Array<MenuItem>, IconMap: SafeAny): Array<ItemType> {
  const nodeMap = new Map();
  const tree: Array<ItemType> = [];

  menuList.forEach((item) => {
    nodeMap.set(item.menu_id, { ...item, children: null, icon: IconMap[item.icon] ?? null, key: item.menu_id });
  });

  menuList.forEach((item) => {
    const node = nodeMap.get(item.menu_id);

    // 根节点
    if (item.parent_id === 0) {
      tree.push(node);
    } else {
      const parent = nodeMap.get(item.parent_id);
      if (parent) {
        if (parent.children === null) {
          parent.children = [];
        }
        parent.children.push(node);
      }
    }
  });

  return tree;
}
