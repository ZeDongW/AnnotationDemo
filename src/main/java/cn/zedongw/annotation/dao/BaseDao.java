package cn.zedongw.annotation.dao;
import cn.zedongw.annotation.myannotation.Column;
import cn.zedongw.annotation.myannotation.Id;
import cn.zedongw.annotation.myannotation.Table;
import cn.zedongw.annotation.util.JDBCUtils;
import cn.zedongw.annotation.util.LogUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName BaseDao
 * @Description: 查询公共类
 * @Author ZeDongW
 * @Date 2019/11/24/0024 12:04
 * @Version 1.0
 * @Modified By:
 * @Modified Time:
 **/
public class BaseDao<T> {

    private Class<T> clazz;  //当前运行类的类型

    private String tableName; //表名

    private String id_primary; //主键

    private Field[] fields; //类属性

    private Logger logger;

    public BaseDao() {

        logger = LogUtils.getLogger();

        Type type = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        clazz = (Class<T>) actualTypeArguments[0];  //获取类的类型

        //根据Table注解获取表对象
        Table table = clazz.getAnnotation(Table.class);
        //根据表对象获取表名
        tableName = table.tablename();

        logger.info("表名:{}", tableName);

        //获取主键
        fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Id id = field.getAnnotation(Id.class);
            if (id != null) {
                Column column = field.getAnnotation(Column.class);
                id_primary = column.columnName();
                break;
            }
        }

        logger.info("主键：{}", id_primary);
    }

    /** @MethodName: findById
      * @Description: 根据ID查找实体类
      * @Param: [id]
      * @Return: T
      * @Author: ZeDongW
      * @Date: 2019/11/24/0024 16:56
      **/
    public T findById(int id){
        try {
            String sql = "select * from " + tableName + " where " + id_primary + " = ?";
            logger.info("根据ID查找SQL：{}", sql);
            return JDBCUtils.getQueryRunner().query(sql, new ResultSetHandler<T> () {
                @Override
                public T handle(ResultSet rs) throws SQLException {
                    try {
                        if (rs.next()){
                            T t = clazz.newInstance();
                            for (Field field : fields) {
                                field.setAccessible(true);
                                //获取属性名
                                String fieldName = field.getName();

                                //获取属性上的注解
                                Column annotation = field.getAnnotation(Column.class);

                                //获取字段名
                                String columnName = annotation.columnName();

                                //获取字段值
                                Object columnValue = rs.getObject(columnName);

                                //赋值
                                BeanUtils.copyProperty(t, fieldName, columnValue);
                            }
                            return t;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** @MethodName: findAll
      * @Description: 查询所有
      * @Param: []
      * @Return: java.util.List<T>
      * @Author: ZeDongW
      * @Date: 2019/11/24/0024 23:31
      **/
    public List<T> findAll(){
        try {
            String sql = "select * from " + tableName;

            logger.info("全部查找SQL：{}", sql);

            return JDBCUtils.getQueryRunner().query(sql, new ResultSetHandler<List<T> > () {

                @Override
                public List<T> handle(ResultSet rs) throws SQLException {
                    try {
                        List<T> list = new ArrayList<>();
                        while (rs.next()){
                            T t = clazz.newInstance();
                            for (Field field : fields) {
                                field.setAccessible(true);
                                //获取属性名
                                String fieldName = field.getName();

                                //获取属性上的注解
                                Column annotation = field.getAnnotation(Column.class);

                                //获取字段名
                                String columnName = annotation.columnName();

                                //获取字段值
                                Object columnValue = rs.getObject(columnName);

                                //赋值
                                BeanUtils.copyProperty(t, fieldName, columnValue);
                            }
                            list.add(t);
                        }
                        return list;
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
