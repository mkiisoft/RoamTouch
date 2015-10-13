package com.roamtouch.menuserver.entities;

import android.view.animation.Animation;

import com.roamtouch.menuserver.shared.Constans;

public class DiningTables {
	
	private int _id;
	private String description;
	private int dining_table_status_id;
	private String created_at;
	private String updated_at;
	private String dining_table_status_description;
	private int status_id;
	private String status;	
	private int blink;	
	
	private boolean flash;
	private int flashId;
	
	public int getFlashId() {
		return flashId;
	}
	public void setFlashId(int flashId) {
		this.flashId = flashId;
	}
	public boolean isFlash() {
		return flash;
	}
	public void setFlash(boolean flash) {
		this.flash = flash;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDining_table_status_description() {
		return dining_table_status_description;
	}
	public void setDining_table_status_description(
			String dining_table_status_description) {	
		this.dining_table_status_description = dining_table_status_description;
	}
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getDining_table_status_id() {
		return dining_table_status_id;
	}
	public void setDining_table_status_id(int dining_table_status_id) {
		this.dining_table_status_id = dining_table_status_id;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}	
	
	public int getStatusIdByDescription(String description){
		
		int d=0;
		
		if (description.equals("abierta")){
			d=Constans.DINING_TABLE_STATUS_OPEN;
		} else if (description.equals("esperando realizar pedido")){
			d=Constans.DINING_TABLE_STATUS_WAITING_FOR_ORDER;
		} else if (description.equals("pedido realizado")){
			d=Constans.DINING_TABLE_STATUS_ORDERED;
		} else if (description.equals("pedido entregado")){
			d=Constans.DINING_TABLE_STATUS_ORDER_DELIVERED;
		} else if (description.equals("pedido de cierre de cuenta")){
			d=Constans.DINING_TABLE_STATUS_ORDER_CLOSE_REQUESTED;
		} else if (description.equals("cierre de cuenta entregado")){
			d=Constans.DINING_TABLE_STATUS_ORDER_CLOSE_REQUEST_DELIVERED;
		} else if (description.equals("pago realizado")){
			d=Constans.DINING_TABLE_STATUS_ORDER_PAYED;
		} else if (description.equals("cobro realizado")){
			d=Constans.DINING_TABLE_STATUS_ORDER_PAYMENT_ADDRESSED;
		} else if (description.equals("cerrada")){
			d=Constans.DINING_TABLE_STATUS_CLOSE;
		}		
		return d; 
	}
	
	public int getBlink() {
		return blink;
	}
	public void setBlink(int blink) {
		this.blink = blink;
	}
	
	

	
}


