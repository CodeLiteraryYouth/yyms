package com.leanin.mapper;

import com.leanin.domain.vo.FormTypeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FormTypeMapper {

	/**
	 * 查询表单列表
	 * @return
	 */
	List<FormTypeVo> findTypeList(@Param("typeStatus") Integer typeStatus);
	/**
	 * 增加表单类型
	 * @param record
	 * @return
	 */
    int addFormType(FormTypeVo record);
    
    /**
     * 注销表单分类
     * @param typeId
     * @return
     */
    int updateTypeStatus(Long typeId);

    /**
     * 查询表单类型数据
     * @param typeName
     * @return
     */
    FormTypeVo findFormType(@Param("typeName") String typeName);

    /**
     * 修改表单类型
     * @param record
     * @return
     */
    int updateFormType(FormTypeVo record);

}