package ar.droid.admin

class ActivityController {
	def activityService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [activityInstanceList: Activity.list(params), activityInstanceTotal: Activity.count()]
    }

    def create = {
        def activityInstance = new Activity()
		activityInstance.geoPoint = new GeoPoint()
        activityInstance.properties = params
        return [activityInstance: activityInstance]
    }

    def save = {
        def activityInstance = activityService.saveActivity(params)
        if (activityInstance.hasErrors()) {
            render(view: "create", model: [activityInstance: activityInstance])
        }
        else {
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'activity.label', default: 'Actividad'), entityInstance.id])}"
            redirect(action: "show", id: activityInstance.id)
        }
    }

    def show = {
        def activityInstance = Activity.get(params.id)
        if (!activityInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'activity.label', default: 'Actividad'), params.id])}"
            redirect(action: "list")
        }
        else {
            [activityInstance: activityInstance]
        }
    }

    def edit = {
        def activityInstance = Activity.get(params.id)
        if (!activityInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'activity.label', default: 'Actividad'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [activityInstance: activityInstance]
        }
    }

    def update = {
		def activityInstance = activityService.updateActivity(params)
		if(activityInstance.hasErrors()){
			render(view: "edit", model: [activityService: activityService])
		}
		else{
			flash.message = "${message(code: 'default.updated.message', args: [message(code: 'activity.label', default: 'Actividad'), entityInstance.id])}"
			redirect(action: "show", id: activityService.id)
		}
    }

    def delete = {
        def activityInstance = Activity.get(params.id)
        if (activityInstance) {
            try {
                activityInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'activity.label', default: 'Actividad'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'activity.label', default: 'Actividad'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'activity.label', default: 'Actividad'), params.id])}"
            redirect(action: "list")
        }
    }
}
