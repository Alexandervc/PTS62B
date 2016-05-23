function initMap() {
    var divs = document.getElementsByClassName("map");
    
    Array.prototype.forEach.call(divs, function(el) {
        var id = el.id;
        var data = document.getElementById(id + "hidden");
        alert(data);
        
        var map = new google.maps.Map(document.getElementById(id), {
            zoom: 3,
            center: {lat: 0, lng: -180},
            mapTypeId: google.maps.MapTypeId.ROADMAP
        });

        var coordinates = [
            {lat: 37.772, lng: -122.214},
            {lat: 21.291, lng: -157.821},
            {lat: -18.142, lng: 178.431},
            {lat: -27.467, lng: 153.027}
        ];

        var path = new google.maps.Polyline({
            path: coordinates,
            geodesic: true,
            strokeColor: '#FF0000',
            strokeOpacity: 1.0,
            strokeWeight: 2
        });

        path.setMap(map);
    });
}

google.maps.event.addDomListener(window, 'load', initMap);