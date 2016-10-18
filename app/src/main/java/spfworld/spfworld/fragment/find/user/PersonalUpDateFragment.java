package spfworld.spfworld.fragment.find.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.xutils.common.Callback;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ab.util.AbDialogUtil;
import ab.util.AbFileUtil;
import ab.util.AbImageUtil;
import ab.util.AbLogUtil;
import ab.util.AbToastUtil;
import ab.util.ScreenUtils;
import spfworld.spfworld.R;
import spfworld.spfworld.activity.User.UserNiNameActivity;
import spfworld.spfworld.activity.User.UserSinatureActivity;
import spfworld.spfworld.entity.User.CityBean;
import spfworld.spfworld.entity.User.UserData;
import spfworld.spfworld.entity.User.UserUpDataSucceed;
import spfworld.spfworld.utils.Contants;
import spfworld.spfworld.utils.SharedHelper;
import spfworld.spfworld.utils.Utils;
import spfworld.spfworld.utils.XutilsClass;
import spfworld.spfworld.utils.dialog.MyAlertDialog;
import spfworld.spfworld.widget.percent.view.CircleImageView;
import spfworld.spfworld.widget.percent.weelutils.OptionsPickerView;
import spfworld.spfworld.widget.percent.weelutils.SexView;
import spfworld.spfworld.widget.percent.weelutils.TimePickerView;

/**
 * 我的资料设置详情
 * Created by Administrator on 2016/9/5.
 */
