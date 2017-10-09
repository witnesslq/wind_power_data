/**
 * Created by LL on 2017/9/18.
 */
var t1 = setTimeout(function () {
    showCharts("mainChart1", 1, "Turbine No.1");
    showCharts("mainChart2", 2, "Turbine No.2");
    window.clearTimeout(t1);
}, 50);

function showCharts(divId, equipId, equipName) {
    var myChart = echarts.init(document.getElementById(divId));
    myChart.showLoading();

    var data1 = [];
    var data2 = [];
    var data3 = [];
    var data4 = [];

    var option = {
        title: {
            text: equipName
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                animation: false
            }
        },
        legend: {
            data: ['Turbine No.1', 'UCL', 'X', 'LCL']
        },
        xAxis: {
            type: 'time',
            splitLine: {
                // show: true
            },
            name: '时间',
            nameLocation: 'middle',
            nameGap: 35,
            nameTextStyle: {fontWeight: 'lighter', fontSize: 13}
        },
        yAxis: {
            name: 'EWMA',
            nameGap: 50,
            nameTextStyle: {fontWeight: 'lighter', fontSize: 13},
            nameLocation: 'middle',
            type: 'value',
            boundaryGap: [0, '100%'],
            splitLine: {
                // show: true
            }
        },
        series: [{
            name: equipName,
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: data1
        }, {
            name: 'UCL',
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: data2
        }, {
            name: '',
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: data3
        }, {
            name: 'LCL',
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: data4
        }]
    };

    var begin = 145;
    var url = "/data/turbineData?id="
    $.get(url + equipId + "&number=" + begin).done(function (mapData) {
        data1 = mapData.values;
        data2 = mapData.ucl;
        data3 = mapData.x;
        data4 = mapData.lcl;
        option.series[0].data = mapData.values;
        option.series[1].data = mapData.ucl;
        option.series[2].data = mapData.x;
        option.series[3].data = mapData.lcl;
        myChart.setOption(option);
        myChart.hideLoading();
        setInterval(function () {
            begin += 12;
            $.get(url + 1 + "&number=" + begin).done(function (rdata) {
                data1.shift();
                data2.shift();
                data3.shift();
                data4.shift();
                for (var i = 0; i < rdata.values.length; i++) {
                    data1.push(rdata.values[i]);
                    data2.push(rdata.ucl[i]);
                    data3.push(rdata.x[i]);
                    data4.push(rdata.lcl[i]);
                }
                myChart.setOption({
                    series: [{data: data1}, {data: data2}, {data: data3}, {data: data4}]
                });
            });
        }, 3000);
    });
}
