package spfworld.spfworld.fragment.find.user;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import spfworld.spfworld.R;
import spfworld.spfworld.activity.IndentDetailActivity;
import spfworld.spfworld.adapter.User.UserOrderAdapter;
import spfworld.spfworld.entity.Content;
import spfworld.spfworld.entity.User.UserOrder;
import spfworld.spfworld.utils.SharedHelper;
import spfworld.spfworld.utils.XutilsClass;
import spfworld.spfworld.widget.percent.view.MyListView;

/**我的订单
 * Created by Administrator on 2016/9/28.
 */
public class UserOrderFragment extends Fragment{
    private Context ncontext;
    private View view;
    @ViewInject(R.id.lv_order)
    private MyListView orderlist;
    private ScrollView mScrollView;
    @ViewInject(R.id.refreshScrollView)
    private PullToRefreshScrollView refreshScrollView;
    @ViewInject(R.id.activity_back)
    private RelativeLayout activity_back;
    private List<UserOrder.DataBean> userData;
    private SharedHelper sharedHelper;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_order, container, false);
        x.view().inject(this, view);
        sharedHelper = new SharedHelper(getActivity());
        ncontext = getActivity();
        GetUserOrder();
        refreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                new GetDataTask().execute();
            }
        });
        mScrollView = refreshScrollView.getRefreshableView();
        activity_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        orderlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Content.DATAINDENT=userData.get(position).getOrder_num();
                Content.currtTime=userData.get(position).getCreate_time();
                Intent intent=new Intent(getActivity(), IndentDetailActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private class GetDataTask extends AsyncTask<Void, Void, String[]> {
        @Override
        protected String[] doInBackground(Void... params) {
            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            refreshScrollView.onRefreshComplete();
            super.onPostExecute(result);
            GetUserOrder();
        }
    }
//    @Event(value={R.id.activity_back},
//            type=View.OnClickListener.class)
//    private void mClick(View v) {
//        switch (v.getId()) {
//            case R.id.activity_back://回退
//                getActivity().finish();
//                break;
//            default:
//                break;
//        }
//    }
//    private int userid = 8;
    private int page = 1;
    private void GetUserOrder(){
        String Userid=sharedHelper.ReadData("String","Userid").toString();
        XutilsClass.getInstance().getUserOrder(Userid, page, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.e("oye==成功",s);
                Gson gson = new Gson();
                UserOrder userOrder = gson.fromJson(s,UserOrder.class);
                userData=userOrder.getData();
                isMyList();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.e("oye==失败",throwable.toString());
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });

    }
    private void isMyList(){
        UserOrderAdapter userOrderAdapter = new UserOrderAdapter(getActivity(),userData);
        orderlist.setAdapter(userOrderAdapter);
    }
}
