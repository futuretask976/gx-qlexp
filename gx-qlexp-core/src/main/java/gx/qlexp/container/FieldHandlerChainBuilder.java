package gx.qlexp.container;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import gx.qlexp.helper.ExpContextHelper;
import lombok.Builder;
import gx.qlexp.annotation.FunctionInject;
import gx.qlexp.annotation.FunctionValue;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Objects.nonNull;

/**
 * @author shenping
 * 2018-12-17
 */
@Builder
public class FieldHandlerChainBuilder {
    private static final Map<Class<?>, Function<String, ?>> defMap = Maps.newHashMap();

    static {
        defMap.put(String.class, Function.identity());
        defMap.put(Byte.class, Byte::parseByte);
        defMap.put(Short.class, Short::parseShort);
        defMap.put(Integer.class, Integer::parseInt);
        defMap.put(Long.class, Long::parseLong);
        defMap.put(Float.class, Float::parseFloat);
        defMap.put(Double.class, Double::parseDouble);
        defMap.put(Character.class, o -> o.charAt(0));
        defMap.put(Boolean.class, Boolean::parseBoolean);
        defMap.put(byte.class, Byte::parseByte);
        defMap.put(short.class, Short::parseShort);
        defMap.put(int.class, Integer::parseInt);
        defMap.put(long.class, Long::parseLong);
        defMap.put(float.class, Float::parseFloat);
        defMap.put(double.class, Double::parseDouble);
        defMap.put(char.class, o -> o.charAt(0));
        defMap.put(boolean.class, Boolean::parseBoolean);
    }
    private Map<String, FunctionFactory.ClassObj> classObjMap;
    private Object obj;
    private Field field;
    private String functionName;
    private List<FieldHandler> fieldHandlerChain;
    private ApplicationContext context;

    public static FieldHandlerChainBuilder build(Map<String, FunctionFactory.ClassObj> classObjMap) {
        FieldHandlerChainBuilder fieldHandlerChainBuilder = FieldHandlerChainBuilder.builder()
                .classObjMap(classObjMap)
                .fieldHandlerChain(Lists.newLinkedList())
                .build();
        fieldHandlerChainBuilder.fieldHandlerChain.add(fieldHandlerChainBuilder.new ValueInject());
        fieldHandlerChainBuilder.fieldHandlerChain.add(fieldHandlerChainBuilder.new ValueParserInject());
        fieldHandlerChainBuilder.fieldHandlerChain.add(fieldHandlerChainBuilder.new BeanInject());
        return fieldHandlerChainBuilder;
    }

    public void handle(Object functionObj, Field field) {
        this.field = field;
        this.obj = functionObj;
        fieldHandlerChain.stream()
                .filter(FieldHandler::match)
                .forEach(FieldHandler::handle);
    }

    private interface FieldHandler {
        boolean match();
        void handle();
    }

    private class ValueInject implements FieldHandler{
        FunctionValue functionValue;

        @Override
        public boolean match() {
            this.functionValue = field.getAnnotation(FunctionValue.class);
            return nonNull(functionValue) && !functionValue.value().startsWith("${");
        }

        @Override
        public void handle() {
            setValue(functionValue.value().substring(2, functionValue.value().length() - 1));
        }
    }

    private class ValueParserInject implements FieldHandler{
        FunctionValue functionValue;

        @Override
        public boolean match() {
            this.functionValue = field.getAnnotation(FunctionValue.class);
            return nonNull(functionValue) && functionValue.value().startsWith("${");
        }

        @Override
        public void handle() {
            setValue(ExpContextHelper.getProperty(functionValue.value().substring(2, functionValue.value().length() - 1)));
        }
    }

    private class BeanInject implements FieldHandler{
        FunctionInject functionInject;

        @Override
        public boolean match() {
            this.functionInject = field.getAnnotation(FunctionInject.class);
            return nonNull(functionInject);
        }

        @Override
        public void handle() {
            field.setAccessible(true);
            try {
                field.set(obj, Optional.ofNullable(classObjMap.get(functionInject.value()))
                        .map(FunctionFactory.ClassObj::getObj)
                        .map(ob -> (Object)ob)
                        .orElseGet(() -> ExpContextHelper.getBean(functionInject.value())));
            } catch (IllegalAccessException e) {
                throw new RuntimeException("设值异常",e);
            }
        }
    }

    private void setValue(String value) {
        field.setAccessible(true);
        try {
            field.set(obj, defMap.get(field.getDeclaringClass()).apply(value));
        } catch (IllegalAccessException e) {
            throw new RuntimeException("设值异常",e);
        }
    }
}
