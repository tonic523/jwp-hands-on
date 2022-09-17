package reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;

class Junit4TestRunner {

    @Test
    void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;

        // TODO Junit4Test에서 @MyTest 애노테이션이 있는 메소드 실행
        Constructor<Junit4Test> constructor = clazz.getDeclaredConstructor();
        Junit4Test junit4Test = constructor.newInstance();

        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            MyTest declaredAnnotation = declaredMethod.getDeclaredAnnotation(MyTest.class);
            if (declaredAnnotation != null) {
                declaredMethod.invoke(junit4Test);
            }
        }
    }
}
