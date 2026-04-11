/**
 * 用户菜单项
 */
export interface MenuItem {
  /**
   * 菜单创建时间
   */
  readonly created_time: string;
  /**
   * 菜单是否启用，0 禁止 1 启用
   */
  readonly enabled: number;
  /**
   * 菜单 icon
   */
  readonly icon: string;
  /**
   * 表id
   */
  readonly id: number;
  /**
   * 菜单名称
   */
  readonly label: string;
  /**
   * 菜单id
   */
  readonly menu_id: number;
  /**
   * 菜单排序
   */
  readonly order: number;
  /**
   * 父菜单id
   */
  readonly parent_id: number;
  /**
   * 菜单url（前端）
   */
  readonly path: string;
}
