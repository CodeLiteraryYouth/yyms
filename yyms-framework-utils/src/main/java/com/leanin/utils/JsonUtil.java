package com.leanin.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import com.google.gson.*;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * Json工具类
 * <p>
 * 主要支持
 * <li>
 * json2obj, json2bean, json2list, json2map, json2xml</li>
 * <li>
 * obj2json, bean2json, list2json, map2json, obj2xml</li>
 * <li>
 * xml2json, xml2obj</li>
 * <li>
 * map2pojo, convert</li>
 * Company:cnpay
 * </p>
 * 
 * @author Administrator
 */
public class JsonUtil {
	
	
	private static final ObjectMapper mapper = new ObjectMapper();
	
	static {
		// 设置输出时包含属性的风格
		mapper.setSerializationInclusion(Include.NON_DEFAULT);
		// 忽略空对象
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		// 关闭时间戳输出，此时是ISO格式
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		/**
		 * 日期转换请在实体或者POJO对应字段加上：@JsonSerialize，如下所示，如有其它格式，自行定义，参照JsonDate或者JsonDateTime
		 * @JsonSerialize(using=JsonDate.class)
		 * private Date loanStartDate;
		 * 或者
		 * @JsonSerialize(using=JsonDateTime.class)
		 * private Date loanStartDate;
		 */
		// 设置默认日期格式器
		// 在解析短日期时失败（2015-2-14）
		// mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		// 空字符串时，解析成null
		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		// 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		// 禁止使用int代表Enum的order()来反序列化Enum,非常危险
		mapper.disable(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS);
	}
	
	/**
	 * bean to json
	 *
	 */
	public static String beanToJson(Object obj, boolean serializeNullValue) {
		
		if (obj == null) {
			return null;
		}
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = serializeNullValue ? gsonBuilder.serializeNulls().registerTypeAdapter(Date.class, new DateSerializerUtils()).setDateFormat(DateFormat.LONG).create()
				: gsonBuilder.registerTypeAdapter(Date.class, new DateSerializerUtils()).setDateFormat(DateFormat.LONG).create();
		String json = gson.toJson(obj);
		return json;
	}

	/**
	 * 日期解序列实用工具类
	 */
	static class DateSerializerUtils implements JsonSerializer<Date> {
		
		
		@Override
		public JsonElement serialize(Date date, Type type, JsonSerializationContext content) {
			
			return new JsonPrimitive(date.getTime());
		}
		
	}
	
	/**
	 * 将对象转换为json格式的字符串
	 * 
	 * @param obj
	 * @return String
	 */
	public static String obj2json(Object obj) {
		String json = "";
		try {
			json = mapper.writeValueAsString(obj);
		}
		catch (IOException e) {
			return "";
		}
		return json;
	}
	
	/**
	 * 将json字符串转换为对应的java对象
	 * 
	 * @param json
	 * @param clazz
	 * @return T
	 */
	public static <T> T json2Obj(String json, Class<T> clazz) {
		try {
			return mapper.readValue(json, clazz);
		}
		catch (Exception e) {
			throw new RuntimeException("解析json错误");
		}
	}
	
	/**
	 * 将字符串转换为列表对象
	 * 
	 * @param json
	 * @return List<Map<String, Object>>
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> json2List(String json) {
		try {
			return mapper.readValue(json, List.class);
		}
		catch (Exception e) {
			throw new RuntimeException("解析json错误");
		}
	}
	
	/**
	 * json string convert to map
	 */
	@SuppressWarnings("unchecked")
	public static <T> Map<String, Object> json2map(String jsonStr) {
		try {
			return mapper.readValue(jsonStr, Map.class);
		}
		catch (IOException e) {
			throw new RuntimeException("解析json错误");
		}
	}
	
	/**
	 * json string convert to map with javaBean
	 */
	public static <T> Map<String, T> json2map(String jsonStr, Class<T> clazz) {
		Map<String, Map<String, Object>> map = null;
		try {
			map = mapper.readValue(jsonStr, new TypeReference<Map<String, T>>() {});
		}
		catch (IOException e) {
			throw new RuntimeException("解析json错误");
		}
		Map<String, T> result = new HashMap<String, T>();
		for (Entry<String, Map<String, Object>> entry : map.entrySet()) {
			result.put(entry.getKey(), map2pojo(entry.getValue(), clazz));
		}
		return result;
	}
	
	/**
	 * json array string convert to list with javaBean
	 */
	public static <T> List<T> json2list(String jsonArrayStr, Class<T> clazz) {
		List<Map<String, Object>> list = null;
		try {
			list = mapper.readValue(jsonArrayStr, new TypeReference<List<T>>() {});
		}
		catch (Exception e) {
			throw new RuntimeException("解析json错误");
		}
		List<T> result = new ArrayList<>();
		for (Map<String, Object> map : list) {
			result.add(map2pojo(map, clazz));
		}
		return result;
	}
	
	/**
	 * map convert to javaBean
	 */
	public static <T> T map2pojo(Map<String, Object> map, Class<T> clazz) {
		return mapper.convertValue(map, clazz);
	}
	
	/**
	 * string2JsonNode
	 * 
	 * @param jsonString
	 * @return JsonNode
	 */
	public static JsonNode string2JsonNode(String jsonString) {
		JsonNode jsonNode = null;
		// since 2.1 use mapper.getFactory() instead mapper.getJsonFactory();
		JsonFactory factory = mapper.getFactory();
		JsonParser jp = null;
		try {
			jp = factory.createParser(jsonString);
			jsonNode = mapper.readTree(jp);
		}
		catch (JsonParseException e) {
			throw new RuntimeException("解析json错误");
		}
		catch (IOException e) {
			throw new RuntimeException("解析json错误");
		}
		return jsonNode;
	}
	
