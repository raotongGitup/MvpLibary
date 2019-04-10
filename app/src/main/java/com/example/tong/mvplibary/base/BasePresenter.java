package com.example.tong.mvplibary.base;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

/**
 * Created by tong on 2019/2/27.
 */

public class BasePresenter<V extends BaseView, M extends BaseMoudle> {

    private V mView, mProxy;
    private M mmoudle;


    public void Attach(final V mView) throws IllegalAccessException, InstantiationException {
        // 绑定view
        this.mView = mView;

        // 动态代理解决每次数据返回时每次判断view
        mProxy = (V) Proxy.newProxyInstance(mView.getClass().getClassLoader(), mView.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (mView == null) {
                    return null;
                }


                return method.invoke(mView, args);
            }
        });
        // 利用反射去动态的创建moudle
        Type[] params = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        // 最好判断下类型
        if (((Class) params[1]).newInstance().getClass().isAssignableFrom(BaseMoudle.class)) {
            mmoudle = (M) ((Class) params[1]).newInstance();
        } else {
            throw new RuntimeException("请添加正确的moudle类型" + this.getClass().getName());
        }

    }

    public void Datach() {

        this.mView = null;
        this.mProxy = null;
    }

    public V getView() {

        return mProxy;
    }

    public M getMoudle() {

        return mmoudle;
    }
}
