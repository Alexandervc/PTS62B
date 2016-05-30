var map;

function initMap() {
    var divs = document.getElementsByClassName("map");
    
    Array.prototype.forEach.call(divs, function(div) {    
        //Get coordinates form hidden input field
        var id = div.id;
        var data = document.getElementById(id + "hidden").value;
        var coordinates = eval(data);
        
        //Create map
        map = new google.maps.Map(document.getElementById(id), {
            mapTypeId: google.maps.MapTypeId.ROADMAP
        });

        //Create polyline
        var path = new google.maps.Polyline({
            path: coordinates,
            geodesic: true,
            strokeColor: '#FF0000',
            strokeOpacity: 1.0,
            strokeWeight: 2
        });

        //Add polyline to map
        path.setMap(map);
        
        //Set default view for map
        setDefaultView();
        
        //Create event for collapsible panel
        var div_acc = div.id.replace('map_', '#');
        
        $(div_acc).on('hidden.bs.collapse', function() {
            initMap();
        });
        
        $(div_acc).on('shown.bs.collapse', function() {
            initMap();
        });
    });
}

function setDefaultView() {
    var centerPT = new google.maps.LatLng(39.399872, -8.224454);
    var zoomPT = 6;
    
    map.setCenter(centerPT);
    map.setZoom(zoomPT);

    /**
    var centerEU = new google.maps.LatLng(46.227638, 2.213749);;
    var zoomEU = 4;
    
    map.setCenter(centerEU);
    map.setZoom(zoomEU);
    **/
}

google.maps.event.addDomListener(window, 'load', initMap);
google.maps.event.addDomListener(window, 'resize', function() {
    setDefaultView() 
});