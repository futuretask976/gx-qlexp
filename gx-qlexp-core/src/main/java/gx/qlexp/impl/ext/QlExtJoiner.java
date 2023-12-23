package gx.qlexp.impl.ext;

import com.google.common.collect.Lists;
import com.ql.util.express.Operator;

import java.util.List;

import static java.util.Objects.nonNull;

/**
 * @author shenping
 * 2019-02-23
 */
public class QlExtJoiner extends Operator {

    @SuppressWarnings("unchecked")
    @Override
    // TODO: 2020/2/25 一个sorter 返回 null 的情况
    public Object executeInner(Object[] list) {
        Object list1 = list[0];
        Object list2 = list[1];

        if (nonNull(list1) && list1 instanceof List && nonNull(list2) && list2 instanceof List) {
            List linkedList1 = Lists.newLinkedList((List)list1);
            List linkedList2 = Lists.newLinkedList((List)list2);
            linkedList1.removeAll(linkedList2);

            List result = Lists.newLinkedList(linkedList1);
            result.addAll(linkedList2);
            return result;
        }
        return null;
    }
}
