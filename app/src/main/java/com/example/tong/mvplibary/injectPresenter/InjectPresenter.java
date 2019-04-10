package com.example.tong.mvplibary.injectPresenter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by tong on 2019/2/27.
 */

@Target(ElementType.FIELD)  // 属性
@Retention(RetentionPolicy.RUNTIME) // 运行时
public @interface InjectPresenter {

}
