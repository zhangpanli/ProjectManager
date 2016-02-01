package com.self.projectmanager.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.self.projectmanager.db.DatabaseUtils;
import com.self.projectmanager.exception.ToastException;
import com.self.projectmanager.model.AdvanceRecord;
import com.self.projectmanager.model.Member;
import com.self.projectmanager.model.Project;
import com.self.projectmanager.model.ProjectMember;
import com.self.projectmanager.model.SignInRecord;
import com.self.projectmanager.utils.StringUtils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SimpleDao {

    public static SQLiteDatabase db;

    public static void setDb(SQLiteDatabase db) {
        SimpleDao.db = db;
    }

    /**
     * 添加工程
     *
     * @param project
     */
    public static void addProject(Project project) {
        String sql = DatabaseUtils.getInsertSQL(project);

        db.execSQL(sql);
    }

    /**
     * 更新工程
     *
     * @param values
     */
    public static void updateProject(ContentValues values) {
        db.update("t_project", values, null, null);
    }

    /**
     * 删除工程
     *
     * @param projectId
     */
    public static void deleteProject(int projectId) {
        String sql = "update t_project set status = '99' where project_id = ?";
        db.execSQL(sql, new Object[]{projectId});
    }

    /**
     * 获取工程
     *
     * @param projectId
     */
    public static Project getProject(int projectId) {
        String sql = "select * from t_project where project_id = ? and status = '01'";

        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(projectId)});

        if (cursor.moveToNext()) {
            return DatabaseUtils.getObject(cursor, Project.class);
        } else {
            return null;
        }
    }

    /**
     * 查询工程
     *
     * @param filter
     * @return
     */
    public static List<Project> listProject(Project filter) {
        StringBuilder sql = new StringBuilder("select * from t_project t1 where status = '01'");

        List<String> args = new ArrayList<String>();
        if (!StringUtils.isEmpty(filter.getProjectName())) {
            sql.append(" and project_name like ?");
            args.add("%" + filter.getProjectName() + "%");
        }

        if (filter.getProjectStarttime() != null) {
            sql.append(" and project_endtime >= ?");
            args.add(filter.getProjectStarttime().getTime() + "");
        }

        if (filter.getProjectEndtime() != null) {
            sql.append(" and project_starttime <= ?");
            args.add(filter.getProjectEndtime().getTime() + "");
        }

        if (filter.getMemberId() != null) {
            sql.append(
                    " and eixsts(select 1 from t_project_member where member_id = ? and project_id = t.project_id and status='01')");
            args.add(filter.getMemberId() + "");
        }

        sql.append(" order by project_starttime desc");

        Cursor cursor = db.rawQuery(sql.toString(), args.toArray(new String[0]));

        List<Project> projects = new ArrayList<Project>(cursor.getCount());
        while (cursor.moveToNext()) {
            Project p = DatabaseUtils.getObject(cursor, Project.class);
            projects.add(p);
        }

        return projects;
    }

    /**
     * 添加成员
     *
     * @param member
     */
    public static void addMember(Member member) {
        String sql = DatabaseUtils.getInsertSQL(member);

        db.execSQL(sql);
    }

    /**
     * 更新成员
     *
     * @param values
     */
    public static void updateMember(ContentValues values) {
        db.update("t_member", values, null, null);
    }

    /**
     * 删除成员
     *
     * @param memberId
     */
    public static void deleteMember(int memberId) {
        String sql = "update t_member set status = '99' where member_id = ?";
        db.execSQL(sql, new Object[]{memberId});
    }

    /**
     * 获取成员
     *
     * @param memberId
     */
    public static Member getMember(int memberId) {
        String sql = "select * from t_member where member_id = ? and status = '01'";

        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(memberId)});

        if (cursor.moveToNext()) {
            return DatabaseUtils.getObject(cursor, Member.class);
        } else {
            return null;
        }
    }

    /**
     * 查询成员
     *
     * @param filter
     * @return
     */
    public static List<Member> listMember(Member filter) {
        StringBuilder sql = new StringBuilder("select * from t_member t1 where status = '01'");

        List<String> args = new ArrayList<String>();
        if (!StringUtils.isEmpty(filter.getMemberName())) {
            sql.append(" and member_name like ?");
            args.add("%" + filter.getMemberName() + "%");
        }

        if (filter.getProjectId() != null) {
            sql.append(" and eixsts(select 1 from t_project_member where member_id = t.member_id and project_id = ? and status='01')");
            args.add(filter.getProjectId() + "");
        }

        sql.append(" order by member_name");

        Cursor cursor = db.rawQuery(sql.toString(), args.toArray(new String[0]));

        List<Member> members = new ArrayList<Member>(cursor.getCount());
        while (cursor.moveToNext()) {
            Member m = DatabaseUtils.getObject(cursor, Member.class);
            members.add(m);
        }

        return members;
    }

    /**
     * 添加工程成员
     *
     * @param projectMember
     */
    public static void addProjectMember(ProjectMember projectMember) {
        String sql = DatabaseUtils.getInsertSQL(projectMember);

        db.execSQL(sql);
    }

    /**
     * 更新工程成员
     *
     * @param values
     */
    public static void updateProjectMember(ContentValues values) {
        db.update("t_project_member", values, null, null);
    }

    /**
     * 删除工程成员
     *
     * @param projectMember
     */
    public static void deleteProjectMember(ProjectMember projectMember) {
        String sql = "update t_project_member set status = '99' where project_id = ? and member_id = ?";

        db.execSQL(sql, new Object[]{projectMember.getProjectId(), projectMember.getMemberId()});
    }

    /**
     * 添加签到记录
     *
     * @param signInRecord
     */
    public static void addSignInRecord(SignInRecord signInRecord) {
        String sql = DatabaseUtils.getInsertSQL(signInRecord);

        db.execSQL(sql);
    }

    /**
     * 更新签到记录
     *
     * @param values
     */
    public static void updateSignInRecord(ContentValues values) {
        db.update("t_sign_in_record", values, null, null);
    }

    /**
     * 删除签到记录
     *
     * @param signInRecord
     */
    public static void deleteSignInRecord(SignInRecord signInRecord) {
        String sql = "update t_sign_in_record set status = '99' where project_id = ? and member_id = ?";

        db.execSQL(sql, new Object[]{signInRecord.getProjectId(), signInRecord.getMemberId()});
    }

    /**
     * 获取签到记录
     *
     * @param signInId
     * @return
     */
    public static SignInRecord getSignInRecord(int signInId) {
        String sql = "select * from t_sign_in_record where sign_in_id = ? and status = '01'";

        Cursor cursor = db.rawQuery(sql, new String[]{signInId + ""});

        if (cursor.moveToNext()) {
            return DatabaseUtils.getObject(cursor, SignInRecord.class);
        } else {
            return null;
        }
    }

    /**
     * 查询签到记录
     *
     * @param filter
     * @return
     */
    public static List<SignInRecord> listSignInRecord(SignInRecord filter) {
        StringBuilder sql = new StringBuilder("select * from t_sign_in_record t1 where status = '01'");

        List<String> args = new ArrayList<String>();
        if (filter.getMemberId() != null) {
            sql.append(" and member_id = ?");
            args.add(filter.getMemberId() + "");
        }

        if (filter.getProjectId() != null) {
            sql.append(" and project_id = ?");
            args.add(filter.getProjectId() + "");
        }

        if (filter.getStarttime() != null) {
            sql.append(" and sign_in_date >= ?");
            args.add(filter.getStarttime().getTime() + "");
        }

        if (filter.getEndtime() != null) {
            sql.append(" and sign_in_date <= ?");
            args.add(filter.getEndtime().getTime() + "");
        }

        sql.append(" order by sign_in_date desc");

        Cursor cursor = db.rawQuery(sql.toString(), args.toArray(new String[0]));

        List<SignInRecord> signInRecords = new ArrayList<SignInRecord>(cursor.getCount());
        while (cursor.moveToNext()) {
            SignInRecord s = DatabaseUtils.getObject(cursor, SignInRecord.class);
            signInRecords.add(s);
        }

        return signInRecords;
    }

    /**
     * 添加预支记录
     *
     * @param advanceRecord
     */
    public static void addAdvanceRecord(AdvanceRecord advanceRecord) {
        String sql = DatabaseUtils.getInsertSQL(advanceRecord);

        db.execSQL(sql);
    }

    /**
     * 更新预支记录
     *
     * @param values
     */
    public static void updateAdvanceRecord(ContentValues values) {
        db.update("t_advance_record", values, null, null);
    }

    /**
     * 删除预支记录
     *
     * @param advanceRecord
     */
    public static void deleteAdvanceRecord(AdvanceRecord advanceRecord) {
        String sql = "update t_advance_record set status = '99' where project_id = ? and member_id = ?";

        db.execSQL(sql, new Object[]{advanceRecord.getProjectId(), advanceRecord.getMemberId()});
    }

    /**
     * 获取预支记录
     *
     * @param advanceId
     * @return
     */
    public static AdvanceRecord getAdvanceRecord(int advanceId) {
        String sql = "select * from t_advance_record where advance_id = ? and status = '01'";

        Cursor cursor = db.rawQuery(sql, new String[]{advanceId + ""});

        if (cursor.moveToNext()) {
            return DatabaseUtils.getObject(cursor, AdvanceRecord.class);
        } else {
            return null;
        }
    }

    /**
     * 查询预支记录
     *
     * @param filter
     * @return
     */
    public static List<AdvanceRecord> listAdvanceRecord(AdvanceRecord filter) {
        StringBuilder sql = new StringBuilder("select * from t_advance_record t1 where status = '01'");

        List<String> args = new ArrayList<String>();
        if (filter.getMemberId() != null) {
            sql.append(" and member_id = ?");
            args.add(filter.getMemberId() + "");
        }

        if (filter.getProjectId() != null) {
            sql.append(" and project_id = ?");
            args.add(filter.getProjectId() + "");
        }

        if (filter.getStarttime() != null) {
            sql.append(" and advance_date >= ?");
            args.add(filter.getStarttime().getTime() + "");
        }

        if (filter.getEndtime() != null) {
            sql.append(" and advance_date <= ?");
            args.add(filter.getEndtime().getTime() + "");
        }

        sql.append(" order by advance_date desc");

        Cursor cursor = db.rawQuery(sql.toString(), args.toArray(new String[0]));

        List<AdvanceRecord> advanceRecords = new ArrayList<AdvanceRecord>(cursor.getCount());
        while (cursor.moveToNext()) {
            AdvanceRecord a = DatabaseUtils.getObject(cursor, AdvanceRecord.class);
            advanceRecords.add(a);
        }

        return advanceRecords;
    }

    /**
     * 执行SQL语句
     *
     * @param sql
     */
    public static void execSQL(String sql) {
        db.execSQL(sql);
    }

    /**
     * 通用查询
     *
     * @param clazz
     * @param sql
     * @param args
     * @return
     */
    public static <T> List<T> query(Class<T> clazz, String sql, Object... args) {
        String[] arggs = new String[args.length];

        for (int i = 0; i < arggs.length; i++) {
            if (args[i] == null) {
                arggs[i] = null;
            } else {
                arggs[i] = String.valueOf(args[i]);
            }
        }

        Cursor cursor = db.rawQuery(sql, arggs);

        List<T> list = new ArrayList<T>(cursor.getCount());
        while (cursor.moveToNext()) {
            list.add(invoke(clazz, cursor));
        }

        return list;
    }

    /**
     * 获取第一条记录
     *
     * @param clazz
     * @param sql
     * @param args
     * @return
     */
    public static <T> T getFirst(Class<T> clazz, String sql, Object... args) {
        String[] arggs = new String[args.length];

        for (int i = 0; i < arggs.length; i++) {
            if (args[i] == null) {
                arggs[i] = null;
            } else {
                arggs[i] = String.valueOf(args[i]);
            }
        }

        Cursor cursor = db.rawQuery(sql, arggs);

        if (cursor.moveToNext()) {
            return invoke(clazz, cursor);
        }

        return null;
    }

    /**
     * cursor转换为指定类型对象
     *
     * @param clazz
     * @param cursor
     * @return
     */
    private static <T> T invoke(Class<T> clazz, Cursor cursor) {
        T t;
        try {
            t = clazz.newInstance();
        } catch (Exception e) {
            throw new ToastException("调用构造方法异常", e);
        }

        int count = cursor.getColumnCount();
        for (int i = 0; i < count; i++) {
            String name = cursor.getColumnName(i);

            String fieldName = columnName2FieldName(name);
            Field field;
            try {
                field = clazz.getDeclaredField(fieldName);
            } catch (Exception e) {
                throw new ToastException("获取字段异常", e);
            }

            String methodName = columnName2SetMethodName(name);
            Method method;
            try {
                method = clazz.getDeclaredMethod(methodName, field.getType());
            } catch (Exception e) {
                throw new ToastException("获取set方法异常", e);
            }

            Object value = getCursorValue(cursor, i, field.getType());

            try {
                method.invoke(t, value);
            } catch (Exception e) {
                throw new ToastException("设值发生异常");
            }
        }

        return t;
    }

    /**
     * 从cursor中获取特定类型值
     *
     * @param cursor
     * @param i
     * @param fieldType
     * @return
     */
    private static Object getCursorValue(Cursor cursor, int i, Class<?> fieldType) {
        Object val = null;

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

        return val;
    }

    private static String columnName2FieldName(String columnName) {
        StringBuilder sb = new StringBuilder();

        boolean toUpper = false;

        for (int i = 0; i < columnName.length(); i++) {
            char c = columnName.charAt(i);

            if (c == '_') {
                toUpper = true;
                continue;
            }

            if (toUpper) {
                if (c >= 'a' && c <= 'z') {
                    sb.append(c - 32);
                } else {
                    sb.append(c);
                }

                toUpper = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    private static String columnName2SetMethodName(String columnName) {
        StringBuilder sb = new StringBuilder("set");

        boolean toUpper = true;

        for (int i = 0; i < columnName.length(); i++) {
            char c = columnName.charAt(i);

            if (c == '_') {
                toUpper = true;
                continue;
            }

            if (toUpper) {
                if (c >= 'a' && c <= 'z') {
                    sb.append(c - 32);
                } else {
                    sb.append(c);
                }

                toUpper = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

}
