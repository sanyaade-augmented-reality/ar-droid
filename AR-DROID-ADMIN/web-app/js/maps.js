var mapInstance;
var markersArray = [];

/**
 * Inicializa un mapa de google maps.
 * 
 * @param lat:
 *            latitud inicial del mapa
 * @param long:
 *            longitud inicial del mapa
 * @param zoom:
 *            zoom del mapa
 * @return void
 */
function initializeMap(id_map, lat, long, zoom) {
	if(zoom == null || zoom == '') zoom = 10;
	if(lat == null || lat == '') lat = -34.921;
	if(long == null || long == '') long = -57.955;
	var latlng = new google.maps.LatLng(lat, long);
    var options = {
    	zoom: zoom,
    	center: latlng,
    	mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    mapInstance = new google.maps.Map(document.getElementById(id_map), options);
}

function placeMarker(location, reset) {
	var marker = new google.maps.Marker({
		position: location, 
		map: mapInstance
	});
	if(reset){
		deleteOverlays();
	}
	addMarker(marker);
	showOverlays();
}

function addMarker(marker) {
	markersArray.push(marker);
}

// Removes the overlays from the map, but keeps them in the array
function clearOverlays() {
	if (markersArray) {
		for (var i=0; i<markersArray.length; i++){
			markersArray[i].setMap(null);
		}
	}
}

// Shows any overlays currently in the array
function showOverlays() {
	if (markersArray) {
		for (var i=0; i<markersArray.length; i++){
	    	markersArray[i].setMap(mapInstance);
	    }
	}
}

// Deletes all markers in the array by removing references to them
function deleteOverlays() {
	if (markersArray) {
		for (var i=0; i<markersArray.length; i++){
			markersArray[i].setMap(null);
	    }
	    markersArray.length = 0;
	}
}