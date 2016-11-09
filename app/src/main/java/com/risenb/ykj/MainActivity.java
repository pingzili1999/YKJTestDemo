package com.risenb.ykj;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.risenb.ykj.base.BaseAppCompatActivity;
import com.risenb.ykj.home.fragment.Home;
import com.risenb.ykj.my.fragment.My;
import com.risenb.ykj.regulation.fragment.Regulation;

public class MainActivity extends BaseAppCompatActivity {


    private Fragment[] arrFragment;
    private RadioButton[] radioButtons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initButton();

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

    }

    //显示不同Fragment
    private void showFragment(int index) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().hide(arrFragment[0]).hide(arrFragment[1]).hide(arrFragment[2]);

        if(arrFragment[index].isAdded()) {
            fragmentTransaction.show(arrFragment[index]).commitAllowingStateLoss();
        }else {
            fragmentTransaction.add(R.id.flMain, arrFragment[index]).show(arrFragment[index]).commitAllowingStateLoss();
        }
    }

    //TODO inflate the layout here
    private void initButton() {
        arrFragment = new Fragment[3];
        arrFragment[0] = new Home();
        arrFragment[1] = new Regulation();
        arrFragment[2] = new My();
        radioButtons = new RadioButton[3];
        radioButtons[0] = (RadioButton) findViewById(R.id.btnHome);
        radioButtons[1] = (RadioButton) findViewById(R.id.btnRegulation);
        radioButtons[2] = (RadioButton) findViewById(R.id.btnMy);
        RadioGroup rgMain = (RadioGroup) findViewById(R.id.rgMain);
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.btnHome:
                        showFragment(0);
                        if (Build.VERSION.SDK_INT >= 21) {
                            View decorView = getWindow().getDecorView();
                            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                            decorView.setSystemUiVisibility(option);
                            getWindow().setStatusBarColor(Color.TRANSPARENT);
                        }
                        break;
                    case R.id.btnRegulation:
                        showFragment(1);
                        break;
                    case R.id.btnMy:
                        showFragment(2);
                        break;
                    default:
                        break;
                }
            }
        });
        showFragment(0);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
