package com.leanin.mapper;

		import java.util.List;

		import org.apache.ibatis.annotations.Mapper;
		import org.apache.ibatis.annotations.Param;
		import org.springframework.web.bind.annotation.RequestParam;

		import com.leanin.domain.CallBackConfig;
@Mapper
public interface CallBackConfigMapper {

	/**
	 * 根据投诉类型查询所有投诉表扬的配置
	 * @return
	 */
	List<CallBackConfig> findConfigList(@Param("configType") Integer configType);
	/**
	 * 更新投诉表扬状态配置状态
	 * @param configNum
	 * @return
	 */
	int updateConfigStatus(@Param("configNum") String configNum, @Param("status") int status);

	/**
	 * 增加投诉表扬配置
	 * @param record
	 * @return
	 */
	int addConfig(CallBackConfig record);

	/**
	 * 根据ID查询投诉表扬信息
	 * @param configNum
	 * @return
	 */
	CallBackConfig findConfigById(String configNum);

	/**
	 * 修改投诉表扬配置信息
	 * @param record
	 * @return
	 */
	int updateConfig(CallBackConfig record);

	List<CallBackConfig> findDealNameByType(@Param("type") Integer type,@Param("status") Integer status);
}