package com.boradcasst.liuan.greendaodemo.dbmanager;

import android.content.Context;

import com.student.dao.DaoMaster;
import com.student.dao.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * 1 创建数据库
 * 2 创建数据库的表
 * 3 包含对数据库的CRUD
 * 4 对数据的升级
 */
public class DaoManager {
    private static final String TAG = "DbManager";
    private static final String DB_NAME = "mydb.sqlite";//数据库名称
    private volatile static DaoManager manager;//多线程访问
    private static DaoMaster.DevOpenHelper helper;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;
    private Context context;

    /**
     * 使用单例模式 获得操作数据库的对象
     * @return
     */
    public void  init(Context context){
        this.context=context;
    }
    public static DaoManager getInstance() {
        DaoManager instance = null;
        if (manager == null) {
            synchronized (DaoManager.class) {
                if (instance == null) {
                    instance = new DaoManager();
                    manager = instance;
                }
            }
        }
        return  instance;
    }

    /**
     * 判断是否有存在数据库 如果没有则创建数据库
     * @return
     */
    public DaoMaster getDaoMaster(){
        if(daoMaster==null){
            DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
            daoMaster=new DaoMaster(devOpenHelper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 完成对数据库的添加 删除 修改 查询的操作 仅仅是一个接口
     * @return
     */
    public DaoSession getDaoSession(){
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster=getDaoMaster();
            }
            daoSession=daoMaster.newSession();
        }
        return daoSession;
    }

    /**
     * 打开输出日志的操作，默认是关闭的
     */
    public void setDebug(){
        QueryBuilder.LOG_SQL=true;
        QueryBuilder.LOG_VALUES=true;
    }
    public void closeDaoSession(){
        if (daoSession != null) {
            daoSession.clear();
            daoSession=null;
        }
    }
    public void closeHelper(){
        if (helper != null) {
            helper.close();
            helper=null;
        }
    }

    /**
     * 关闭所有的操作，数据库开启的时候，使用完毕了必须关闭
     */
    public void closeConnection(){
        closeHelper();
        closeDaoSession();
    }
}
