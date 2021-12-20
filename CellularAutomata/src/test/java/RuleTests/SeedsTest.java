package RuleTests;

import static com.google.common.truth.Truth.assertThat;

import Strategy.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class SeedsTest {

    // Testing if seeds rule set is satisfied
    @Test
    public void seedsTest(){
        Rule rule = Rule.seeds();

        for( int i = 0; i < 9; i++){
            assertThat(rule.cellAliveNextGen(i, true)).isEqualTo(false);
        }

        for( int i = 0; i < 9; i++){
            assertThat(rule.cellAliveNextGen(i, false)).isEqualTo(i == 2);
        }
    }
}