public class PersonalUpDateFragment extends Fragment {
    private Context context;
    private View view;
    private static final int REQUEST_PICK_PICTURE = 0x1955;
    private static final int REQUEST_TAKE_PICTURE = 0x1956;
    private static final int REQUEST_CROP_PICTURE = 0x1957;
    private static final int REQUEST_NICHENG = 1;
    private static final int REQUEST_XINBIE = 2;
    private static final int REQUEST_DAY = 3;
    private static final int REQUEST_CITY = 4;
    private static final int REQUEST_SIGNATURE = 5;
    private static final int REQUEST_PHOTO = 6;
    private Handler handler = new Handler();
    // 照相机拍照得到的图片
    private String filePath;
    private File mCurrentPhotoFile;
    @ViewInject(R.id.id_headphoto)
    private CircleImageView id_headphoto;
    @ViewInject(R.id.tv_nicheng_fast)
    private TextView tvName;
    @ViewInject(R.id.tv_signature1)
    private TextView tvSignature;
    @ViewInject(R.id.tv_time1)
    private TextView tvTime;
    @ViewInject(R.id.tv_sex)
    private TextView tvSex;
    @ViewInject(R.id.tv_city_get)
    private TextView tv_city_get;
    private SharedHelper sharedHelper;
//    private CityPicker cityPicker;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_data_dao,container,false);
        x.view().inject(this,view);
        sharedHelper=new SharedHelper(getActivity());
        MgetUserData();
        context = getActivity();
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        MgetUserData();
    }
    @Event(value={R.id.bu_user_data_finsh,
            R.id.user_data,R.id.rl_nicheng,
            R.id.rl_gengder,R.id.rl_brithday,
            R.id.rl_city,R.id.rl_signature,
            R.id.avatar_change_take_picture_layout,
            R.id.avatar_change_choose_image_layout,
            R.id.ll_cancle,R.id.tv_button},
            type=View.OnClickListener.class)


    private void mClick(View v){
        switch (v.getId()){
            case R.id.bu_user_data_finsh://回退
                final MyAlertDialog myAlertDialog=new MyAlertDialog(getActivity());
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
            case R.id.tv_button://保存设置、上传个人资料
                GetUpUserData();
                break;
            case R.id.user_data://头像
                showSelectPop();
                break;
            case R.id.rl_nicheng://昵称
                 Intent intent = new Intent(getActivity(), UserNiNameActivity.class);
                startActivityForResult(intent,REQUEST_NICHENG);
                break;
            case R.id.rl_gengder://性别
                choiceBusiness();
                break;
            case R.id.rl_brithday://生日
                TimePicter();
                break;
            case R.id.rl_city://城市
//                selectCity();
                break;
            case R.id.rl_signature://签名
                intent = new Intent(getActivity(), UserSinatureActivity.class);
                startActivityForResult(intent,REQUEST_SIGNATURE);
                break;
            case R.id.avatar_change_take_picture_layout://拍照
                dismissPop();
                try {
                    String mFileName = System.currentTimeMillis() + ".jpg";
                    mCurrentPhotoFile = new File(
                            AbFileUtil.getImageDownloadDir(context), mFileName);
                    Intent intent2 = new Intent();
                    intent2.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent2.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(mCurrentPhotoFile));
                    intent2.putExtra("return-data", true);
                    startActivityForResult(intent2, REQUEST_TAKE_PICTURE);
                } catch (Exception e) {
                    AbToastUtil.showCustomerToast(context, getResources().getString(R.string.system_camera_not_found));
                }
                break;
            case R.id.avatar_change_choose_image_layout://从相册中选择
                dismissPop();
                intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                try {
                    startActivityForResult(intent, REQUEST_PICK_PICTURE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.ll_cancle://取消
                dismissPop();
                AbDialogUtil.removeDialog(viewpop);
                break;
            default:
                break;
        }
    }


//    private void selectCity() {
//        CityPicker cp=new CityPicker(getActivity(),this);
//        cp.show();
//    }

    //设置城市
//    @Override
//    public void getCity(String name) {
//
//        tv_city_get.setText(name);
//
//        String[] split = name.split(" ");
//        AbLogUtil.i("oye",name);
//        AbLogUtil.i("oye",split.toString());
//    }


    String url ;
    public  void uploadFile(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        url= Base64.encodeToString(bytes, 0);
        AbLogUtil.i("oye","图片 ===== " + url);

    }

    //个人信息网络请求
    public void MgetUserData(){
        String Userid=sharedHelper.ReadData("String","Userid").toString();
        XutilsClass.getInstance().getUserData(Userid, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.e("===user cler",s);
                Gson gson = new Gson();
                UserData userData = gson.fromJson(s,UserData.class);
                ImageLoader.getInstance().displayImage(Contants.IMAGE_BASE_URL4+userData.getData().getHeadpic(),id_headphoto);
                if (userData.getData()!=null) {
                    AbLogUtil.i("oye","Head == "+userData.getData().getHeadpic().toString());
                    tvName.setText(userData.getData().getNickname().toString());
                    tv_city_get.setText(userData.getData().getCity().toString());
                    tvTime.setText(userData.getData().getBirthday().toString());
                    tvSignature.setText(userData.getData().getSignature().toString());
                    if (userData.getData().getSex()=="0"){
                        tvSex.setText("女");
                    }else {
                        tvSex.setText("男");
                    }
//                    tvSex.setText(userData.getData().getSex().toString());
                }
            }
            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.e("==user over",throwable.toString());
            }
            @Override
            public void onCancelled(CancelledException e) {
            }
            @Override
            public void onFinished() {
            }
        });
    }

    //个人信息资料上传网络请求
    public void GetUpUserData(){
//        String s= null;
//        String s1= null;
//        String s2= null;
//        String setcity = tv_city_get.getText().toString().trim();
//        String[] str = setcity.split(" ",3);
//        for (int i = 0; i < str.length; i++) {
//            s=str[0].toString();
//            s1=str[1].toString();
//            s2=str[2].toString();
//        }
//        StringBuilder sb = new StringBuilder();
//        sb.append(s+",");
//        sb.append(s1+",");
//        sb.append(s2+",");
//        AbLogUtil.i("oye","GetUpUserData=="+sb.toString());
        String Userid=sharedHelper.ReadData("String","Userid").toString();
        XutilsClass.getInstance().getUpUserData(Userid, url, tvName.getText().toString(), "", "", "", tvSignature.getText().toString(), sex, tvTime.getText().toString(), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                if (getActivity()!=null){
                    AbLogUtil.i("oye","个人信息上传=="+s);
                    Gson gson = new Gson();
                    UserUpDataSucceed userUpDataSucceed =gson.fromJson(s,UserUpDataSucceed.class);
                    if (userUpDataSucceed.getStatus()==200){
                        getActivity().finish();
                        AbToastUtil.showToast(getActivity(),"上传成功");
                    }else {
                        Toast.makeText(getActivity(),"上传失败"+userUpDataSucceed.getMessage(),Toast.LENGTH_SHORT);
                    }
                }
            }
            @Override
            public void onError(Throwable throwable, boolean b) {
            }
            @Override
            public void onCancelled(CancelledException e) {
            }
            @Override
            public void onFinished() {
            }
        });
    }

    /**
     * 修改头像
     */
    private View viewpop;
    private PopupWindow pop;
    private void showSelectPop(){
        if(viewpop==null){
            viewpop=getActivity().getLayoutInflater().inflate(R.layout.pop_headphoto, null);
            x.view().inject(this,viewpop);
        }
        pop= AbDialogUtil.showPopWindow2(context, getActivity().getWindow().getDecorView(), viewpop, ScreenUtils.getScreenWidth(context));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_NICHENG){
            String name = data.getStringExtra("name");
            tvName.setText(name);
        }else if (requestCode ==REQUEST_SIGNATURE){
            String signature = data.getStringExtra("signature");
            tvSignature.setText(signature);
        }

        try {
            switch (requestCode) {
                case REQUEST_TAKE_PICTURE:// 相机返回结果
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                            cropImg(mCurrentPhotoFile.getAbsolutePath());
                            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoFile.getAbsolutePath());
                            if (bitmap!=null){
                                id_headphoto.setImageBitmap(bitmap);
                            }
                        }
                    }, 100);
                    break;
                case REQUEST_PICK_PICTURE:// 相册返回结果
                    if (data == null) {
                        return;
                    }
                    Uri uri = data.getData();
                    Bitmap bitmap = BitmapFactory.decodeFile(AbImageUtil.getPath(uri,getActivity()));
                    if (bitmap!=null){
                        id_headphoto.setImageBitmap(bitmap);
                    }
                    uploadFile(bitmap);
                    break;
                case REQUEST_CROP_PICTURE:// 获取裁剪后的图片路径
                    if (data != null) {
                        filePath = data.getStringExtra("PATH");
                    }
                    setBitmap();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
        }
    }

    /**
     * 设置图片
     */
    private void setBitmap() {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                if (bitmap!=null){
                    id_headphoto.setImageBitmap(bitmap);
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * 性别、时间
     */
    private TimePickerView pvTime;

    private void TimePicter() {
        // 时间选择器
        if (pvTime == null) {
            pvTime = new TimePickerView(getActivity(), TimePickerView.Type.YEAR_MONTH_DAY);
            // 控制时间范围
            Calendar calendar = Calendar.getInstance();
            // pvTime.setRange(calendar.get(Calendar.YEAR) - 20,
            // calendar.get(Calendar.YEAR));//要在setTime 之前才有效
            pvTime.setCancelable(true);
            pvTime.setRange(1990, calendar.get(Calendar.YEAR));
            pvTime.setTime(new Date());
            pvTime.setCyclic(false);
            pvTime.setCancelable(true);
            // 时间选择后回调
            pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

                @Override
                public void onTimeSelect(Date date) {
                    String time = getTime(date);
                    tvTime.setText(time);

                    Toast.makeText(getActivity(), getTime(date), Toast.LENGTH_SHORT).show();
                }
            });
        }
        pvTime.show();
    }

    private String[] sexMsgTitle;
    private SexView sexView;
    private int sex;
    private void choiceBusiness() {
        if (sexView == null) {
            sexMsgTitle = new String[] { "女", "男"};
            sexView = new SexView(getActivity());
            sexView.setCancelable(true);
            ArrayList<String> list = new ArrayList<String>();
            for (int i = 0; i < sexMsgTitle.length; i++) {
                list.add(sexMsgTitle[i]);
            }
            sexView.setPicker(list);
            sexView.setTitle("请选择性别");
            sexView.setCyclic(false, true, true);
            sexView.setSelectOptions(0);
            sexView.setOnoptionsSelectListener(new SexView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3) {
                        String s = sexMsgTitle[options1];
                        tvSex.setText(s);
                      if (options1== 0){
                          sex = 0;
                      }else {
                          sex =1;
                      }
                    Toast.makeText(getActivity(), sexMsgTitle[options1], Toast.LENGTH_SHORT).show();
                }
            });
        }
        sexView.show();
    }

    private OptionsPickerView mAdressOptions;// 地址选择
    private List<CityBean> mCitys;
    private void selectCity(){
        if (mAdressOptions == null) {
            mCitys = Utils.getCityList(context);
            mAdressOptions = AbDialogUtil.getCityOptionsPicker(context, mCitys);
            mAdressOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3) {
                    tv_city_get.setText(mCitys.get(options1).getC().get(option2).getD().get(options3).getN());
                }
            });
            mAdressOptions.show();
        } else {
            mAdressOptions.show();
        }

    }


    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        return format.format(date);
    }
    /**
     * 消失pop
     */
    private void dismissPop() {
        if(pop!=null&&pop.isShowing()){
            pop.dismiss();
        }
    }



}

