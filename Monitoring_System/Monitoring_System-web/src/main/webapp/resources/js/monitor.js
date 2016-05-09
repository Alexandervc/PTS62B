google.charts.load("current", {packages: ['corechart', 'controls', 'timeline']});
google.charts.setOnLoadCallback(drawCharts);

function drawChart(fieldname) {
    var json = document.getElementById(fieldname); 
    var obj = JSON.parse(json.value);    
    var systemName = obj[0][0];    
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
    drawChart("ASShidden");
    drawChart("VShidden");
    drawChart("RADhidden");
}