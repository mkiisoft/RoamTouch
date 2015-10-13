package com.roamtouch.menuserver.entities;

public class DiningTableDetail {

	private int order_id;
	private String order_date;
	private int dining_table_id;
	private String order_description = "";
	private int order_status_id;
	private Double order_total;
	private int dining_table_status_id;
	private int user_id;
	private String user_last_name;
	private String user_first_name;
	
	private int order_detail_id;
	private int menu_item_categories_id;
	private int menu_item_id;
	private String menu_item_name;
	private int addon_id;
	private String addon_description;
	private int quantity;
	private double price;
	private double subtotal;

	private int menu_item_category_id;
	
	private boolean isHearder1;
	private boolean isHearder2;
	
	
	public int getMenu_item_categories_id() {
		return menu_item_categories_id;
	}
	public void setMenu_item_categories_id(int menu_item_categories_id) {
		this.menu_item_categories_id = menu_item_categories_id;
	}
	public String getAddon_description() {
		return addon_description;
	}
	public void setAddon_description(String addon_description) {
		this.addon_description = addon_description;
	}
	public int getAddon_id() {
		return addon_id;
	}
	public void setAddon_id(int addon_id) {
		this.addon_id = addon_id;
	}
	public boolean isHearder1() {
		return isHearder1;
	}
	public void setHearder1(boolean isHearder1) {
		this.isHearder1 = isHearder1;
	}
	public int getOrder_id() {
		return order_id;
	}
	public boolean isHearder2() {
		return isHearder2;
	}
	public void setHearder2(boolean isHearder2) {
		this.isHearder2 = isHearder2;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public String getOrder_date() {
		return order_date;
	}
	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}
	public int getDining_table_id() {
		return dining_table_id;
	}
	public void setDining_table_id(int dining_table_id) {
		this.dining_table_id = dining_table_id;
	}
	public String getOrder_description() {
		return order_description;
	}
	public void setOrder_description(String order_description) {
		this.order_description = order_description;
	}
	public int getOrder_status_id() {
		return order_status_id;
	}
	public void setOrder_status_id(int order_status_id) {
		this.order_status_id = order_status_id;
	}
	public Double getOrder_total() {
		return order_total;
	}
	public void setOrder_total(Double order_total) {
		this.order_total = order_total;
	}
	public int getDining_table_status_id() {
		return dining_table_status_id;
	}
	public void setDining_table_status_id(int dining_table_status_id) {
		this.dining_table_status_id = dining_table_status_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_last_name() {
		return user_last_name;
	}
	public void setUser_last_name(String user_last_name) {
		this.user_last_name = user_last_name;
	}
	public String getUser_first_name() {
		return user_first_name;
	}
	public void setUser_first_name(String user_first_name) {
		this.user_first_name = user_first_name;
	}
	public int getOrder_detail_id() {
		return order_detail_id;
	}
	public void setOrder_detail_id(int order_detail_id) {
		this.order_detail_id = order_detail_id;
	}
	public int getMenu_item_id() {
		return menu_item_id;
	}
	public void setMenu_item_id(int menu_item_id) {
		this.menu_item_id = menu_item_id;
	}
	public String getMenu_item_name() {
		return menu_item_name;
	}
	public void setMenu_item_name(String menu_item_name) {
		this.menu_item_name = menu_item_name;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public int getMenu_item_category_id() {
		return menu_item_category_id;
	}
	public void setMenu_item_category_id(int menu_item_category_id) {
		this.menu_item_category_id = menu_item_category_id;
	}
	
	
}
