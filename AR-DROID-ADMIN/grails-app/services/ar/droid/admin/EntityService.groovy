package ar.droid.admin;

import com.ibm.icu.text.SimpleDateFormat

import ar.droid.admin.reader.ReaderNews
import ar.droid.admin.reader.ReaderActivity

class EntityService {
	static transactional = true
	
	def Entity saveEntity(params) {
		def entityInstance = new Entity()
		entityInstance.properties = params
		entityInstance.readerActivity = ReaderActivity.fromString(params.readerActivity_select)
		entityInstance.readerNews = ReaderNews.fromString(params.readerNews_select)
		
		entityInstance.ultimaActualizacion = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/1979")
		entityInstance.ultimoId = 0
		
		// crear composiciones
		entityInstance.readerActivity.properties = params.readerActivity
		entityInstance.readerNews.properties = params.readerNews
		
		entityInstance.geoPoint = new GeoPoint()
		
		// validar posición
		if(params.latitude == null || ''.equals(params.latitude) || params.longitude == null || ''.equals(params.longitude)){
			entityInstance.errors.rejectValue('geoPoint', 'Debe seleccionar la ubicaci�n')
		}
		else {
			// armar geopoint
			entityInstance.geoPoint.latitude = new BigDecimal(params.latitude)
			entityInstance.geoPoint.longitude = new BigDecimal(params.longitude)
			entityInstance.geoPoint.altitude = new BigDecimal(params.altitude)
			
			entityInstance.save(flush: true)
		}
		return entityInstance;
	}
	
	def Entity updateEntity(params) {
		def entityInstance = Entity.get(params.id)
		if (entityInstance){
			if (params.version) {
				def version = params.version.toLong()
				if (entityInstance.version > version) {
					entityInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'entity.label', default: 'Entity')] as Object[], "Otro usuario modifico el objeto")
					return entityInstance
				}
			}
			def photo = entityInstance.photo;
			entityInstance.properties = params
			
			if(params.get("photo").size == 0){
				entityInstance.photo = photo
			}
			
			// modificar geopoint
			entityInstance.geoPoint.latitude = new BigDecimal(params.latitude)
			entityInstance.geoPoint.longitude = new BigDecimal(params.longitude)
			entityInstance.geoPoint.altitude = new BigDecimal(params.altitude)
			
			entityInstance.readerActivity = ReaderActivity.fromString(params.readerActivity_select)
			entityInstance.readerNews = ReaderNews.fromString(params.readerNews_select)
		
			// crear composiciones
			entityInstance.readerActivity.properties = params.readerActivity
			entityInstance.readerNews.properties = params.readerNews
			
			entityInstance.save(flush: true)
		}
		return entityInstance
	}
	
	def synchronizeEvents(entityInstance, params){
		entityInstance.readerActivity.synchronizeEvents(entityInstance, params);
	}
}
