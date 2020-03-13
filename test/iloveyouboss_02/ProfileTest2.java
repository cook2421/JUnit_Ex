package iloveyouboss_02;

/*
    테스트 코드 리팩토링
    테스트 코드는 메소드 별로 각각 컴파일되며, 순서도 없다.
    @Before 어노테이션은 먼저 실행되어 공통적인 부분을 초기화할 수 있게 해준다.
*/



import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProfileTest2 {

    private Profile profile;
    private BooleanQuestion question;
    private Criteria criteria;


    @Before
    public void create(){
        profile = new Profile("Bull Hockey, Inc.");
        question = new BooleanQuestion(1, "Got bonuses?");
        criteria = new Criteria();
    }


    @Test
    public void matchAnswersFalseWhenMustMatchCriteriaNotMet(){
        profile.add(new Answer(question, Bool.FALSE));
        criteria.add(new Criterion(new Answer(question, Bool.TRUE), Weight.MustMatch));

        boolean matches = profile.matches(criteria);

        assertFalse(matches);
    }

    @Test
    public void matchAnswersTrueForAnyDontCareCriteria(){
        profile.add(new Answer(question, Bool.FALSE));
        criteria.add(new Criterion(new Answer(question, Bool.TRUE), Weight.DontCare));

        boolean matches = profile.matches(criteria);

        assertTrue(matches);
    }
}
