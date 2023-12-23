package gx.qlexp.constant;

import lombok.Getter;

/**
 * @author shenping
 * 2018-12-21
 */
public enum Operators {
    lt("<", "С��"),
    gt(">", "����"),
    le("<=", "С�ڵ���"),
    ge(">=", "���ڵ���"),
    eq("==", "����"),
    ne("!=", "������"),
    ;

    @Getter
    private String symbol;

    @Getter
    private String desc;

    Operators(String symbol, String desc) {
        this.symbol = symbol;
        this.desc = desc;
    }
}
