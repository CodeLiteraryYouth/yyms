package com.leanin.testmodel;

public class MapUtils {
//    /**
//     * 将map转换为object，转换全部属性（按照key和对象属性之间的关系进行转换）
//     * @param map
//     * @param t
//     * @param <T>
//     * @return
//     */
//    public static <T> T  mapToObject(Map<String, Object> map, T t) {
//        return mapToObject(map, t,null);
//    }
//
//    /**
//     * 将map转换为object，排除指定key
//     * @param map
//     * @param t
//     * @param excludeKeys
//     * @param <T>
//     * @return
//     */
//    public static <T> T  mapToObject(Map<String, Object> map, T t, String[] excludeKeys) {
//        Class beanClass = t.getClass();
//        String[] declaredFieldsName = getDeclaredFieldsName(beanClass);
//        if (ArrayUtils.isNotEmpty(excludeKeys)) {
////            MapUtil.removeEntries(map, excludeKeys);
//        }
//        for (Object k : map.keySet()) {
//            Object v = map.get(k);
//            if (ArrayUtils.contains(declaredFieldsName, k.toString())) {
//                try {
//                    Field field = beanClass.getDeclaredField(k.toString());
//                    field.setAccessible(true);
//                    field.set(t, v);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return t;
//    }
//
//    /**
//     * 获取转换后的对象的所有属性名称，以字符串数组形式返回
//     * @param beanClass
//     * @return
//     */
//    public static String[] getDeclaredFieldsName(Class beanClass) {
//        Field[] fields = beanClass.getDeclaredFields();
//        int size = fields.length;
//        String[] fieldsName = new String[size];
//        for (int i = 0; i < size; i++) {
//            fieldsName[i] = fields[i].getName();
//        }
//        return fieldsName;
//    }
}
