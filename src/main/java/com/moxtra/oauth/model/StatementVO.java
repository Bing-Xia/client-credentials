package com.moxtra.oauth.model;

import java.util.Map;

public class StatementVO {

	private String month;
	
	private Map<String, Double> list;
	
	private Double total_amount;

	/**
	 * @return the month
	 */
	public String getMonth() {
		return month;
	}

	/**
	 * @param month the month to set
	 */
	public void setMonth(String month) {
		this.month = month;
	}

	/**
	 * @return the list
	 */
	public Map<String, Double> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(Map<String, Double> list) {
		this.list = list;
	}

	/**
	 * @return the total_amount
	 */
	public Double getTotal_amount() {
		return total_amount;
	}

	/**
	 * @param total_amount the total_amount to set
	 */
	public void setTotal_amount(Double total_amount) {
		this.total_amount = total_amount;
	}

	
}
