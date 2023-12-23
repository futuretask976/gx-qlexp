package gx.qlexp;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class QlExpTestor {
    public static QlExpTestor qlExpTest;
    
    public static void main(String args[]) {
        if (qlExpTest == null) {
            qlExpTest = new QlExpTestor();
        }
        qlExpTest.doTest();
    }
    
    public void doTest() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("qlexp-spring.xml");
        System.out.println(ac.getBean("person"));
    }
}
