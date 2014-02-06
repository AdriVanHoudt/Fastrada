import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: Adri
 * Date: 5/02/14
 * Time: 8:11
 */
public class AppTest {

    @Test
    public void test1() {
        Game game = new Game();
        game.roll(20);

        assertEquals("should be 20", 20, game.score());
    }


}
