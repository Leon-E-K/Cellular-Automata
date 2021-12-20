package RuleTests;

import static com.google.common.truth.Truth.assertThat;

import Strategy.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class GameOfLifeTest {

    // Testing if the gameOfLife rule set is satisfied
    @Test
    public void gameOfLifeTest(){
        Rule rule = Rule.gameOfLife();

        for( int i = 0; i < 9; i++){
            assertThat(rule.cellAliveNextGen(i, true)).isEqualTo(i == 3 || i == 2);
        }

        for( int i = 0; i < 9; i++){
            assertThat(rule.cellAliveNextGen(i, false)).isEqualTo(i == 3);
        }
    }
}
