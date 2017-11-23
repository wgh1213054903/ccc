package com.jk.service;

import java.util.List;

import com.jk.model.Power;

public interface PowerService {
	public List<Power> powerList(Power power) throws Exception;
	
	public List<Power> getPowerRootNode(Power power) throws Exception;
	
	public void updatePower(Power power) throws Exception;
	
	public void deletePower(Power power) throws Exception;
	
	public void addPowerNode(Power power) throws Exception;
	
	public void insertPower(List<Power> list) throws Exception;

	

	
      
}
