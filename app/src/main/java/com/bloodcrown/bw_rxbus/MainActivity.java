package com.bloodcrown.bw_rxbus;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bloodcrown.bw_rxbus.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mBinding;
    MainPersenter persenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        persenter = new MainPersenter(this);
        mBinding.setPersenter(persenter);
        mBinding.setBook(new Book());
        mBinding.setCat(new Cat());

    }


}
