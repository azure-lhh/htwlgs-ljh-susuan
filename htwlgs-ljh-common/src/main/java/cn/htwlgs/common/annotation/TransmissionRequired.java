package cn.htwlgs.common.annotation;




import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//@Target({ElementType.TYPE,ElementType.METHOD})  //该注解修饰类中的方法，准许controller和子方法添加本注解
@Target(ElementType.METHOD) //该注解修饰类中的方法，只准许子方法添加本注解
@Retention(RetentionPolicy.RUNTIME)
public @interface TransmissionRequired {

    String value() default "";
}
