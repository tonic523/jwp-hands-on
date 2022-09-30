package nextstep.study.di.stage3.context;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * 스프링의 BeanFactory, ApplicationContext에 해당되는 클래스
 */
class DIContainer {

    private final Set<Object> beans;

    public DIContainer(final Set<Class<?>> classes) {
        this.beans = new HashSet<>();
        try {
            registerBean(classes);
            dependencyInjection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registerBean(Set<Class<?>> classes) throws Exception {
        for (Class<?> clazz : classes) {
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            beans.add(constructor.newInstance());
        }
    }

    private void dependencyInjection() throws IllegalAccessException {
        for (Object bean : beans) {
            inject(bean);
        }
    }

    private void inject(Object bean) throws IllegalAccessException{
        Constructor<?>[] constructors = bean.getClass()
                .getConstructors();

        Set<Field> declaredFields = Set.of(bean.getClass()
                .getDeclaredFields());

        for (Constructor<?> constructor : constructors) {
            for (Class<?> parameterType : constructor.getParameterTypes()) {
                Object parameterBean = getBean(parameterType);
                Optional<Field> optionalField = declaredFields.stream()
                        .filter(f -> f.getType().isAssignableFrom(parameterBean.getClass()))
                        .findAny();
                if (optionalField.isPresent()) {
                    Field field = optionalField.get();
                    field.setAccessible(true);
                    field.set(bean, parameterBean);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(final Class<T> aClass) {
        if (contains(aClass)) {
            return (T) beans.stream()
                    .filter(bean -> aClass.isAssignableFrom(bean.getClass()))
                    .findAny()
                    .orElseThrow();
        }
        return null;
    }

    private <T> boolean contains(Class<T> aClass) {
        return beans.stream()
                .anyMatch(bean -> aClass.isAssignableFrom(bean.getClass()));
    }
}
