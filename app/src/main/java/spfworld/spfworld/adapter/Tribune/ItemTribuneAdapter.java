package spfworld.spfworld.adapter.Tribune;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import ab.util.AbToastUtil;
import spfworld.spfworld.R;
import spfworld.spfworld.activity.Tribune.ImagePagerActivity;
import spfworld.spfworld.entity.Tribune.Tribune;
import spfworld.spfworld.widget.percent.PercentLinearLayout;
import android.widget.RelativeLayout;
import spfworld.spfworld.widget.percent.view.CircleImageView;
import android.widget.TextView;
import spfworld.spfworld.widget.percent.view.MyGridView2;
import android.widget.ImageView;
import android.widget.CheckBox;

import com.nostra13.universalimageloader.core.ImageLoader;

public class ItemTribuneAdapter extends BaseAdapter {

    private List<Tribune.DataBean> objects;

    private Context context;
    private LayoutInflater layoutInflater;

    public ItemTribuneAdapter(Context context, List<Tribune.DataBean> objects) {
        this.context = context;
        this.objects=objects;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Tribune.DataBean getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_tribune, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((Tribune.DataBean)getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(Tribune.DataBean object, ViewHolder holder) {
        ImageLoader.getInstance().displayImage("http://"+object.getHeadpic(),holder.iv);
        holder.tvTitle.setText(object.getUsername());
        holder.tvContent.setText(object.getTitle());
        holder.tvInfo.setText(object.getComment());
        holder.tvMinute.setText(object.getCreated_at());
        holder.tvLove.setText(object.getZan());
        holder.tvComment.setText(object.getReplycount());
        if (object.getPic()!=null&&object.getPic().size()>0){
            holder.mGrid.setVisibility(View.VISIBLE);
            final String[] urls =object.getPic().toArray(new String[object.getPic().size()]);
            holder.mGrid.setAdapter(new GridAdapter(context, object.getPic()));
            holder.mGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    enterPhotoDetailed(urls, position);
                    //AbToastUtil.showToast(context,"" +position);
                }
            });
        }else {
            holder.mGrid.setVisibility(View.GONE);
        }
    }
    /**
     * 进入图片详情页
     * @param
     * @param position 角标
     */
    protected void enterPhotoDetailed(String[] urls, int position) {
        Intent intent = new Intent(context, ImagePagerActivity.class);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        context.startActivity(intent);
    }

    protected class ViewHolder {
        private PercentLinearLayout allLayout;
    private RelativeLayout rlTribuneItem;
    private CircleImageView iv;
    private TextView tvTitle;
    private TextView tvContent;
    private TextView tvInfo;
    private MyGridView2 mGrid;
    private TextView tvMinute;
    private TextView minuteTv;
    private TextView tvCitySet;
    private TextView tvComment;
    private ImageView ivComment;
    private TextView tvLove;
    private CheckBox ivLove;

        public ViewHolder(View view) {
            allLayout = (PercentLinearLayout) view.findViewById(R.id.all_layout);
            rlTribuneItem = (RelativeLayout) view.findViewById(R.id.rl_tribune_item);
            iv = (CircleImageView) view.findViewById(R.id.iv);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            tvContent = (TextView) view.findViewById(R.id.tv_content);
            tvInfo = (TextView) view.findViewById(R.id.tv_info);
            mGrid = (MyGridView2) view.findViewById(R.id.mGrid);
            tvMinute = (TextView) view.findViewById(R.id.tv_minute);
            minuteTv = (TextView) view.findViewById(R.id.minute_tv);
            tvCitySet = (TextView) view.findViewById(R.id.tv_city_set);
            tvComment = (TextView) view.findViewById(R.id.tv_comment);
            ivComment = (ImageView) view.findViewById(R.id.iv_comment);
            tvLove = (TextView) view.findViewById(R.id.tv_love);
            ivLove = (CheckBox) view.findViewById(R.id.iv_love);
        }
    }
}
