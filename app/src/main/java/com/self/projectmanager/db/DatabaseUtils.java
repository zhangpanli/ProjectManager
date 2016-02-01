package com.self.projectmanager.db;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.self.projectmanager.annotation.Column;
import com.self.projectmanager.annotation.Table;
import com.self.projectmanager.dao.SimpleDao;
import com.self.projectmanager.exception.ToastException;

import android.content.Context;
import android.database.Cursor;

public class DatabaseUtils {

    private static Map<Class<?>, TableMapping> tableMap = new HashMap<>();

    private static SqliteHelper helper;

    private static Context context;

    public static void setContext(Context context) {
        DatabaseUtils.context = context;

        helper = new SqliteHelper(context, "database.db", null, 1);

        SimpleDao.setDb(helper.getWritableDatabase());
    }

    /**
     * 获取SqliteHelper
     *
     * @return
     */
    public static SqliteHelper getSqliteHelper() {
        if (helper == null) {
            helper = new SqliteHelper(context, "database.db", null, 1);
        }
        return helper;
    }

    /**
     * 通过对象生成insert语句
     *
     * @param obj
     * @return
     */
    public static String getInsertSQL(Object obj) {
        Class<?> clazz = obj.getClass();

        TableMapping mapping = getStructure(clazz);

        StringBuilder columnStr = new StringBuilder();
        StringBuilder valueStr = new StringBuilder();
        columnStr.append("insert into ").append(mapping.tableName).append("(");
        valueStr.append("values(");

        List<ColumnMapping> columns = mapping.columns;

        columnStr.append(columns.get(0).cloumnName);
        valueStr.append(getStringValue(getValue(obj, columns.get(0).getMethod), columns.get(0).columnType));

        for (int i = 1; i < columns.size(); i++) {
            ColumnMapping column = columns.get(i);
            Method m = column.getMethod;
            Object val = getValue(obj, m);

            columnStr.append(", ").append(column.cloumnName);
            valueStr.append(", ").append(getStringValue(val, column.columnType));
        }

        columnStr.append(")");
        valueStr.append(")");

        return columnStr.append(valueStr).toString();
    }

    /**
     * 通过Cursor生成对象
     *
     * @param cursor
     * @param clazz
     * @return
     */
    public static <T> T getObject(Cursor cursor, Class<T> clazz) {
        T t;
        try {
            t = clazz.newInstance();
        } catch (Exception e) {
            throw new ToastException("创建对象异常", e);
        }

        TableMapping mapping = getStructure(clazz);

        for (ColumnMapping column : mapping.columns) {
            int i = cursor.getColumnIndex(column.cloumnName);

            if (i < 0) {
                continue;
            }

            Object val = null;
            Class<?> fieldType = column.getMethod.getReturnType();
            if (Integer.class.equals(fieldType)) {
                val = cursor.getInt(i);
            } else if (Long.class.equals(fieldType)) {
                val = cursor.getLong(i);
            } else if (BigDecimal.class.equals(fieldType)) {
                val = new BigDecimal(cursor.getString(i));
            } else if (String.class.equals(fieldType)) {
                val = cursor.getString(i);
            } else if (Date.class.equals(fieldType)) {
                val = new Date(cursor.getLong(i));
            } else {
                throw new ToastException("未实现类型:" + fieldType);
            }

            setValue(t, column.setMethod, val);
        }

        return t;
    }

    /**
     * 获取表结构信息
     *
     * @param clazz
     * @return
     */
    private static TableMapping getStructure(Class<?> clazz) {
        TableMapping mapping = tableMap.get(clazz);

        if (mapping != null) {
            return mapping;
        }

        Table table = clazz.getAnnotation(Table.class);
        if (table == null) {
            throw new ToastException("类型错误");
        }

        mapping = new TableMapping(table.name());

        Method[] methods = clazz.getDeclaredMethods();

        for (int i = 0; i < methods.length; i++) {
            Column column = methods[i].getAnnotation(Column.class);
            if (column == null) {
                continue;
            }

            Method setMethod;
            try {
                setMethod = clazz.getDeclaredMethod('s' + methods[i].getName().substring(1),
                        methods[i].getReturnType());
            } catch (Exception e) {
                throw new ToastException("获取set方法错误");
            }

            mapping.columns.add(new ColumnMapping(column.name(), column.type(), methods[i], setMethod));
        }

        tableMap.put(clazz, mapping);

        return mapping;
    }

    /**
     * 获取字符串值
     *
     * @param o
     * @param type
     * @return
     */
    private static String getStringValue(Object o, int type) {
        if (o == null) {
            return null;
        }

        String v;

        switch (type) {
            case Types.VARCHAR:
                v = "'" + o + "'";
                break;
            case Types.DATE:
                v = String.valueOf(((Date) o).getTime());
                break;
            default:
                v = o.toString();
        }

        return v;
    }

    private static Object getValue(Object o, Method m) {
        try {
            return m.invoke(o);
        } catch (Exception e) {
            throw new ToastException("取值发生异常", e);
        }
    }

    private static void setValue(Object o, Method m, Object v) {
        try {
            m.invoke(o, v);
        } catch (Exception e) {
            throw new ToastException("设值发生异常");
        }
    }

    private static class TableMapping {
        public String tableName;

        public List<ColumnMapping> columns = new ArrayList<ColumnMapping>();

        public TableMapping(String tableName) {
            super();
            this.tableName = tableName;
        }

    }

    private static class ColumnMapping {
        public String cloumnName;
        public int columnType;
        public Method getMethod;
        public Method setMethod;

        public ColumnMapping(String cloumnName, int columnType, Method getMethod, Method setMethod) {
            super();
            this.cloumnName = cloumnName;
            this.columnType = columnType;
            this.getMethod = getMethod;
            this.setMethod = setMethod;
        }

    }
}
