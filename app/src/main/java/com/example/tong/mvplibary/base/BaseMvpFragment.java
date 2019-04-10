package com.example.tong.mvplibary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tong.mvplibary.injectPresenter.InjectPresenter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tong on 2019/2/28.
 */

public abstract class BaseMvpFragment<P extends BasePresenter> extends Fragment {

    private List<BasePresenter> presenters;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        presenters = new ArrayList<>();
        iniFragment();
        View view = initView();
        return view;
    }

    private void iniFragment() {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            InjectPresenter injectPresenter = field.getAnnotation(InjectPresenter.class);
            if (injectPresenter != null) {
                // 创建注入
                Class<? extends BasePresenter> basePresenter = null;
                if (field.getType().getClass().isAssignableFrom(BasePresenter.class)) {
                    basePresenter = (Class<? extends BasePresenter>) field.getType();
                } else {
                    throw new RuntimeException("请在正确的presenter添加注释" + this.getClass().getName());
                }
                // 创建presenter对象
                try {
                    BasePresenter presenter = basePresenter.newInstance();
                    // 设置filed
                    presenters.add(presenter);
                    field.setAccessible(true);
                    field.set(this, presenter);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    protected abstract void initData();

    protected abstract View initView();

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
