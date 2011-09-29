package ar.droid.admin;

class ActivityService {
	static transactional = true
	
	def Entity saveActivity(params) {
		def activityInstance = new Activity()
		activityInstance.properties = params
		def canSave = false
		
		// ubicación de la actividad
		if(params.positionSameAsEvent){
			def event = Event.get(params.event.id)
			activityInstance.geoPoint = entity.geoPoint
			canSave = true
		}
		else{
			// validar posición
			if(params.latitude == null || ''.equals(params.latitude) || params.longitude == null || ''.equals(params.longitude)){
				activityInstance.errors.rejectValue('geoPoint', 'Debe seleccionar la ubicación')
			}
			else {
				// armar geopoint
				activityInstance.geoPoint.latitude = new BigDecimal(params.latitude)
				activityInstance.geoPoint.longitude = new BigDecimal(params.longitude)
				activityInstance.geoPoint.altitude = new BigDecimal(params.altitude)
				canSave = true
			}
		}	
		if(canSave)
			activityInstance.save(flush: true)
			
		return activityInstance;
	}
	
	def Entity updateActivity(params) {
		def activityInstance = Entity.get(params.id)
		if (activityInstance){
			if (params.version) {
				def version = params.version.toLong()
				if (activityInstance.version > version) {
					activityInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'entity.label', default: 'Entity')] as Object[], "Otro usuario modifico el objeto")
					return entityInstance
				}
			}
			def canSave = false
			
			// ubicación de la actividad
			if(params.positionSameAsEvent){
				def event = Event.get(params.event.id)
				activityInstance.geoPoint = entity.geoPoint
				canSave = true
			}
			else{
				// validar posición
				if(params.latitude == null || ''.equals(params.latitude) || params.longitude == null || ''.equals(params.longitude)){
					activityInstance.errors.rejectValue('geoPoint', 'Debe seleccionar la ubicación')
				}
				else {
					// armar geopoint
					activityInstance.geoPoint.latitude = new BigDecimal(params.latitude)
					activityInstance.geoPoint.longitude = new BigDecimal(params.longitude)
					activityInstance.geoPoint.altitude = new BigDecimal(params.altitude)
					canSave = true
				}
			}
			if(canSave){
				activityInstance.properties = params
				activityInstance.save(flush: true)
			}
		}
		return entityInstance
	}
	
	def synchronizeEvents(entityInstance,params){
		entityInstance.readerActivity.synchronizeEvents(entityInstance,params);
	}
}
