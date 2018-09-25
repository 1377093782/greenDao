package com.boradcasst.liuan.greendaodemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.boradcasst.liuan.greendaodemo.dbmanager.CommonUtils;
import com.student.entity.Student;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    /**
     * 添加数据
     */
    private Button mBtClick;
    private CommonUtils commonUtils;
    /**
     * 添加数据
     */
    private Button mBtInsert;
    /**
     * 添加多条数据
     */
    private Button mBtInsertMult;
    /**
     * 修改一条数据
     */
    private Button mBtUpdata;
    /**
     * 删除一条数据
     */
    private Button mBtDelete;
    /**
     * 删除所有数据
     */
    private Button mBtDeleteAll;
    /**
     * 查询单条记录
     */
    private Button mBtQuery;
    /**
     * 查询所有记录
     */
    private Button mBtQueryAll;
    /**
     * 使用queryBuilder
     */
    private Button mBtQueryBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        commonUtils = new CommonUtils(this);
    }

    private void initView() {
        mBtClick = (Button) findViewById(R.id.bt_insert);
        mBtClick.setOnClickListener(this);
        mBtInsert = (Button) findViewById(R.id.bt_insert);
        mBtInsert.setOnClickListener(this);
        mBtInsertMult = (Button) findViewById(R.id.bt_insert_mult);
        mBtInsertMult.setOnClickListener(this);
        mBtUpdata = (Button) findViewById(R.id.bt_updata);
        mBtUpdata.setOnClickListener(this);
        mBtDelete = (Button) findViewById(R.id.bt_delete);
        mBtDelete.setOnClickListener(this);
        mBtDeleteAll = (Button) findViewById(R.id.bt_delete_all);
        mBtDeleteAll.setOnClickListener(this);
        mBtQuery = (Button) findViewById(R.id.bt_query);
        mBtQuery.setOnClickListener(this);
        mBtQueryAll = (Button) findViewById(R.id.bt_query_all);
        mBtQueryAll.setOnClickListener(this);
        mBtQueryBuilder = (Button) findViewById(R.id.bt_query_builder);
        mBtQueryBuilder.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            //插入数据库
            case R.id.bt_insert:
                insertData();
                break;
            case R.id.bt_insert_mult:
                insertMultData();
                break;
            case R.id.bt_updata:
                updata();
                break;
            case R.id.bt_delete:
                delete();
                break;
            case R.id.bt_delete_all:
                deleteAll();
                break;
            case R.id.bt_query:
                query();
                break;
            case R.id.bt_query_all:
                queryAll();
                break;
            case R.id.bt_query_builder:
                queryBuilder();
                break;
        }
    }

    private void queryBuilder() {
//  commonUtils.query1();
  commonUtils.query3();
        //           commonUtils.query2();
    }

    private void queryAll() {
        List<Student> students = commonUtils.queryAll();
        Log.e(TAG, "queryAll: " + students.toString());
    }

    private void query() {
        Student student = commonUtils.queryOne(1L);
        Log.e(TAG, "query: " + student.toString());
    }

    private void deleteAll() {
        commonUtils.deleteAll();
    }

    private void delete() {
        Student student = new Student();
        student.setId(1231153L);

        commonUtils.deleteStudent(student);
    }

    private void updata() {
        //update student set name='jack' where id=1001;
        Student student = new Student();
        student.setId(1231153L);
        student.setAge(100);
        student.setName("Jack");
        student.setAddress("惠科");
        commonUtils.updateStudent(student);
    }

    private void insertMultData() {
        Log.e(TAG, "insertMultData: ");
        List<Student> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Student student = new Student();
            student.setAddress("山东");
            student.setAge(18);
            student.setName("小六" + i);
            list.add(student);
        }
        commonUtils.insertMultStudent(list);
    }

    private void insertData() {
        Log.e(TAG, "insertData: ");
        Student student1 = getStudent("北京",15,"张三");
        Student student2 = getStudent("山东",20,"李四");
        Student student3 = getStudent("江西",20,"王五");
        Student student4 = getStudent("深圳",25,"小六");
//        student.setId(1231153L);
        commonUtils.insertStudent(student1);
        commonUtils.insertStudent(student2);
        commonUtils.insertStudent(student3);
        commonUtils.insertStudent(student4);
    }

    @NonNull
    private Student getStudent(String adress,int age,String name) {
        Student student = new Student();
        student.setAddress(adress);
        student.setAge(age);
        student.setName(name);
        return student;
    }

}
