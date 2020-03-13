package iloveyouboss_03.test.scratch;

import org.junit.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.*;

public class AssertHamcrestTest {

/*
*
*  부동소수점 수를 두 개 비교
*
* */
    @Test
    public void assertDoubleEqual(){
        assertThat(2.32 * 3, equalTo(6.96));
    }
    // 두 개의 float 혹은 double 양을 비교할 때는 두 수가 벌어질 수 있는 공차
    // 또는 허용 오차를 지정해야 한다.

    @Test
    public void assertWithTolerance(){
        assertTrue(Math.abs((2.32 * 3) - 6.96) < 0.0005);
    }
    // 가독성이 떨어짐.
    // 'isCloseTo'라는 햄크레스트 매처 사용 가능

    @Test
    public void assertDoublesCloseTo(){
        assertThat(2.32 * 3, closeTo(6.96, 0.0005));
    }
}
