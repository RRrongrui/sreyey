package spfworld.spfworld.fragment.find.tribune;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.xutils.common.Callback;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import ab.util.AbLogUtil;
import ab.util.AbStrUtil;
import spfworld.spfworld.R;
import spfworld.spfworld.activity.LoginActivity;
import spfworld.spfworld.activity.Tribune.PostTribuneDateActivity;
import spfworld.spfworld.activity.Tribune.TribuneDatailsActivity;
import spfworld.spfworld.adapter.Tribune.GridAdapter;
import spfworld.spfworld.adapter.Tribune.ItemTribuneAdapter;
import spfworld.spfworld.adapter.Tribune.TribuneAdapter;
import spfworld.spfworld.entity.Tribune.Tribune;
import spfworld.spfworld.utils.SharedHelper;
import spfworld.spfworld.utils.ToastUtils;
import spfworld.spfworld.utils.XutilsClass;
import spfworld.spfworld.utils.dialog.MyAlertDialog;

/**
 * 论坛界面
 * Created by Administrator on 2016/8/30.
 */
public class TribuneFragment extends Fragment {
    private View view;
    private TribuneAdapter adapter;
    private List<Tribune.DataBean> data;
    private int page=1;
    @ViewInject(R.id.mRecyclerView)
    private PullToRefreshListView recyclerView;
    @ViewInject(R.id.swipelayout)
    private SwipeRefreshLayout layout;
    @ViewInject(R.id.tv_select)
    private TextView tvSelect;
    private GridAdapter ga;
    private LinearLayoutManager manager;
    private Context mContext;
    private PopupWindow popupWindow;
    private SharedHelper sharedHelper;
    private ItemTribuneAdapter itemTribuneAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tribune, container, false);
        x.view().inject(this, view);
        PotTribune();
        sharedHelper=new SharedHelper(getActivity());
        return view;
    }
    //帖子列表网络请求
    String keyword = "2";
    private void PotTribune() {
        XutilsClass.getInstance().getTribune(page+"",keyword, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                   AbLogUtil.i("oye", AbStrUtil.parseEmpty(s));
                   Gson gson=new Gson();
                   Tribune tribune = gson.fromJson(s,Tribune.class);
                   data=tribune.getData();
                getData();
            }
            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.e("TribuneFragment数据请求失败",throwable.toString());
            }
            @Override
            public void onCancelled(CancelledException e) {
            }
            @Override
            public void onFinished() {
            }
        });
    }

    private void getData() {
        itemTribuneAdapter = new ItemTribuneAdapter(getActivity(), data);
        recyclerView.setAdapter(itemTribuneAdapter);
    }

    @Event(value = {R.id.iv_discuss, R.id.ll_pag}, type = View.OnClickListener.class)
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_discuss://发布我的帖子
                String user_key = sharedHelper.ReadData("String", "user_key").toString();
                if (user_key == "user_key" || user_key == null) {
                    GetStatusLogin2();
                }else {
                    Intent intent = new Intent(mContext, PostTribuneDateActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ll_pag://智能排序
                ShowListPopupWindow();

                break;
            default:
                break;
        }
    }

    private void ShowListPopupWindow() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_list_item_1,null);
        TextView tv_smart = (TextView) contentView.findViewById(R.id.tv_smart);
        TextView order_Date = (TextView) contentView.findViewById(R.id.order_data);
        TextView order_Hot = (TextView) contentView.findViewById(R.id.order_hot);

        final PopupWindow popupWindow = new PopupWindow(contentView, 300, 500);
//        // 可以获取焦点
        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(tvSelect);
        tv_smart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSelect.setText("智能排序");
                keyword = "2";
                page= 1;//默认请求第一页数据
                PotTribune();
                popupWindow.dismiss();
            }
        });

        order_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // ToastUtils.showToast(getActivity(),"时间排序");
                tvSelect.setText("时间排序");
                keyword = "2";
                page= 1;//默认请求第一页数据
                PotTribune();
                popupWindow.dismiss();
            }
        });
        order_Hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // ToastUtils.showToast(getActivity(),"热度排序");
                tvSelect.setText("热度排序");
                keyword = "1";
                page= 1;//默认请求第一页数据
                PotTribune();
                popupWindow.dismiss();
            }
        });
    }
    public void GetStatusLogin2(){
        final MyAlertDialog myAlertDialog = new MyAlertDialog(getActivity());
        myAlertDialog.setTitle("提示");
        myAlertDialog.setMessage("您还没有登录？");
        myAlertDialog.SetCancelButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

            }
        });
        myAlertDialog.SetDetermineButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAlertDialog.Dismiss();
            }
        });

        myAlertDialog.Show();
    }
}