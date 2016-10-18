package spfworld.spfworld.fragment.find.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import spfworld.spfworld.R;
import spfworld.spfworld.activity.PondDetailActivity;
import spfworld.spfworld.adapter.User.MyColtFondAdapter;
import spfworld.spfworld.entity.User.ColtFond;
import spfworld.spfworld.utils.SharedHelper;
import spfworld.spfworld.utils.XutilsClass;
import spfworld.spfworld.widget.percent.view.MyListView;

/**
 * 塘口
 * Created by Administrator on 2016/9/28.
 */
public class MyColtFondFragment extends Fragment{
    private MyColtFondAdapter myColtFondAdapter;
    private List<ColtFond.DataBean> coltfondData;
    @ViewInject(R.id.fond_listview)
    private MyListView fond_listview;
    private SharedHelper sharedHelper;
    private int page =1;
    public int MID;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.my_colt_fond,container,false);
        x.view().inject(this,view);
        sharedHelper=new SharedHelper(getActivity());
        GetMyColtFondFragment();
        return view;
    }
    private void GetMyColtFondFragment(){
        final String method="1";
        String Userid=sharedHelper.ReadData("String","Userid").toString();
        XutilsClass.getInstance().getMyColtFondFragment(method,Userid, page+"", new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.i("GetMyColtFondFragment==",s);
                Gson gson = new Gson();
                ColtFond coltFond = gson.fromJson(s,ColtFond.class);
                coltfondData = coltFond.getData();
                initfond();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("GetMyColtFond==失败",throwable.toString());
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void initfond() {
        MyColtFondAdapter myColtFondAdapter = new MyColtFondAdapter(getActivity(),coltfondData);
        fond_listview.setAdapter(myColtFondAdapter);
        fond_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), PondDetailActivity.class);
                startActivity(intent);
            }
        });
        fond_listview.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

            public void onCreateContextMenu(ContextMenu menu, View v,
                                            ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(0, 0, 0, "删除");
                menu.add(0, 1, 0, "取消");

            }
        });
    }
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        MID = (int) info.id;// 这里的info.id对应的就是数据库中_id的值

        switch (item.getItemId()) {
            case 0:
                // 添加操作
                Toast.makeText(getActivity(),
                        "添加",
                        Toast.LENGTH_SHORT).show();
                break;

            case 1:
                // 删除操作
                break;

            case 2:
                // 删除ALL操作
                break;

            default:
                break;
        }

        return super.onContextItemSelected(item);

    }
}
