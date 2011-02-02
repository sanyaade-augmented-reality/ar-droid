package ar.droid.driving;

import java.util.ArrayList;
import java.util.List;

public class RoutePath {

	private List<Route> routes = new ArrayList<Route>();

	public List<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}
	
	public Route getRoute(){
		if (getRoutes().size()>0) return getRoutes().get(0);
		else return null;
	}
}
