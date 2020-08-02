package com.xiaoshu.controller;

import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.entity.Device;
import com.xiaoshu.entity.DeviceVo;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Role;
import com.xiaoshu.entity.User;
import com.xiaoshu.service.DeviceService;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.RoleService;
import com.xiaoshu.service.TypeService;
import com.xiaoshu.service.UserService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.WriterUtil;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("device")
public class DeviceController extends LogController{
	static Logger logger = Logger.getLogger(DeviceController.class);

	@Autowired
	private DeviceService deviceService;
	
	@Autowired
	private RoleService roleService ;
	
	@Autowired
	private OperationService operationService;
	
	@Autowired
	private TypeService typeService;
	
	
	@RequestMapping("deviceIndex")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		List<Role> roleList = roleService.findRole(new Role());
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		request.setAttribute("tList", typeService.findAll());
		return "device";
	}
	
	@RequestMapping(value="deviceList",method=RequestMethod.POST)
	public void deviceList(DeviceVo deviceVo,HttpServletRequest request,HttpServletResponse response,String offset,String limit) throws Exception{
		try {
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<DeviceVo> deviceList=deviceService.findUserPage(deviceVo, pageNum, pageSize);
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total",deviceList.getTotal() );
			jsonObj.put("rows", deviceList.getList());
	        WriterUtil.write(response,jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户展示错误",e);
			throw e;
		}
	}
	
	
	// 新增或修改
			@RequestMapping("reserveUser")
			public void reserveUser(HttpServletRequest request,Device device,HttpServletResponse response){
				Integer deviceid = device.getDeviceid();
				JSONObject result=new JSONObject();
				try {
					
					Device device2 = deviceService.findByName(device.getDevicename());
					
					if (deviceid != null) {   // userId不为空 说明是修改
						if(device2==null ||(device2 != null && device2.getDeviceid().equals(deviceid))){
							deviceService.updateDevice(device);
							result.put("success", true);
						}else{
							result.put("success", true);
							result.put("errorMsg", "该人员名被使用");
						}
						
					}else {   // 添加
						if(device2==null){  // 没有重复可以添加
							deviceService.addDevice(device);
							result.put("success", true);
						} else {
							result.put("success", true);
							result.put("errorMsg", "该人员名被使用");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("保存用户信息错误",e);
					result.put("success", true);
					result.put("errorMsg", "对不起，操作失败");
				}
				WriterUtil.write(response, result.toString());
			}
		
	
			@RequestMapping("deleteUser")
			public void delUser(HttpServletRequest request,HttpServletResponse response){
				JSONObject result=new JSONObject();
				try {
					String[] ids=request.getParameter("ids").split(",");
					for (String id : ids) {
						deviceService.deleteDevice(Integer.parseInt(id));
					}
					result.put("success", true);
					result.put("delNums", ids.length);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("删除用户信息错误",e);
					result.put("errorMsg", "对不起，删除失败");
				}
				WriterUtil.write(response, result.toString());
			}
	
	
}
