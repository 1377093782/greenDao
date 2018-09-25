package com.boradcasst.liuan.daogenerator;


import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class DaoMaker {
    public static void main(String[] args) {
        //生成数据库的实体类XX entity对应的是数据库的表
        Schema schema = new Schema(2,"com.student.entity");
        addStudent(schema);
        schema.setDefaultJavaPackageDao("com.student.dao");
        try {
            new DaoGenerator().generateAll(schema,"G:\\android\\demo\\GreenDaoDemo\\app\\src\\main\\java-gen");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  创建数据库的表
     * @param schema
     */
    private static void addStudent(Schema schema){
        //创建数据库的表
        Entity entity=schema.addEntity("Student");
        //主键 是int类型
        entity.addIdProperty();
        entity. addStringProperty("name");//创建数据库的列
        entity. addStringProperty("address");//创建数据库的列
        entity. addIntProperty("age");//创建数据库的列
    }
}
