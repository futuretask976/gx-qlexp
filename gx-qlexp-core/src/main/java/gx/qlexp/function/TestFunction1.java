package gx.qlexp.function;

import gx.qlexp.annotation.FunctionMetaData;
import gx.qlexp.annotation.FunctionMethod;
import gx.qlexp.annotation.FunctionParam;
import gx.qlexp.constant.FunctionType;
import org.springframework.stereotype.Component;

@Component
@FunctionMetaData(value = "testFunction1", type = FunctionType.FILTER)
public class TestFunction1 {
    @FunctionMethod(desc = "奥格人群", render = "买家全部{命中|不命中}{tag}")
    public boolean check(@FunctionParam(fromContext = true, name = "buyerId") Long userId,
                         @FunctionParam(desc = "奥格人群(奥格人群)", name = "tag") String tag) {
        return true;
    }
}
