google.charts.load("current", {packages:['corechart', 'controls', 'timeline']});
google.charts.setOnLoadCallback(drawChart);

function drawChart() {
    var container = document.getElementById('timeline1');
    var chart = new google.visualization.Timeline(container);

    var dataTable = new google.visualization.DataTable();
    dataTable.addColumn({type: 'string', id: 'State'});
    dataTable.addColumn({type: 'string', id: 'Name'});
    dataTable.addColumn({type: 'date', id: 'Start'});
    dataTable.addColumn({type: 'date', id: 'End'});
    dataTable.addRows([
        ['Online', 'Online', new Date(0,0,0,12,0,0), new Date(0,0,0,13,30,0)],
        ['Online', 'Offline', new Date(0,0,0,13,30,0), new Date(0,0,0,14,0,0)],
        ['Online', 'Online', new Date(0,0,0,14,0,0), new Date(0,0,0,15,0,0)],
        ['Online', 'Offline', new Date(0,0,0,15,0,0), new Date(0,0,0,16,0,0)],
        ['Online', 'Online', new Date(0,0,0,16,0,0), new Date(0,0,0,17,30,0)],
        ['Online', 'Offline', new Date(0,0,0,17,30,0), new Date(0,0,0,18,0,0)],

        ['Endpoints', 'Online', new Date(0,0,0,12,0,0), new Date(0,0,0,14,30,0)],
        ['Endpoints', 'Offline', new Date(0,0,0,14,30,0), new Date(0,0,0,15,30,0)],
        ['Endpoints', 'Online', new Date(0,0,0,15,30,0), new Date(0,0,0,17,30,0)],
        ['Endpoints', 'Offline', new Date(0,0,0,17,30,0), new Date(0,0,0,18,0,0)],

        ['Features', 'Offline', new Date(0,0,0,12,0,0), new Date(0,0,0,13,0,0)],
        ['Features', 'Online', new Date(0,0,0,13,0,0), new Date(0,0,0,18,0,0)]
    ]);

    var colors = [];

    var colorMap = {
        Online: 'green',
        Offline: 'red'
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