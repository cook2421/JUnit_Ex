package iloveyouboss_03.test.scratch;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class AssertTest {

    class InsufficientFundsException extends RuntimeException {
        public InsufficientFundsException(String message){
            super(message);
        }
        private static final long serialversionUID = 1L;
    }


    class Account{
        int balance;
        String name;

        Account(String name){
            this.name = name;
        }

        void deposit(int dollars){
            balance += dollars;
        }

        void withdraw(int dollars){
            if(balance < dollars){
                throw new InsufficientFundsException("balance only " + balance);
            }
            balance -= dollars;
        }

        public String getName(){
            return name;
        }

        public int getBalance(){
            return balance;
        }

        public boolean hasPositiveBalance(){
            return balance > 0;
        }
    }


    private Account account;


    @Before
    public void createAccount(){
        account = new Account("an account name");
    }




/*
*
*   assertTrue
*
* */
    @Test
    public void hasPositiveBalance(){
        account.deposit(50);
        assertTrue(account.hasPositiveBalance());
    }

/*
 *
 *   assertThat
 *
 * */
    @Test
    public void depositIncreasesBalance(){
        int initialBalance = account.getBalance();
        account.deposit(100);
        assertTrue(account.getBalance() > initialBalance);
        assertThat(account.getBalance(), equalTo(100));
    }

    @Test
    public void matchesFailure(){
        assertThat(account.getName(), startsWith("xyz"));
    }

/*
 *
 *   중요한 햄크레스트 매처
 *
 * */
    @Test
    public void comparesArraysFailing(){
        assertThat(new String[]{"a", "b"}, equalTo(new String[]{"a", "b"}));
        assertThat(new String[]{"a", "b", "c"}, equalTo(new String[]{"a", "b"}));
    }

/*
 *
 *   'is' decorator
 *
 * */
    @Test
    public void variousMatcherTests(){
        Account account = new Account("my big fat acct");
        /* 장식자(decorator) 사용 */
        assertThat(account.getName(), is(equalTo("my big fat acct")));
        assertThat(account.getName(), is("my big fat acct"));
        assertThat(account.getName(), equalTo("my big fat acct"));  // best

        /* 부정할 때 */
        assertThat(account.getName(), not(equalTo("plunderings")));
    }

/*
 *
 *   null 체크법
 *
 * */
    @Test
    public void nullCheckTests(){
        Account account = new Account(null);
        assertThat(account.getName(), is(not(nullValue())));
        assertThat(account.getName(), is(notNullValue()));

        assertThat(account.getName(), equalTo("Cook"));
        /*
        null이 아닌 값을 자주 검사하는 것은 설계 문제이거나 지나치게 걱정하는 것이다.
        많은 경우에 null 검사는 불필요하고 가치가 없다.
        account.getName()이 null을 반환한다면 equalTo("~")는 테스트하지 않는다.
        대신 오류가 발생하며 테스트 실패는 발생하지 않는다.
        */
    }

/*
 *
 *   단언의 근거를 설명해주는 message 인자
 *
 * */
    @Test
    public void testWithWorthlessAssertionComment(){
        account.deposit(50);
        assertThat("account balance is 100", account.getBalance(), equalTo(50));
    }
    // 이 설명문은 테스트를 정확하게 설명하지 않는 정도가 아니라 거짓말 가깝다.
    // 테스트를 코드 자체만으로 이해할 수 있게 작성하는 것이 더 좋다.




/*
 *
 *   <예외를 기대하는 세 가지 방법>
 *
 *   1. 단순한 방식: 어노테이션 사용
 *   2. 옛 방식: try/catch와 fail
 *   3. 새로운 방식: ExpectedException 규칙
 *
 * */
    // 1. 단순한 방식: 어노테이션 사용
    @Test(expected = InsufficientFundsException.class)
    public void throwsWhenWithdrawingTooMuch(){
        account.withdraw(100);
    }

    // 2. 옛 방식: try/catch와 fail
    @Test
    public void throwsWhenWithdrawingTooMuchTry(){
        try{
            account.withdraw(100);
            fail();
        } catch (InsufficientFundsException expected){
            System.out.println(expected);
            assertThat(expected.getMessage(), equalTo("balance only 0"));
        }
    }

    // 3. 새로운 방식: ExpectedException 규칙
    @Rule
    public ExpectedException thrown = ExpectedException.none();
        // AOP와 유사한 기능
        // 테스트의 셋업 단계에서 나머지 테스트를 실행할 때 발생할 수 있는 일을 규칙에 알린다.
    @Test
    public void exceptionRule(){
        thrown.expect(InsufficientFundsException.class);   // thrown 규칙 인스턴스는 InsufficientFundsException 예외가 발생함을 알려준다.
        thrown.expectMessage("balance only 0");   /* 예외 객체에 적절한 메시지가 포함되어 있는지 검사하길 원하여
                                                              thrown 규칙에 다른 기대사항을 지정했다. */

        account.withdraw(100);
    }


/*
 *
 *   예외 무시
 *
 * */
    @Test
    public void readsFromTestFile() throws IOException {
        String filename = "test.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write("test data");
        writer.close();
        // ...
    }
}
