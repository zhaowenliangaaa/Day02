package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.TypeMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;

import aj.org.objectweb.asm.Type;

@Service
public class TypeService {

	@Autowired
	TypeMapper typeMapper;

	public List<com.xiaoshu.entity.Type>findAll(){
		return typeMapper.selectAll();
	}
		
}
