package ar.droid.model;

import java.util.List;

public class Resource {
	
	private static Resource resource;
	
	private Resource(){
	}
	
	public List<Entity> entities = null;
	public List<Event> allevents = null;
	
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
	
	public  List<Event> getEvents(){
		if (allevents == null){
			allevents = ResourceHelperFactory.createResourceHelper().getEvents();
		}
		return allevents;
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
}
