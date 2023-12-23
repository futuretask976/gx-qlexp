package gx.qlexp.constant;

import lombok.Getter;

/**
 * @author shenping
 * 2018-12-21
 */
public enum Operators {
    lt("<", "小于"),
    gt(">", "大于"),
    le("<=", "小于等于"),
    ge(">=", "大于等于"),
    eq("==", "等于"),
    ne("!=", "不等于"),
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
