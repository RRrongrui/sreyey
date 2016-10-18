package spfworld.spfworld.widget.percent;


import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import com.ant.liao.GifView;

import spfworld.spfworld.R;

public class LoadingDialog extends AlertDialog {

	private GifView imageview;
	private AnimationDrawable mAnimation;

	public LoadingDialog(Context context, int theme) {
		super(context, theme);
	}

	public LoadingDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_loding_layout);
		imageview = (GifView) findViewById(R.id.gifView);
		imageview.setGifImage(R.drawable.loading);//加载动画
		setCancelable(false);
	}

}
