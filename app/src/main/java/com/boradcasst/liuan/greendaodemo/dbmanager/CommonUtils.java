package com.boradcasst.liuan.greendaodemo.dbmanager;

import android.content.Context;
import android.util.Log;

import com.student.dao.StudentDao;
import com.student.entity.Student;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * 完成对某一张表的具体操作 ORM 操作的是对象 Student
 */
public class CommonUtils {
    private DaoManager manager;

    private static final String TAG = "CommonUtils";

    public CommonUtils(Context context) {
        this.manager = DaoManager.getInstance();
        manager.init(context);
    }

    /**
     * 完成对数据中student 表的插入操作
     *
     * @param student
     * @return
     */
    public boolean insertStudent(Student student) {
        boolean flag = false;
        flag = manager.getDaoSession().insert(student) != -1;
        Log.e(TAG, "insertStudent: " + flag);
        return flag;
    }

    /**
     * 同时插入多条记录，需要开辟新的线程
     *
     * @param students
     * @return
     */
    public boolean insertMultStudent(final List<Student> students) {
        boolean flag = false;
        try {
            manager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (Student student : students) {
                        manager.getDaoSession().insertOrReplace(student);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对student的某一条记录的修改
     *
     * @param student
     * @return
     */
    public boolean updateStudent(Student student) {
        boolean falg = false;
        try {
            manager.getDaoSession().update(student);
            falg = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return falg;
    }

    public boolean deleteStudent(Student student) {
        boolean falg = false;
        try {

            manager.getDaoSession().delete(student);//按照指定的id 进行删除 delete from student where _id=?
            falg = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteAll() {
        boolean falg = false;
        try {

            manager.getDaoSession().deleteAll(Student.class);//删除素有的记录

            falg = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return falg;
    }

    /**
     * 返回多条记录
     *
     * @return
     */
    public List<Student> queryAll() {
        return manager.getDaoSession().loadAll(Student.class);
    }

    /**
     * 按照主键返回单条记录
     *
     * @param key
     * @return
     */
    public Student queryOne(Long key) {
        return manager.getDaoSession().load(Student.class, key);
    }

    public void query1() {
        //使用native sql进行查询操作
        List<Student> list = manager.getDaoSession().queryRaw(Student.class, "where name like ? and _id > ?", new String[]{"%六%", "3"});
        Log.e(TAG, "query1: " + list);
    }

    /**
     * select * from student where name like ? or name =?
     * < <=  !=  in  between and
     * select * from student where age > 23 and adress like "江西"
     */
    public void query2() {
        //使用查询构建器
        QueryBuilder<Student> builder = manager.getDaoSession().queryBuilder(Student.class);
//        逻辑与
        List<Student> list = builder.where(StudentDao.Properties.Age.ge(1)).where(StudentDao.Properties.Address.like("北京")).list();
        Log.e(TAG, "query2: "+list.toString() );
    }

    public void query3(){
        //select * from student where (address ='北京' or age >21) and name like '%张%'
        //逻辑与 和逻辑或 是双目运算符
        QueryBuilder<Student> builder = manager.getDaoSession().queryBuilder(Student.class);
        builder.whereOr(StudentDao.Properties.Address.eq("北京"),StudentDao.Properties.Age.gt(21));
        builder.where(StudentDao.Properties.Name.like("张"));
        builder.whereOr(StudentDao.Properties.Id.ge(new Integer(2)),StudentDao.Properties.Age.ge(21));
        //取前三条数据
        builder.limit(3);
        List<Student> list = builder.list();
        Log.e(TAG, "query3: "+list );
    }
}
