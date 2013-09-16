package com.gap.metrics;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:spring-config.xml")
public class SpringAppTests {
//    @Autowired
//    private HelloService helloService;

//    @Test
    public void testSayHello() {
//        Assert.assertEquals("Hello world!", helloService.sayHello());
    }

    @Test
    public void testSample(){
        List<String> list = new ArrayList<String>();
        list.add(null);
        list.add(null);
        list.add(null);
        System.out.println(list.toString());
        Assert.assertEquals(list.size(), 3);
    }

}
