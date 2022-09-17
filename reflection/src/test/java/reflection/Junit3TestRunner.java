package reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;

class Junit3TestRunner {

    @Test
    void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;
        // TODO Junit3Test에서 test로 시작하는 메소드 실행
        Constructor<Junit3Test> constructor = clazz.getDeclaredConstructor();
        Junit3Test junit3Test = constructor.newInstance();
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            declaredMethod.invoke(junit3Test);
        }
    }

    @Test
    void static_method() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;
        Method four = clazz.getDeclaredMethod("four");
        four.invoke(Junit3Test.class);
    }
}
