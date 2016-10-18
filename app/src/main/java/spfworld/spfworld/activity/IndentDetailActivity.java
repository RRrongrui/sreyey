package spfworld.spfworld.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import spfworld.spfworld.R;
import spfworld.spfworld.activity.User.UserOrderActivity;
import spfworld.spfworld.entity.Content;
import spfworld.spfworld.entity.IndentDetail;
import spfworld.spfworld.pay.PayDemoActivity;
import spfworld.spfworld.utils.ImageLoad;
import spfworld.spfworld.utils.SharedHelper;
import spfworld.spfworld.utils.XutilsClass;

/**
 * Created by guozhengke on 2016/9/26.
 * 订单详情页
 * post请求
 */
public class IndentDetailActivity extends AppCompatActivity {
    @ViewInject(R.id.indent_back)
    private ImageView indent_back;
    @ViewInject(R.id.indent_detail_no)
    private TextView indent_detail_no;
    @ViewInject(R.id.indent_detail_state)
    private TextView indent_detail_state;
    @ViewInject(R.id.indent_detail_name)
    private TextView indent_detail_name;
    @ViewInject(R.id.indent_detail_evenTtime)
    private TextView indent_detail_evenTtime;
    @ViewInject(R.id.indent_detail_address)
    private TextView indent_detail_address;
    @ViewInject(R.id.indent_detail_phone)
    private TextView indent_detail_phone;
    @ViewInject(R.id.event_indentTime)
    private TextView event_indentTime;
    @ViewInject(R.id.indent_detail_dCost)
    private TextView indent_detail_dCost;
    @ViewInject(R.id.indent_detail_number)
    private  TextView indent_detail_number;
    @ViewInject(R.id.indent_detail_zCost)
    private TextView indent_detail_zCost;
    @ViewInject(R.id.indent_detail_img)
    private ImageView indent_detail_img;
    @ViewInject(R.id.pay_success)
    private LinearLayout pay_success;
    @ViewInject(R.id.indent_detail_false)
    private  TextView indent_detail_false;
    @ViewInject(R.id.indent_detail_true)
    private  TextView indent_detail_true;
    @ViewInject(R.id.indent_detail_time)
    private TextView indent_detail_time;
   // private SharedHelper sharedHelper;
    private Handler handler=new Handler();
    private SimpleDateFormat dfs;
    private long MINUTE=0;
    private long SECOND=0;
    private long TIME=900;
    private List<IndentDetail.DataBean> data;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_indent_dtail);
        x.view().inject(this);
        GetIndentDtail();

        //返回键
        indent_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //取消订单
        indent_detail_false.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetIndentDel(data.get(0).getOrder_num());
            }
        });
        //前往支付
        indent_detail_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MINUTE!=0&&SECOND!=0){
                    Content.DATAINDENT=data.get(0).getOrder_num();
                    Content.event_name=data.get(0).getAct_theme();
                    Content.Zmony=data.get(0).getTotal_price();
                    Intent intent=new Intent(IndentDetailActivity.this, PayDemoActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"已超时,交易关闭", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    //定时器线程
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            if (MINUTE==0){
                if (SECOND==0){
                    indent_detail_time.setVisibility(View.GONE);
                    indent_detail_state.setText("交易关闭");
                }else if (SECOND==60){
                    SECOND=59;
                    SECOND--;
                }else if (SECOND<11){
                    SECOND--;
                    indent_detail_time.setText("剩余支付时间:"+MINUTE+":0"+SECOND);
                }else {
                    SECOND--;
                    indent_detail_time.setText("剩余支付时间:"+MINUTE+":"+SECOND);
                }
            }else if (MINUTE<10){
                if (SECOND==0||SECOND==60){
                    MINUTE--;
                    SECOND=59;
                    SECOND--;
                    indent_detail_time.setText("剩余支付时间:"+"0"+MINUTE+":"+SECOND);
                }else if (SECOND<11){
                    SECOND--;
                    indent_detail_time.setText("剩余支付时间:"+"0"+MINUTE+":0"+SECOND);
                }else {
                    SECOND--;
                    indent_detail_time.setText("剩余支付时间:"+"0"+MINUTE+":"+SECOND);
                }

            }else if (MINUTE==15){
                MINUTE--;
                if (SECOND==0||SECOND==60){
                    SECOND=59;
                    SECOND--;
                    indent_detail_time.setText("剩余支付时间:"+MINUTE+":"+SECOND);
                }else if (SECOND<11){
                    SECOND--;
                    indent_detail_time.setText("剩余支付时间:"+MINUTE+":0"+SECOND);
                }else {
                    SECOND--;
                    indent_detail_time.setText("剩余支付时间:"+MINUTE+":"+SECOND);
                }
            }else if (SECOND==0||SECOND==60){
                SECOND=59;
                MINUTE--;
                SECOND--;
                indent_detail_time.setText("剩余支付时间:"+MINUTE+":"+SECOND);
            }else if (SECOND<11){
                SECOND--;
                indent_detail_time.setText("剩余支付时间:"+MINUTE+":0"+SECOND);
            }else {
                SECOND--;
                indent_detail_time.setText("剩余支付时间:"+MINUTE+":"+SECOND);
            }
            //online_payment_countDown.setText(TIME+"");
            handler.postDelayed(this,1000);
        }
    };
    //判断支付时间
    public void TimePay(final String currtTime, final String payTime) {
        Log.e("定时器调用","");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Date begin=dfs.parse(currtTime);
                    Date end = dfs.parse(payTime);
                    long between=(end.getTime()-begin.getTime())/1000;//除以1000是为了转换成秒
                    Log.e("秒数计算值：",between+"");
                    if (between <=TIME){
                        long minute=between%3600/60;//分
                        long second=between%60;//秒
                        MINUTE=15-minute;
                        SECOND=59-second;
                        Log.e("Runnable",MINUTE+""+SECOND+"");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        handler.postDelayed(runnable,1000);
    }
    //页面网络请求
    public void GetIndentDtail(){
        XutilsClass.getInstance().postIndetDetail(Content.DATAINDENT, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.e("GetIndentDtail",s);
                Gson gson=new Gson();
                IndentDetail indentDetail=gson.fromJson(s,IndentDetail.class);
                data=indentDetail.getData();
                //判断返回参数
                if (indentDetail.getStatus()==200){
                    //设置数据
                    ImageLoad imageLoad=new ImageLoad();
                    imageLoad.HttpImage("http://"+data.get(0).getContent(),indent_detail_img);
                    indent_detail_no.setText("订单编号:"+data.get(0).getOrder_num());
                    indent_detail_name.setText(data.get(0).getAct_theme());
                    indent_detail_evenTtime.setText("时间:"+data.get(0).getJoindate()+" "+data.get(0).getJointime());
                    indent_detail_address.setText("地址:"+data.get(0).getAct_address());
                    indent_detail_phone.setText(data.get(0).getAppmobile());
                    event_indentTime.setText(data.get(0).getCreate_time());
                    indent_detail_dCost.setText("￥"+data.get(0).getPrice());
                    indent_detail_number.setText(data.get(0).getNum()+"份");
                    indent_detail_zCost.setText("￥"+data.get(0).getTotal_price());
                    switch (data.get(0).getStatus()){
                        case "0":
                            indent_detail_state.setText("待付款");
                            pay_success.setVisibility(View.VISIBLE);
                            dfs= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date curDate = new Date(System.currentTimeMillis());
                            String payTime = dfs.format(curDate);
                            Log.e("获取时间",payTime);
                            Log.e("返回时间",Content.currtTime);
                            TimePay(Content.currtTime,payTime);
                            break;
                        case "1":
                            indent_detail_state.setText("付款成功");
                            indent_detail_time.setVisibility(View.GONE);
                            pay_success.setVisibility(View.GONE);
                            break;
                    }
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.e("GetIndentDtail___",throwable.toString());
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
    //取消订单网络请求
    public  void GetIndentDel(String order_num){
        XutilsClass.getInstance().postIndentDel(order_num, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.e("跳转，取消订单成功",s);
                //Intent intent=new Intent();
                //startActivity(intent);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.e("取消订单失败",throwable.toString());
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
