google.charts.load("current", {packages: ['corechart', 'controls', 'timeline']});
google.charts.setOnLoadCallback(drawCharts);

function drawChart(fieldname) {
    var json = document.getElementById(fieldname);

    var obj = JSON.parse(json.value);
    var container = document.getElementById(obj.systemName);
    var chart = new google.visualization.Timeline(container);
    
    var dataTable = new google.visualization.DataTable();
    dataTable.addColumn({type: 'string', id: 'State'});
    dataTable.addColumn({type: 'string', id: 'Name'});
    dataTable.addColumn({type: 'string', id: 'ID'});
    dataTable.addColumn({type: 'date', id: 'Start'});
    dataTable.addColumn({type: 'date', id: 'End'});
    
    dataTable.addRows([
        ['Online', obj.status, obj.status, new Date(0, 0, 0, 0, 0, 0), new Date(0, 0, 0, 24, 0, 0)],
        ['Endpoints', obj.endpoints, obj.endpoints, new Date(0, 0, 0, 0, 0, 0), new Date(0, 0, 0, 24, 0, 0)],
        ['Features', obj.functional, obj.functional, new Date(0, 0, 0, 0, 0, 0), new Date(0, 0, 0, 24, 0, 0)]
    ]);
   
    /*
    var dataTable = new google.visualization.DataTable();
    dataTable.addColumn({type: 'string', id: 'State'});
    dataTable.addColumn({type: 'string', id: 'Name'});
    dataTable.addColumn({type: 'date', id: 'Start'});
    dataTable.addColumn({type: 'date', id: 'End'});
    
    dataTable.addRows([
        ['Online', obj.status, new Date(0, 0, 0, 0, 0, 0), new Date(0, 0, 0, 24, 0, 0)],
        ['Endpoints', obj.endpoints, new Date(0, 0, 0, 0, 0, 0), new Date(0, 0, 0, 24, 0, 0)],
        ['Features', obj.functional, new Date(0, 0, 0, 0, 0, 0), new Date(0, 0, 0, 24, 0, 0)],
    ]);
    */

    var colors = [];

    var colorMap = {
        passed: 'green',
        failed: 'red'
    }

    for (var i = 0; i < dataTable.getNumberOfRows(); i++) {
        colors.push(colorMap[dataTable.getValue(i, 1)]);
    }

    var options = {
        colors: colors,
        groupByRowLabel: true
    };
    
    // use a DataView to hide the category column from the Timeline
    var view = new google.visualization.DataView(dataTable);
    view.setColumns([0, 2, 3, 4]);
    
    chart.draw(view, options);
}

$(window).resize(function(){
    drawCharts();
});
function drawCharts() {
    drawChart("ASShidden");
    drawChart("VShidden");
    drawChart("RADhidden");
}
