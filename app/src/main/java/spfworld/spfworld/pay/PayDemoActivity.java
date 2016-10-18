package spfworld.spfworld.pay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.alipay.sdk.app.PayTask;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.xutils.x;

import spfworld.spfworld.R;
import spfworld.spfworld.fragment.find.login.LoginFragment;
import spfworld.spfworld.fragment.find.login.RegisterFragment;
import spfworld.spfworld.fragment.find.login.SetPassWordFragment;
import spfworld.spfworld.fragment.find.pay.OnlinePaymentFragment;

public class PayDemoActivity extends FragmentActivity {
	private FragmentManager FM;
	public Handler handler;
	private List<Fragment> FGList;
	private  final int MAIN_CONTENT_SPACE_ID=R.id.payDemo_fragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_main);
		x.view().inject(this);
		//fragment切换
		FM = getSupportFragmentManager();
		FGList = iniFragmentList();
		setFGshow(0);
		//handler传值
		handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what){
					case 0:
						setFGshow(0);
						break;
				}
			}
		};
	}


	/**
	 * 按下标显示Fragment
	 *
	 * @param index
	 */
	public void setFGshow(int index) {
		FragmentTransaction FT = FM.beginTransaction();
		if (index < FGList.size()) {
			for (int i = 0; i < FGList.size(); i++) {
				if (index == i) {
					FT.show(FGList.get(i));
				} else {
					FT.hide(FGList.get(i));
				}
			}
		} else {
			Log.e("MainActivity", "下标越界没有此页面");
		}
		FT.commit();
	}
	/**
	 * 初始化Fragment将Fragment添加到事务中去
	 */
	public List<Fragment> iniFragmentList() {
		List<Fragment> list = new ArrayList<Fragment>();
		FragmentTransaction FT = FM.beginTransaction();
		OnlinePaymentFragment onlinePaymentFragment=new OnlinePaymentFragment();
		FT.add(MAIN_CONTENT_SPACE_ID,onlinePaymentFragment,"登录_TAG=0");
		FT.commit();

		list.add(onlinePaymentFragment);
		return list;
	}

}
