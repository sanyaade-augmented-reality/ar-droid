package ar.droid.model;

import ar.droid.admin.reader.ReaderNews;

import ar.droid.location.GeoPoint;

public class Entity {
	private Long id;
	private String name;
	private String description;
	private String url;
	private GeoPoint geoPoint;
	private String photoUrl;
	private TypeEntity typeEntity;
	
	private ReaderNews readerNews;
	
	public ReaderNews getReaderNews() {
		return readerNews;
	}
	public void setReaderNews(ReaderNews readerNews) {
		this.readerNews = readerNews;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entity other = (Entity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
