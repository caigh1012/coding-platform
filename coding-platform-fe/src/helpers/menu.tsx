import {
  AppstoreOutlined,
  FileOutlined,
  FileSearchOutlined,
  SafetyOutlined,
  SettingOutlined,
  StarOutlined,
} from '@ant-design/icons';

import { menuConvertTree } from '@/utils/menu';
import { MenuItem } from '@/interfaces/user/user-menu.interface';

const IconMap = {
  AppstoreOutlined: <AppstoreOutlined />,
  FileSearchOutlined: <FileSearchOutlined />,
  StarOutlined: <StarOutlined />,
  SettingOutlined: <SettingOutlined />,
  SafetyOutlined: <SafetyOutlined />,
  FileOutlined: <FileOutlined />,
};

export function getMenuTree(menuList: Array<MenuItem>) {
  return menuConvertTree(menuList, IconMap);
}
