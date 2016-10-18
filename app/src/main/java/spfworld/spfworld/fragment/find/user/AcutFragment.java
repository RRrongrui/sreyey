package spfworld.spfworld.fragment.find.user;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xutils.view.annotation.Event;
import org.xutils.x;

import spfworld.spfworld.R;
import spfworld.spfworld.utils.SharedHelper;
import spfworld.spfworld.utils.dialog.MyAlertDialog;

/**
 * Created by Administrator on 2016/10/16.
 */
public class AcutFragment extends Fragment {
    private Context context;
    private View view;private SharedHelper sharedHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_data_acut,container,false);
        x.view().inject(this,view);
        sharedHelper=new SharedHelper(getActivity());

        context = getActivity();
        return view;
    }
    @Event(value={R.id.activity_back,
            R.id.rl_phont,R.id.rl_nicheng,
            R.id.rl_gengder,R.id.rl_brithday,
            R.id.rl_city,R.id.rl_signature,
            R.id.avatar_change_take_picture_layout,
            R.id.avatar_change_choose_image_layout,
            R.id.ll_cancle,R.id.tv_button},
            type=View.OnClickListener.class)


    private void mClick(View v) {
        switch (v.getId()) {
            case R.id.activity_back://回退
                final MyAlertDialog myAlertDialog = new MyAlertDialog(getActivity());
                myAlertDialog.setTitle("提示");
                myAlertDialog.setMessage("是否退出?");
                myAlertDialog.SetCancelButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().finish();
                    }
                });
                myAlertDialog.SetDetermineButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myAlertDialog.Dismiss();
                    }
                });
                myAlertDialog.Show();
                break;
            case R.id.rl_phont:
                break;
            case R.id.rl_qq:
                break;
            case R.id.rl_weixin:
                break;
            default:
                break;
        }
    }
}
