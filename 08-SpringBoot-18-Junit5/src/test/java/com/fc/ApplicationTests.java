package com.fc;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

//加载Spring容器环境
@SpringBootTest
@DisplayName("Junit5测试注解类")
class ApplicationTests {

    @Test
    @DisplayName("测试DisplayName注解")
    void test() {
        System.out.println("测试DisplayName注解");
    }

    @Test
    @Timeout(value = 4,unit = TimeUnit.SECONDS)
    @DisplayName("测试超时注解")
    void testTimeout() {
        try {
            Thread.sleep(3000);
            System.out.println("测试超时注解@Timeout");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Tag("暖男浩")
    @Test
    @DisplayName("测试@Tag标签注解")
    void testTag() {
        System.out.println("我是暖男");
    }

    @RepeatedTest(3)
    void testRepeatedTest() {
        System.out.println("今天又是想你的一天");
    }

    @Disabled
    @Test
    @DisplayName("测试Disabled")
    void testDisabled() {
        System.out.println("测试Disabled");
    }

    @BeforeEach
    void testBeforeEach() {
        System.out.println("开始测试");
    }

    @BeforeAll
    static void testBeforeAll() {
        System.out.println("所有测试方法测试之前，只执行一次");
    }

    @AfterAll
    static void testAfterAll() {
        System.out.println("所有测试方法测试之后，只执行一次");
    }

    @AfterEach
    void testAfterEach() {
        System.out.println("测试结束");
    }
}
