package com.jk.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.jk.dao.PowerDao;
import com.jk.model.Power;

@Repository("powerDao")
public class PowerDaoImpl implements PowerDao {
	@Autowired
	@Qualifier("sqlMapClient")
	private SqlMapClient sqlMapClient;

	@Override
	public List<Power> powerList(Power power) throws Exception {
		List powerList = this.sqlMapClient.queryForList("Power.powerList",power);
		return powerList;
	}

	@Override
	public List<Power> getPowerRootNode(Power power) throws Exception {
		return this.sqlMapClient.queryForList("Power.getPowerRootNode", power);
	}

	@Override
	public List<Power> isExsitChildrenNode(Power power) throws Exception {
		return this.sqlMapClient.queryForList("Power.isExsitChildrenNode", power);
	}

	@Override
	public void updatePower(Power power) throws Exception {
		this.sqlMapClient.update("Power.updatePower",power);
	}

	@Override
	public void deletePower(Power power) throws Exception {
		this.sqlMapClient.delete("Power.deletePower",power);
	}

	@Override
	public void addPowerNode(Power power) throws Exception {
		 this.sqlMapClient.insert("Power.addPowerNode", power);		
	}

	@Override
	public void insertPower(List<Power> list) throws Exception {
		sqlMapClient.insert("Power.insertMany",list);
	}

	
  
}
