package com.example.dialogtest;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dialogtest.entiy.User;

import org.litepal.LitePal;

import java.util.List;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextTest;
    private Button mBtn1;
    private Button mBtn2;
    private Button mBtn3;
    private Button mBtn4;
    private Button mBtn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
    }

    private void initView() {
        mTextTest = (TextView) findViewById(R.id.test_text);
        mBtn1 = (Button) findViewById(R.id.btn1);
        mBtn1.setOnClickListener(this);
        mBtn2 = (Button) findViewById(R.id.btn2);
        mBtn2.setOnClickListener(this);
        mBtn3 = (Button) findViewById(R.id.btn3);
        mBtn3.setOnClickListener(this);
        mBtn4 = (Button) findViewById(R.id.btn4);
        mBtn4.setOnClickListener(this);
        mBtn5 = (Button) findViewById(R.id.btn5);
        mBtn5.setOnClickListener(this);

        check();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1://创建数据库
                // TODO 19/07/08
                SQLiteDatabase db = LitePal.getDatabase();
                boolean open = db.isOpen();
                mTextTest.setText("数据库状态:" + open);
                break;
            case R.id.btn2://增
                // TODO 19/07/08
                for (int i = 0; i < 5; i++) {
                    User movie1 = new User();
                    movie1.setName("东成西就" + i);
                    movie1.setPrice(10000 + i);
                    //这一句代码就是将一条记录存储进数据库中
                    movie1.save();
                }
                check();
                break;
            case R.id.btn3://删
                // TODO 19/07/08
                LitePal.deleteAll(User.class);

                check();
                break;
            case R.id.btn4://改
                // TODO 19/07/08
                User user1 = new User();
                user1.setName("修改后的名字");
                user1.updateAll("name like ?", "东成西就");

                check();
                break;
            case R.id.btn5://查
                // TODO 19/07/08
                check();
                break;
            default:
                break;
        }
    }

    public void check() {
        long[] ids = new long[]{101, 99, 97};
        List<User> list = LitePal.findAll(User.class, ids);
        StringBuffer stringBuffer = new StringBuffer();
        for (User user : list) {
            stringBuffer.append(user.getName() + "  " + user.getPrice() + "  id:" + user.getId() + "\n");
        }
        mTextTest.setText(stringBuffer.toString());
    }
}
