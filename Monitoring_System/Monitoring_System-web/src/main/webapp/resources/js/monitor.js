//google.charts.load("current", {packages:['corechart', 'controls', 'timeline']});
//google.charts.setOnLoadCallback(drawChart);
//
//function drawChart() {
//    var json = document.getElementById("hiddenField");
//    var container = document.getElementById('timeline1');
//    var chart = new google.visualization.Timeline(container);
//    console.log(json.value);
//
//    var obj = JSON.parse(json.value);
//    
//    var dataTable = new google.visualization.DataTable();
//    dataTable.addColumn({type: 'string', id: 'State'});
//    dataTable.addColumn({type: 'string', id: 'Name'});
//    dataTable.addColumn({type: 'date', id: 'Start'});
//    dataTable.addColumn({type: 'date', id: 'End'});
//    //


google.charts.load("current", {packages: ['corechart', 'controls', 'timeline']});
google.charts.setOnLoadCallback(drawChart);

function drawChart() {
    var container = document.getElementById('timeline1');
    var chart = new google.visualization.Timeline(container);
    
    //var json = document.getElementById("hiddenField");
    //console.log(json.value);

    //var obj = JSON.parse(json.value);

    var dataTable = new google.visualization.DataTable();
    dataTable.addColumn({type: 'string', id: 'State'});
    dataTable.addColumn({type: 'string', id: 'Name'});
    dataTable.addColumn({type: 'date', id: 'Start'});
    dataTable.addColumn({type: 'date', id: 'End'});
    //console.log(obj);
    
    dataTable.addRows([
        ['Online', 't', new Date(0, 0, 0, 12, 0, 0), new Date(0, 0, 0, 13, 30, 0)],
        ['Online', 'f', new Date(0, 0, 0, 13, 30, 0), new Date(0, 0, 0, 14, 0, 0)],
        ['Online', 't', new Date(0, 0, 0, 14, 0, 0), new Date(0, 0, 0, 15, 0, 0)],
        ['Online', 'f', new Date(0, 0, 0, 15, 0, 0), new Date(0, 0, 0, 16, 0, 0)],
        ['Online', 't', new Date(0, 0, 0, 16, 0, 0), new Date(0, 0, 0, 17, 30, 0)],
        ['Online', 'f', new Date(0, 0, 0, 17, 30, 0), new Date(0, 0, 0, 18, 0, 0)],
        ['Endpoints', 't', new Date(0, 0, 0, 12, 0, 0), new Date(0, 0, 0, 14, 30, 0)],
        ['Endpoints', 'f', new Date(0, 0, 0, 14, 30, 0), new Date(0, 0, 0, 15, 30, 0)],
        ['Endpoints', 't', new Date(0, 0, 0, 15, 30, 0), new Date(0, 0, 0, 17, 30, 0)],
        ['Endpoints', 'f', new Date(0, 0, 0, 17, 30, 0), new Date(0, 0, 0, 18, 0, 0)],
        ['Features', 'f', new Date(0, 0, 0, 12, 0, 0), new Date(0, 0, 0, 13, 0, 0)],
        ['Features', 't', new Date(0, 0, 0, 13, 0, 0), new Date(0, 0, 0, 18, 0, 0)]
    ]);

    var colors = [];

    var colorMap = {
        t: 'green',
        f: 'red'
    }

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
    drawChart();
});