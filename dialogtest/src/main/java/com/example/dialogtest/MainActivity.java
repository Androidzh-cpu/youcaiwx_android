package com.example.dialogtest;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dialogtest.widget.ActionSheetDialog;
import com.example.dialogtest.widget.AlertDialog;
import com.lidong.pdf.PDFView;
import com.lidong.pdf.listener.OnDrawListener;
import com.lidong.pdf.listener.OnLoadCompleteListener;
import com.lidong.pdf.listener.OnPageChangeListener;
import com.xuhao.didi.core.iocore.interfaces.IPulseSendable;
import com.xuhao.didi.core.pojo.OriginalData;
import com.xuhao.didi.socket.client.sdk.OkSocket;
import com.xuhao.didi.socket.client.sdk.client.ConnectionInfo;
import com.xuhao.didi.socket.client.sdk.client.action.SocketActionAdapter;
import com.xuhao.didi.socket.client.sdk.client.connection.IConnectionManager;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button mClick1;
    private Button mClick2;
    private Button mClick3;
    private Button mClick4;
    private Button mClick5;
    private Button mClick6;
    private MainActivity context;
    private PDFView mPdfviewMy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        initView();

        loadPDF();
    }

    public void loadPDF() {
        String url = "http://youcai2020.oss-cn-beijing.aliyuncs.com/style/pdf/20190320/87c12941d38bb7724e273a9d58e69b28.pdf";
        mPdfviewMy.fileFromLocalStorage(new OnPageChangeListener() {
            @Override
            public void onPageChanged(int page, int pageCount) {
                Toast.makeText(MainActivity.this, "page= " + page +
                        " pageCount= " + pageCount, Toast.LENGTH_SHORT).show();
            }
        }, new OnLoadCompleteListener() {
            @Override
            public void loadComplete(int nbPages) {
                Toast.makeText(MainActivity.this, "加载完成" + nbPages, Toast.LENGTH_SHORT).show();
            }
        }, new OnDrawListener() {
            @Override
            public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

            }
        }, url, getPdfTime());
    }

    public String getPdfTime() {
        long timeStampSec = System.currentTimeMillis();
        String str = String.valueOf(timeStampSec);
        if (!TextUtils.isEmpty(str)) {
            return str;
        } else {
            return "1234567890";
        }
    }

    private void initView() {
        mClick1 = (Button) findViewById(R.id.click1);
        mClick1.setOnClickListener(this);
        mClick2 = (Button) findViewById(R.id.click2);
        mClick2.setOnClickListener(this);
        mClick3 = (Button) findViewById(R.id.click3);
        mClick3.setOnClickListener(this);
        mClick4 = (Button) findViewById(R.id.click4);
        mClick4.setOnClickListener(this);
        mClick5 = (Button) findViewById(R.id.click5);
        mClick5.setOnClickListener(this);
        mClick6 = (Button) findViewById(R.id.click6);
        mClick6.setOnClickListener(this);
        mPdfviewMy = (PDFView) findViewById(R.id.pdfView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.click1:
                // TODO 19/03/31
                new ActionSheetDialog(context)
                        .builder()
                        .setTitle("清空消息列表后，聊天记录依然保留，确定要清空消息列表？")
                        .setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .addSheetItem("清空消息列表", ActionSheetDialog.SheetItemColor.Red
                                , new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {

                                    }
                                }).show();
                break;
            case R.id.click2:
                // TODO 19/03/31
                new ActionSheetDialog(context)
                        .builder()
                        .setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .addSheetItem("发送给好友", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {

                                    }
                                })
                        .addSheetItem("转载到空间相册", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {

                                    }
                                })
                        .addSheetItem("上传到群相册", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {

                                    }
                                })
                        .addSheetItem("保存到手机", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {

                                    }
                                })
                        .addSheetItem("收藏", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {

                                    }
                                })
                        .addSheetItem("查看聊天图片", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {

                                    }
                                }).show();
                break;
            case R.id.click3:
                // TODO 19/03/31
                new ActionSheetDialog(context)
                        .builder()
                        .setTitle("请选择操作")
                        .setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .addSheetItem("条目一", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        Toast.makeText(context,
                                                "item" + which, Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                })
                        .addSheetItem("条目二", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        Toast.makeText(context,
                                                "item" + which, Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                })
                        .addSheetItem("条目三", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        Toast.makeText(context,
                                                "item" + which, Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                })
                        .addSheetItem("条目四", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        Toast.makeText(context,
                                                "item" + which, Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                })
                        .addSheetItem("条目五", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        Toast.makeText(context,
                                                "item" + which, Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                })
                        .addSheetItem("条目六", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        Toast.makeText(context,
                                                "item" + which, Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                })
                        .addSheetItem("条目七", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        Toast.makeText(context,
                                                "item" + which, Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                })
                        .addSheetItem("条目八", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        Toast.makeText(context,
                                                "item" + which, Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                })
                        .addSheetItem("条目九", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        Toast.makeText(context,
                                                "item" + which, Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                })
                        .addSheetItem("条目十", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        Toast.makeText(context,
                                                "item" + which, Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                }).show();
                break;
            case R.id.click4:
                // TODO 19/03/31
                new AlertDialog(context).builder().setTitle("退出当前账号")
                        .setMsg("再连续登陆15天，就可变身为QQ达人。退出QQ可能会使你现有记录归零，确定退出？")
                        .setPositiveButton("确认退出", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
                break;
            case R.id.click5:
                // TODO 19/03/31
                new AlertDialog(context).builder()
                        .setMsg("你现在无法接收到新消息提醒。请到系统-设置-通知中开启消息提醒")
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
                break;
            case R.id.click6:
                //连接参数设置(IP,端口号),这也是一个连接的唯一标识,不同连接,该参数中的两个值至少有其一不一样
                ConnectionInfo info = new ConnectionInfo("101.201.237.201", 2346);
                //调用OkSocket,开启这次连接的通道,拿到通道Manager
                IConnectionManager manager = OkSocket.open(info);
                //注册Socket行为监听器,SocketActionAdapter是回调的Simple类,其他回调方法请参阅类文档
                manager.registerReceiver(new SocketActionAdapter() {
                    @Override
                    public void onSocketConnectionSuccess(ConnectionInfo info, String action) {
                        Log.e("ConnectionInfo socket: ", "onSocketConnectionSuccess---action: " + action);

                        //连接成功其他操作...
                        //链式编程调用,给心跳管理器设置心跳数据,一个连接只有一个心跳管理器,因此数据只用设置一次,如果断开请再次设置.
                        OkSocket.open(info)
                                .getPulseManager()
                                .setPulseSendable(new PulseData())//只需要设置一次,下一次可以直接调用pulse()
                                .pulse();//开始心跳,开始心跳后,心跳管理器会自动进行心跳触发
                    }

                    @Override
                    public void onSocketIOThreadStart(String action) {
                        super.onSocketIOThreadStart(action);
                        Log.e("ConnectionInfo socket: ", "onSocketIOThreadStart---action: " + action);
                    }

                    @Override
                    public void onSocketReadResponse(ConnectionInfo info, String action, OriginalData data) {
                        super.onSocketReadResponse(info, action, data);
                        Log.e("ConnectionInfo socket: ", "onSocketReadResponse---action: " + action);
                    }
                });
                //调用通道进行连接
                manager.connect();
                break;
            default:
                break;
        }
    }

    public class PulseData implements IPulseSendable {
        private String str = "pulse";

        @Override
        public byte[] parse() {
            byte[] body = str.getBytes(Charset.defaultCharset());
            ByteBuffer bb = ByteBuffer.allocate(4 + body.length);
            bb.order(ByteOrder.BIG_ENDIAN);
            bb.putInt(body.length);
            bb.put(body);
            return bb.array();
        }
    }

}
