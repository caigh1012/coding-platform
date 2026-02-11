package cn.caigh.coding_platform.pojo.entity;

import java.util.Date;

public class Menu {
  private Integer id;
  private Integer menu_id;
  private String label;
  private String path;
  private String icon;
  private Integer parent_id;
  private Integer Order;
  private Integer enabled;
  private Date created_time;

  public Menu() {
  }

  @Override
  public String toString() {
    return "Menu{" +
        "id=" + id +
        ", menu_id=" + menu_id +
        ", label='" + label + '\'' +
        ", path='" + path + '\'' +
        ", icon='" + icon + '\'' +
        ", parent_id=" + parent_id +
        ", Order=" + Order +
        ", enabled=" + enabled +
        ", created_time=" + created_time +
        '}';
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getMenu_id() {
    return menu_id;
  }

  public void setMenu_id(Integer menu_id) {
    this.menu_id = menu_id;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public Integer getParent_id() {
    return parent_id;
  }

  public void setParent_id(Integer parent_id) {
    this.parent_id = parent_id;
  }

  public Integer getOrder() {
    return Order;
  }

  public void setOrder(Integer order) {
    Order = order;
  }

  public Integer getEnabled() {
    return enabled;
  }

  public void setEnabled(Integer enabled) {
    this.enabled = enabled;
  }

  public Date getCreated_time() {
    return created_time;
  }

  public void setCreated_time(Date created_time) {
    this.created_time = created_time;
  }
}
