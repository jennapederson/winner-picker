package winner.picker



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Winner)
class WinnerTests {

    void testDefaultOfAlreadyAWinner() {
		Winner winner = new Winner()
       	assert !winner.alreadyAWinner
    }
}
