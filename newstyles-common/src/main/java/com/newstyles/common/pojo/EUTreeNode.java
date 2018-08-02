package com.newstyles.common.pojo;


/*
 * EasyUI属性控件节点
 */
public class EUTreeNode {
	
	//节点的id，数据库中对应数据为bigint，所以设置为长整形
	private long id;
	private String text;
	private String state;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	

}
