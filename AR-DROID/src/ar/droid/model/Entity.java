package ar.droid.model;

import ar.droid.location.GeoPoint;

public class Entity {
	String name;
	String description;
	String url;
	GeoPoint geoPoint;
	String photoUrl;
	TypeEntity typeEntity;
	
	public TypeEntity getTypeEntity() {
		return typeEntity;
	}
	public void setTypeEntity(TypeEntity typeEntity) {
		this.typeEntity = typeEntity;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public GeoPoint getGeoPoint() {
		return geoPoint;
	}
	public void setGeoPoint(GeoPoint geoPoint) {
		this.geoPoint = geoPoint;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
