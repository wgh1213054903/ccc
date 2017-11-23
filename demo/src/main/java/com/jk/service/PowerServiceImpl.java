package com.jk.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jk.dao.PowerDao;
import com.jk.model.Power;

@Service("powerService")
public class PowerServiceImpl implements PowerService {
	@Autowired
	private PowerDao powerDao;
	@Override
	public List<Power> powerList(Power power) throws Exception {
		return powerDao.powerList(power);
	}
	@Override
	public List<Power> getPowerRootNode(Power power) throws Exception {
		List<Power> powerRootNode = new ArrayList<Power>();
		if (null != power.getId() && !"".equals(power.getId())) {
			powerRootNode = powerDao.isExsitChildrenNode(power);
		} else {
			powerRootNode = powerDao.getPowerRootNode(power);
		}
		List<Power> powerList = new ArrayList<Power>();
		for (Power power2 : powerRootNode) {
			if (isExsitChildrenNode(power2)) {
				power2.setIsParent("true");
			}
			powerList.add(power2);
		}
		return powerList;
	}
	public boolean isExsitChildrenNode(Power power) throws Exception {
		boolean flag = false;
		List<Power> exsitChildrenNode = powerDao.isExsitChildrenNode(power);
		if (CollectionUtils.isNotEmpty(exsitChildrenNode)) {
			flag = true;
		}
		return flag;
	}
	@Override
	public void updatePower(Power power) throws Exception {
		powerDao.updatePower(power);
	}
	@Override
	public void deletePower(Power power) throws Exception {
		powerDao.deletePower(power);
	}
	@Override
	public void addPowerNode(Power power) throws Exception {
		powerDao.addPowerNode(power);
	}
	@Override
	public void insertPower(List<Power> list) throws Exception {
		powerDao.insertPower(list);
	}
	
	
}
