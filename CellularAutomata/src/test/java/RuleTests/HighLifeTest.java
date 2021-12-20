package RuleTests;

import static com.google.common.truth.Truth.assertThat;

import Strategy.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class HighLifeTest {

    // Testing if the highLife rule set is satisfied
    @Test
    public void highLifeTest(){
        Rule rule = Rule.highLife();

        for( int i = 0; i < 9; i++){
            assertThat(rule.cellAliveNextGen(i, true)).isEqualTo(i == 3 || i == 2);
        }

        for( int i = 0; i < 9; i++){
            assertThat(rule.cellAliveNextGen(i, false)).isEqualTo(i == 3 || i == 6);
        }
    }
}
