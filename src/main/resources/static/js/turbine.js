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
    var data5 = [];

    var option = {
        title: {
            text: equipName
        },
        tooltip: {
            trigger: 'axis',
            formatter: function (params) {
                var tparam = params[0].data;
                var ucl = params[1].data;
                var date = new Date(tparam[0]);
                var tipstr = '<div>时间：' + date.getFullYear() + '/' + (date.getMonth() + 1) + '/' + date.getDate() + ' '
                    + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds() + '</div>'
                    + '<div>' + params[0].seriesName + '：  ' + params[0].data[1] + '</div>'
                    + '<div>' + params[1].seriesName + '： ' + params[1].data[1] + '</div>'
                    + '<div>' + params[2].seriesName + '： ' + params[2].data[1] + '</div>'
                    + '<div>' + params[3].seriesName + '： ' + params[3].data[1] + '</div>'
                if (tparam[1] > ucl[1]) {
                    tipstr += '<div style="color: red">即将断裂</div>';
                } else {
                    tipstr += '<div>正常</div>';
                }
                return tipstr;
            },
            axisPointer: {
                animation: false
            }
        },
        legend: {
            data: ['UCL', 'X', 'LCL'],
            x: 'right',
            orient: 'vertical',
            bottom: 70,
            itemGap: 45
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
            max: 0.8,
            splitLine: {
                // show: true
            }
        },
        series: [{
            name: equipName,
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: data1,
            itemStyle: {
                normal: {
                    lineStyle: {
                        color: '#0054A6',
                        width: 2
                    }
                }
            }
        }, {
            name: 'UCL',
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: data2,
            itemStyle: {
                normal: {
                    lineStyle: {
                        color: '#931313',
                        width: 1
                    }
                }
            }
        }, {
            name: 'X',
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: data3,
            itemStyle: {
                normal: {
                    lineStyle: {
                        color: '#00841F',
                        width: 1
                    }
                }
            }
        }, {
            name: 'LCL',
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: data4,
            itemStyle: {
                normal: {
                    lineStyle: {
                        color: '#931313',
                        width: 1
                    }
                }
            }
        }, {
            name: 'error',
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: data5,
            itemStyle: {
                normal: {
                    lineStyle: {
                        color: '#CE0000',
                        width: 2
                    }
                }
            }
        }]
    };

    var begin = 145;
    var url = "/data/turbineData?id="
    $.get(url + equipId + "&number=" + begin).done(function (mapData) {
        data1 = mapData.values;
        data2 = mapData.ucl;
        data3 = mapData.x;
        data4 = mapData.lcl;
        for (var i = 0; i < mapData.values.length; i++) {
            var point = mapData.values[i];
            var uclPoint = mapData.ucl[i];
            if (point[1] > uclPoint[1]) {
                data5.push(mapData.values[i]);
            }
        }
        option.series[0].data = data1;
        option.series[1].data = data2;
        option.series[2].data = data3;
        option.series[3].data = data4;
        myChart.setOption(option);
        myChart.hideLoading();
        setInterval(function () {
            begin += 144;
            $.get(url + equipId + "&number=" + begin).done(function (rdata) {
                data1.shift();
                data2.shift();
                data3.shift();
                data4.shift();
                for (var i = 0; i < rdata.values.length; i++) {
                    var point = rdata.values[i];
                    var uclPoint = rdata.ucl[i];
                    if (point[1] > uclPoint[1]) {
                        data5.push(rdata.values[i]);
                    }
                    data1.push(rdata.values[i]);
                    data2.push(rdata.ucl[i]);
                    data3.push(rdata.x[i]);
                    data4.push(rdata.lcl[i]);
                }
                myChart.setOption({
                    series: [{data: data1}, {data: data2}, {data: data3}, {data: data4}, {data: data5}]
                });
            });
        }, 3000);
    });
}