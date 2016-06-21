google.charts.load("current", {packages: ['corechart', 'controls', 'timeline']});
google.charts.setOnLoadCallback(drawCharts);
var Ass;
var Vs;
var Rad;

function drawChart(json) {
    if(json === undefined) return;
    var obj = [];
    for(var i = 0; i < json.length; i++) {
        obj[i] = JSON.parse(json[i]);
    }
    //console.log(obj);
    systemName = obj[0][0];
    var container = document.getElementById(systemName);
    var chart = new google.visualization.Timeline(container);    
    
    var dataTable = new google.visualization.DataTable();
    dataTable.addColumn({type: 'string', id: 'State'});
    dataTable.addColumn({type: 'string', id: 'Name'});
    dataTable.addColumn({type: 'date', id: 'Start'});
    dataTable.addColumn({type: 'date', id: 'End'});    
        
    for(var i = 0; i < obj.length; i++) {
        var o = obj[i];        
        var date1 = new Date(o[3][0], o[3][1], o[3][2], o[3][3], o[3][4], o[3][5]);
        var date2 = new Date(o[4][0], o[4][1], o[4][2], o[4][3], o[4][4], o[4][5]);
        
        dataTable.addRow([
            String(o[1]), String(o[2]), date1, date2
        ]);
    }
   
    var colors = [];

    var colorMap = {
        passed: 'green',
        failed: 'red'
    };

    for (var i = 0; i < dataTable.getNumberOfRows(); i++) {
        colors.push(colorMap[dataTable.getValue(i, 1)]);
    }

    var options = {
        colors: colors,
        groupByRowLabel: true
    };
    
    chart.draw(dataTable, options);
}

$(window).resize(function(){
    drawCharts();
});

function drawCharts() {
    drawChart(Ass);
    drawChart(Vs);
    drawChart(Rad);
}

var wsUri = getRootUri() +
    "/Monitoring_System-web/endpoint";
    function getRootUri() {
        //192.168.24.70
        return "ws://192.168.24.70:8080";
    }

var websocket;

function socketsInit() {
    websocket = new WebSocket(wsUri);
    websocket.onmessage = function(evt) {
        onMessage(evt);
    };
}

function onMessage(evt) {    
    var message = JSON.parse(evt.data);
    Ass = message.ASS;
    Vs = message.VS;
    Rad = message.RAD;
    drawCharts();
}


$( document ).ready(function() {
    socketsInit();
});



