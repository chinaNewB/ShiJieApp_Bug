package com.example.shijieapp.bean;

public class MenuItem {
    private String itemTitle;
    private Integer itemIcon;

    public MenuItem(String itemTitle, Integer itemIcon) {
        this.itemTitle = itemTitle;
        this.itemIcon = itemIcon;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public Integer getItemIcon() {
        return itemIcon;
    }


}