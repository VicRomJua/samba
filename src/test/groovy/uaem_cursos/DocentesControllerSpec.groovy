package uaem_cursos

import grails.test.mixin.*
import spock.lang.*

@TestFor(DocentesController)
@Mock(Docentes)
class DocentesControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null

        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
        assert false, "TODO: Provide a populateValidParams() implementation for this generated test suite"
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.docentesList
            model.docentesCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.docentes!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def docentes = new Docentes()
            docentes.validate()
            controller.save(docentes)

        then:"The create view is rendered again with the correct model"
            model.docentes!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            docentes = new Docentes(params)

            controller.save(docentes)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/docentes/show/1'
            controller.flash.message != null
            Docentes.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def docentes = new Docentes(params)
            controller.show(docentes)

        then:"A model is populated containing the domain instance"
            model.docentes == docentes
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def docentes = new Docentes(params)
            controller.edit(docentes)

        then:"A model is populated containing the domain instance"
            model.docentes == docentes
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/docentes/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def docentes = new Docentes()
            docentes.validate()
            controller.update(docentes)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.docentes == docentes

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            docentes = new Docentes(params).save(flush: true)
            controller.update(docentes)

        then:"A redirect is issued to the show action"
            docentes != null
            response.redirectedUrl == "/docentes/show/$docentes.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/docentes/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def docentes = new Docentes(params).save(flush: true)

        then:"It exists"
            Docentes.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(docentes)

        then:"The instance is deleted"
            Docentes.count() == 0
            response.redirectedUrl == '/docentes/index'
            flash.message != null
    }
}
