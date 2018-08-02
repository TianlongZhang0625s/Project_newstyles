package com.newstyles.common.pojo;

import java.util.List;

public class EUDataGridResult {

	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	private long total;
	private List<?> rows;    //?泛型可以加任意类型
	
}
