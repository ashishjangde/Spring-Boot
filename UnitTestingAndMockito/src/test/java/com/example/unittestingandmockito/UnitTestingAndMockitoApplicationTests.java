package com.example.unittestingandmockito;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;

@Slf4j
@SpringBootTest //if we remove this annotation the test cases not affect
class UnitTestingAndMockitoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testAddingNumbers() {
        int a = 23;
        int b = 78;

        int result = sumOfTwoNumbers(a,b);

//        Assertions.assertThat(101,result);
        Assertions.assertThat(result).isEqualTo(101)
                .isCloseTo(101, Offset.offset(1));

        Assertions.assertThat("Apple").isEqualTo("Apple")
                .startsWith("Ap")
                .endsWith("le")
                .hasSize(5);

        log.info("Here is the result :- {}",result);
    }

    int sumOfTwoNumbers(int a, int b) {
       return a + b;
    }

    @BeforeAll
    static void beforeAll() {
         log.info("Before All");
    }
    @AfterAll
    static void afterAll() {
        log.info("After All");
    }

    @BeforeEach   // before each test method
    void setUp() {
        log.info("Before each test");
    }
    @AfterEach  // after each test method
    void tearDown() {
        log.info("After each test");
    }

    @Test
    void testTwo() {
        log.info("Test two");
    }
    @Test
    void testThree() {
        log.info("Test three");
    }

    @Test
    @Disabled // for disabling the test case
    @DisplayName("TestCaseOne") //for changing the name of the test case
    void testOne() {
      log.info("testOne");
    }


    @Test
    void testDivideTwoNumbers_WhenDenominatorIsZero_ThenArithmeticException(){
        int a = 56;
        int b = 0;
        Assertions.assertThatThrownBy(() -> divideTwoNumbers(a,b)).isInstanceOf(ArithmeticException.class)
        .hasMessage("Divide by zero");

    }

    double divideTwoNumbers(int d1, int d2) {
        try {
            return  d1/d2;
        }catch (ArithmeticException e) {
            log.info("Arithmetic Exception :- {}", e.getMessage());
            throw new ArithmeticException("Divide by zero");

        }
    }

}
