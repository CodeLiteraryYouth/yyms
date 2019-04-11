package com.leanin.dao;


import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @ClassName BaseMapper
 * @Description dao基础类
 * @Author 刘壮
 * @Date 2019/4/9 14:15
 * @ModifyDate 2019/4/9 14:15
 * @Version 1.0
 */
public interface BaseMapper<T> extends Mapper<T>, IdsMapper<T>, MySqlMapper<T> {

        }