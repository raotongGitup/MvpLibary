package com.example.tong.mvplibary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.tong.mvplibary.injectPresenter.InjectPresenter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tong on 2019/2/27.
 */

public abstract class BaseMVpActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {

    private List<BasePresenter> presenters;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        presenters = new ArrayList<>();
        initActivity();
        initView();
        initData();
    }

    protected void initActivity() {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            InjectPresenter injectPresenter = field.getAnnotation(InjectPresenter.class);
            if (injectPresenter != null) {
                // 创建注入
                Class<? extends BasePresenter> basePresenter = null;
                // 判断下类型  获取继承类判断是否是 BasePresenter 抛出异常
                if (field.getType().getClass().isAssignableFrom(BasePresenter.class)) {
                    basePresenter = (Class<? extends BasePresenter>) field.getType();
                } else {
                    throw new RuntimeException("请在正确的presenter添加注释" + this.getClass().getName());
                }
                // 创建presenter对象
                try {
                    BasePresenter presenter = basePresenter.newInstance();
                    // 绑定
                    presenter.Attach(this);
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

    protected abstract void initData();

    protected abstract void initView();

    protected abstract void setContentView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (BasePresenter presenter : presenters) {
            presenter.Datach();

        }
    }
}