	/**
	 * 将java对象转换为json节点对象
	 * 
	 * @param bean
	 * @return JsonNode
	 */
	public static JsonNode bean2Json(Object bean) {
		try {
			if (bean == null) {
				return null;
			}
			TokenBuffer buffer = new TokenBuffer(mapper, true);
			// TODO if error set false
			mapper.writeValue(buffer, bean);
			JsonNode node = mapper.readTree(buffer.asParser());
			return node;
		}
		catch (Exception e) {
			throw new RuntimeException("对象转换错误");
		}
	}
	
	/**
	 * 创建一个节点数组对象
	 * 
	 * @return ArrayNode
	 */
	public static ArrayNode newArrayNode() {
		return mapper.createArrayNode();
	}
	
	/**
	 * 将json字符串转换为json节点数组
	 * 
	 * @param json
	 * @return ArrayNode
	 */
	public static ArrayNode toArrayNode(String json) {
		try {
			return mapper.readValue(json, ArrayNode.class);
		}
		catch (Exception e) {
			throw new RuntimeException("解析json错误");
		}
	}
	
	/**
	 * 判断字符串是否是JSON对象字符串
	 * 
	 * @Date:2014-08-26
	 * @author wangzh
	 * @param str
	 * @return
	 * @Description:
	 * @return boolean
	 */
	public static boolean isJsonObjectString(String str) {
		return str != null && str.matches("^\\{.*\\}$");
	}
	
	/**
	 * 判断字符串是否是JSON数组字符串
	 * 
	 * @Date:2014-08-26
	 * @author wangzh
	 * @param str
	 * @return
	 * @Description:
	 * @return boolean
	 */
	public static boolean isJsonArrayString(String str) {
		return str != null && str.matches("^\\[.*\\]$");
	}
	
	/**
	 * JSON字符串递归转成JAVA Map对象
	 * 
	 * @Date:2014-08-26
	 * @author wangzh
	 * @param json
	 * @return
	 * @Description:
	 * @return Map<String,Object>
	 */
	public static Map<String, Object> parseMap(String json) {
		if (!isJsonObjectString(json)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		JsonNode jsonNode = string2JsonNode(json);
		Iterator<String> keys = jsonNode.fieldNames();
		while (keys.hasNext()) {
			String key = keys.next();
			JsonNode value = jsonNode.get(key);
			if (value == null) {
				map.put(key, null);
				continue;
			}
			String _value = obj2json(value);
			if (isJsonObjectString(_value)) {
				map.put(key, parseMap(_value));
			}
			else if (isJsonArrayString(_value)) {
				map.put(key, parseList(_value));
			}
			else {
				map.put(key, value.asText());
			}
		}
		return map;
	}
	
	/**
	 * JSON字符串递归转成JAVA List对象
	 * 
	 * @Date:2014-08-26
	 * @author wangzh
	 * @param jsonstr
	 * @return
	 * @Description:
	 * @return List<Object>
	 */
	public static List<Object> parseList(String jsonstr) {
		if (!isJsonArrayString(jsonstr)) {
			return null;
		}
		ArrayNode array = toArrayNode(jsonstr);
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < array.size(); i++) {
			JsonNode value = array.get(i);
			if (value == null) {
				list.add(null);
				continue;
			}
			String _value = obj2json(value);
			if (isJsonArrayString(_value)) {
				list.add(parseList(_value));
			}
			else if (isJsonObjectString(_value)) {
				list.add(parseMap(_value));
			}
			else {
				list.add(value.asText());
			}
		}
		return list;
	}
	
	
	public static <T> boolean putList(List<T> list) throws Exception {
		String jsonVal = mapper.writeValueAsString(list);
		return jsonVal == null ? false : true;
	}
	
	public static <T> List<T> getList(String jsonVal, Class<T> clazz) {
		TypeFactory t = TypeFactory.defaultInstance();
		List<T> list = null;
		try {
			/**
			 * 日期转换请在实体或者POJO对应字段加上：@JsonSerialize，如下所示，如有其它格式，自行定义，参照JsonDate或者JsonDateTime
			 * @JsonSerialize(using=JsonDate.class)
			 * private Date loanStartDate;
			 * 或者
			 * @JsonSerialize(using=JsonDateTime.class)
			 * private Date loanStartDate;
			 */
			// 指定容器结构和类型（这里是ArrayList和clazz）
			list = mapper.readValue(jsonVal, t.constructCollectionType(ArrayList.class, clazz));
		}
		catch (IOException e) {
			e.printStackTrace();
			try {
				// 如果T确定的情况下可以使用下面的方法：
				if (null == list)
					list = mapper.readValue(jsonVal, new TypeReference<List<T>>() {});
			}
			catch (IOException e3) {
				e3.printStackTrace();
				throw new RuntimeException("解析json错误");
			}
		}
		return list;
	}
	
	/**
	 * beantoMap
	 * 
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> beantoMap(Object obj) {
		
		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				
				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);
					
					map.put(key, value);
				}
				
			}
		}
		catch (Exception e) {
			System.out.println("transBean2Map Error " + e);
		}
		
		return map;
		
	}
	
}
