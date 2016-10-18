package spfworld.spfworld.activity.User;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import spfworld.spfworld.R;
import spfworld.spfworld.fragment.find.user.AcutFragment;

/**
 * Created by Administrator on 2016/10/16.
 */
public class AcutActivity extends AppCompatActivity {
    private AcutFragment acutFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main);
        FragmentManager fragmt = getSupportFragmentManager();
        FragmentTransaction transaction = fragmt.beginTransaction();
        if (null == acutFragment) {
            acutFragment = new AcutFragment();
            transaction.add(R.id.id_frag_user_date_layout, acutFragment);
            transaction.commit();
        } else {
            transaction.show(acutFragment);
        }
    }
}
