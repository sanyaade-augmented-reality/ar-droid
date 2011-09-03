package ar.droid.model;

import java.util.HashMap;
import java.util.List;

import ar.droid.admin.survey.response.Summary;

public class Resource {
	
	private static Resource resource;
	
	private Resource(){
	}
	
	public List<Entity> entities = null;
	
	public List<TypeEvent> typeEvents = null;
	
	public List<TypeEntity> typeEntities = null;
	
	private HashMap<String, List<Event>> allevents = new HashMap<String, List<Event>>(); 
	
	
	
	public  List<TypeEvent> getTypeEvents(){
		if (typeEvents ==null)
			typeEvents = 	ResourceHelperFactory.createResourceHelper().getTypeEvents();
		return typeEvents;
		
	}
	
	public  List<TypeEntity> getTypeEntities(){
		if (typeEntities ==null)
			typeEntities = 	ResourceHelperFactory.createResourceHelper().getTypeEntities();
		return typeEntities;
		
	}
	public  List<Entity> getEntities(){
		if (entities ==null)
			entities = 	 ResourceHelperFactory.createResourceHelper().getEntities();
		return entities;
	}
	
	public  List<Event> getEvents(Entity entity){
		if (entity.getEvents() == null){
			entity.setEvents(ResourceHelperFactory.createResourceHelper().getEvents(entity));
		}
		return entity.getEvents();
	}
	
	public  List<Event> getEvents(String param){
		if (allevents.get(param)== null){
			allevents.put(param,ResourceHelperFactory.createResourceHelper().getEvents(param));
		}
		return allevents.get(param);
	}
	
	public Entity getEntity(Long id){
		Entity entity = new Entity(id);
		return getEntity(entity); 
	}
	

	public Entity getEntity(Entity entity){
		if (entities != null){
			int index = entities.indexOf(entity);
			if (index != -1){
				return entities.get(index);
			}
		}	
		return null;
	}
	
	public static Resource getInstance(){
		if (resource == null){
			resource = new Resource();
		}
		return resource;
	}
	
	public Summary getSummary(Event event){
		Summary ss =ResourceHelperFactory.createResourceHelper().getSummary(event); 
		return ss;
	}
	
	
	
}
