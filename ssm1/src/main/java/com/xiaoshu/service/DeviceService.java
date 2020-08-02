package com.xiaoshu.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.DeviceMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.Device;
import com.xiaoshu.entity.DeviceVo;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;

@Service
public class DeviceService {

	@Autowired
	DeviceMapper deviceMapper;

	public PageInfo<DeviceVo> findUserPage(DeviceVo deviceVo, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		List<DeviceVo> deviceList = deviceMapper.queryDevice(deviceVo);
		PageInfo<DeviceVo> pageInfo = new PageInfo<DeviceVo>(deviceList);
		return pageInfo;
	}
	
	public Device findByName(String name){
		Device device = new Device();
		device.setDevicename(name);
		return deviceMapper.selectOne(device );
	}
	
	//添加
	public void addDevice(Device device){
		device.setCreatetime(new Date());
		deviceMapper.insert(device);
	}
	
	//修改
	public void updateDevice(Device device){
		device.setCreatetime(new Date());
		deviceMapper.updateByPrimaryKey(device);
	}
	
	
	//删除
	public void deleteDevice(Integer id){
		deviceMapper.deleteByPrimaryKey(id);
	}


}
