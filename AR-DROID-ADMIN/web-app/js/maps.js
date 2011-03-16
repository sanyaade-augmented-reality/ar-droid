var mapInstance;
var markersArray = [];
var geocoder;



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
    geocoder = new google.maps.Geocoder();
    mapInstance = new google.maps.Map(document.getElementById(id_map), options);
}

function placeMarker(location, title, reset) {
	codeLatLng(location, title, reset);
}

function addPlaceMarker(location, title, reset) {
	var marker = new google.maps.Marker({
		position: location, 
		map: mapInstance,
		title: title,
		icon: '/AR-DROID-ADMIN/images/marker/arrow.png'
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

function codeLatLng(location, title, reset) {
	var latlngStr = title.split("@",2);
    var lat = parseFloat(latlngStr[0]);
	var lng = parseFloat(latlngStr[1]);
    var latlng = new google.maps.LatLng(lat,lng);
    if (geocoder) {
    	geocoder.geocode({'latLng': latlng}, function(results, status) {
    	if (status == google.maps.GeocoderStatus.OK) {
          if (results[1]) {
        	  //alert(results[1].formatted_address);
        	   var xTitle = results[0].formatted_address;
        	   addPlaceMarker(location, xTitle, reset);
          }        
        } 
      });
    }
  }

function tableToDirection(pTable){
	var someNodeList = $(pTable);
 	var nodes =$A(someNodeList.getElementsByTagName('TR'));
	nodes.each(function(row){
		var tds = $A(row.getElementsByTagName('TD'));
		if (tds.length>0){
			var latlngStr = tds[3].innerHTML.split("@",2);
			var lat = parseFloat(latlngStr[0]);
			var lng = parseFloat(latlngStr[1]);
			var latlng = new google.maps.LatLng(lat,lng);
			var geocoder = new google.maps.Geocoder();
			if (geocoder) {
				geocoder.geocode({'latLng': latlng}, function(results, status) {
				if (status == google.maps.GeocoderStatus.OK) {
						if (results[1]) {
  		 				var xTitle = results[0].formatted_address;
  						tds[3].innerHTML  = xTitle;
   				 	}        
				} 
				});
	 	}	
	}
	});         
	
}
	
