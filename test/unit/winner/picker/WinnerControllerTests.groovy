package winner.picker



import org.junit.*
import grails.test.mixin.*

@TestFor(WinnerController)
@Mock(Winner)
class WinnerControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/winner/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.winnerInstanceList.size() == 0
        assert model.winnerInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.winnerInstance != null
    }

    void testSave() {
        controller.save()

        assert model.winnerInstance != null
        assert view == '/winner/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/winner/show/1'
        assert controller.flash.message != null
        assert Winner.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/winner/list'


        populateValidParams(params)
        def winner = new Winner(params)

        assert winner.save() != null

        params.id = winner.id

        def model = controller.show()

        assert model.winnerInstance == winner
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/winner/list'


        populateValidParams(params)
        def winner = new Winner(params)

        assert winner.save() != null

        params.id = winner.id

        def model = controller.edit()

        assert model.winnerInstance == winner
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/winner/list'

        response.reset()


        populateValidParams(params)
        def winner = new Winner(params)

        assert winner.save() != null

        // test invalid parameters in update
        params.id = winner.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/winner/edit"
        assert model.winnerInstance != null

        winner.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/winner/show/$winner.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        winner.clearErrors()

        populateValidParams(params)
        params.id = winner.id
        params.version = -1
        controller.update()

        assert view == "/winner/edit"
        assert model.winnerInstance != null
        assert model.winnerInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/winner/list'

        response.reset()

        populateValidParams(params)
        def winner = new Winner(params)

        assert winner.save() != null
        assert Winner.count() == 1

        params.id = winner.id

        controller.delete()

        assert Winner.count() == 0
        assert Winner.get(winner.id) == null
        assert response.redirectedUrl == '/winner/list'
    }
}
