var map;
var month;
var year;

$(document).ready(function() {    
    var date = new Date();
    setDate(date.getMonth(), date.getYear());
});

function setupEvents() {
    var divs = document.getElementsByClassName("map");
    
    Array.prototype.forEach.call(divs, function(div) {
        //Create event for collapsible panel
        var div_acc = div.id.replace("map_", "#");

        $(div_acc).on("shown.bs.collapse", function() {
            initMap();
        });
    });
}

function setDate(month, year) {
    this.month = month;
    this.year = year;    
}

// Create the XHR object.
function createCORSRequest(method, url) {
    var xhr = new XMLHttpRequest();
    
    if ("withCredentials" in xhr) {
        // XHR for Chrome/Firefox/Opera/Safari.
        xhr.open(method, url, true);
    } else if (typeof XDomainRequest != "undefined") {
        // XDomainRequest for IE.
        xhr = new XDomainRequest();
        xhr.open(method, url);
    } else {
        // CORS not supported.
        xhr = null;
    }
    
    return xhr;
}

function initMap() {    
    var divs = document.getElementsByClassName("map");
    
    Array.prototype.forEach.call(divs, function(div) {
        //Get cartrackerId
        var id = div.id;
        var cartrackerId = id.substring(4, id.length);
        
        //Setup API URL
        var apiurl = "http://192.168.24.72:8080/VS-web/vsapi/cartrackers/" 
                + cartrackerId + "/coordinates?month=" + month + "&year="
                + year;
        
        //HTTP request
        var xhttp = createCORSRequest();

        //Get response
        xhttp.onload = function() {
            if (xhttp.readyState === 4 && xhttp.status === 200) {               
                var data = xhttp.responseText;
                
                //Create map
                map = new google.maps.Map(document.getElementById(id), {
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                });                
                
                if (data !== "[]") {
                    //String to json
                    var coordinates = eval(data);
                    
                    //Create polyline
                    var path = new google.maps.Polyline({
                        path: coordinates,
                        geodesic: true,
                        strokeColor: "#FF0000",
                        strokeOpacity: 1.0,
                        strokeWeight: 2
                    });

                    //Add polyline to map
                    path.setMap(map);
                }
                
                //Set default view for map
                setDefaultView();
            }
        };

        //Send request      
        if (xhttp !== null) {
            xhttp.open("GET", apiurl, true);
            xhttp.send();
        }
    });
}

function setDefaultView() {
    /**
    var centerPT = new google.maps.LatLng(39.399872, -8.224454);
    var zoomPT = 6;
    
    map.setCenter(centerPT);
    map.setZoom(zoomPT);
    **/
    
    var centerEU = new google.maps.LatLng(46.227638, 2.213749);;
    var zoomEU = 4;
    
    map.setCenter(centerEU);
    map.setZoom(zoomEU);    
}

google.maps.event.addDomListener(window, "load", setupEvents);
google.maps.event.addDomListener(window, "resize", function() {
    setDefaultView() 
});