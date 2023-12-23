package gx.qlexp.function;

import gx.qlexp.annotation.FunctionMethod;
import gx.qlexp.constant.FunctionType;
import gx.qlexp.annotation.FunctionMetaData;
import gx.qlexp.annotation.FunctionParam;
import org.springframework.stereotype.Component;

@Component
@FunctionMetaData(value = "testFunction2", type = FunctionType.FILTER)
public class TestFunction2 {
    @FunctionMethod(desc = "any奥格人群", render = "买家任意{命中|不命中}{tag}")
    public boolean anyMatch(@FunctionParam(fromContext = true, name = "buyerId") Long userId,
                            @FunctionParam(desc = "奥格人群(奥格人群)", name = "tag") String tag) {
        return false;
    }
}