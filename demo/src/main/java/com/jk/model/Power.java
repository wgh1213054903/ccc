package com.jk.model;

import java.io.Serializable;
/**
 * 
 * <pre>项目名称：实体类  power   
 * 类名称：Power    
 * 类描述：    
 * 创建人：王广贺 wgh_java@126.com    
 * 创建时间：2017年9月21日 上午8:35:56    
 * 修改人：王广贺 wgh_java@126.com  
 * 修改时间：2017年9月21日 上午8:35:56    
 * 修改备注：       
 * @version </pre>
 */

public class Power implements Serializable {

	

	private static final long serialVersionUID = -3044811173915560959L;
	//id
	private Integer id;
	//节点名称
	private String name;
	//图片
	private String icon;
	//url地址
	private String url;
	//target展示方式
	private String target;
	//类型
	private String type;
	//pid父节点id
	private Integer pid;
	//open是否打开
	private String open = "true";
	//isParent是否是父节点
	private String isParent = "false";
//-------------------get set方法-----------------------------
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getIsParent() {
		return isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}
//-------------------- hashcode equals方法---------------------------
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Power other = (Power) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
//--------------------------toString方法-------------------------------
	@Override
	public String toString() {
		return "Power [id=" + id + ", name=" + name + ", icon=" + icon
				+ ", url=" + url + ", target=" + target + ", type=" + type
				+ ", pid=" + pid + ", open=" + open + ", isParent=" + isParent
				+ "]";
	}
	
}
