/**
 * Created by LL on 2017/9/18.
 */
var t1 = setTimeout(function () {
    showCharts("mainChart", 1, "No.1");
    window.clearTimeout(t1);
}, 50);

/**
 *
 * @param divId div ID
 * @param type 查询类型
 * @param equipName 设备名称
 */
function showCharts(divId, type, equipName) {
    var myChart = echarts.init(document.getElementById(divId));
    myChart.showLoading();
    var legends = [
        "法兰X方向轴1/2X摆度/um",
        "法兰X方向轴1X摆度/um",
        "法兰X方向轴2X摆度/um",
        "法兰Y方向轴1/2X摆度/um",
        "法兰Y方向轴1X摆度/um",
        "法兰Y方向轴2X摆度/um"
    ];
    var data1 = []; // 法兰X方向轴1/2X摆度/um
    var data2 = []; // 法兰X方向轴1X摆度/um
    var data3 = []; // 法兰X方向轴2X摆度/um
    var data4 = []; // 法兰Y方向轴1/2X摆度/um
    var data5 = []; // 法兰Y方向轴1X摆度/um
    var data6 = []; // 法兰Y方向轴2X摆度/um


    var option = {
        title: {
            text: equipName
        },
        tooltip: {
            trigger: 'axis',
            formatter: function (params) {
                var x1 = params[0].data[1];
                var x2 = params[1].data[1];
                var x3 = params[2].data[1];
                var y1 = params[3].data[1];
                var y2 = params[4].data[1];
                var y3 = params[5].data[1];
                var faultType = "正常";
                var color = "#FFFFFF";
                if (x1 > 200 && y1 > 200 && x3 > 400 && y3 > 400) {
                    faultType = "转子不对中";
                    color = "#FF0000"
                } else if (x1 > 200 && y1 > 200) {
                    faultType = "油膜涡动";
                    color = "#00FF00"
                } else if (x2 > 400 && y2 > 400) {
                    faultType = "转子不平衡";
                    color = "#0000FF";
                }
                var date = new Date(params[0].data[0]);
                var str = '<div>时间：' + date.getFullYear() + '/' + (date.getMonth() + 1) + '/' + date.getDate() + ' '
                    + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds() + '</div>' +
                    "<div><span style='color: " + params[0].color + "'>" + params[0].seriesName + "</span>:" + x1 + "</div>" +
                    "<div><span style='color: " + params[1].color + "'>" + params[1].seriesName + "</span>:" + x2 + "</div>" +
                    "<div><span style='color: " + params[2].color + "'>" + params[2].seriesName + "</span>:" + x3 + "</div>" +
                    "<div><span style='color: " + params[3].color + "'>" + params[3].seriesName + "</span>:" + y1 + "</div>" +
                    "<div><span style='color: " + params[4].color + "'>" + params[4].seriesName + "</span>:" + y2 + "</div>" +
                    "<div><span style='color: " + params[5].color + "'>" + params[5].seriesName + "</span>:" + y3 + "</div>" +
                    "<div>状态：" + "<span style='color: " + color + "'>" + faultType + "</span>" + "</div>";
                return str;
            },
            axisPointer: {
                animation: false
            }
        },
        legend: {
            data: legends,
            x: "right",
            orient: 'vertical',
            itemGap: 20,
            selectedMode: false
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
            name: '摆度/um',
            nameGap: 50,
            nameTextStyle: {fontWeight: 'lighter', fontSize: 13},
            nameLocation: 'middle',
            type: 'value',
            boundaryGap: [0, '100%'],
            max: 500,
            min: 0,
            splitLine: {
                // show: true
            }
        },
        series: [{
            name: legends[0],
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: data1,
            itemStyle: {
                normal: {
                    lineStyle: {
                        color: "#416FA6",
                        width: 2
                    }
                }
            }
        }, {
            name: legends[1],
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: data2,
            itemStyle: {
                normal: {
                    lineStyle: {
                        color: "#A8423F",
                        width: 2
                    }
                }
            }
        }, {
            name: legends[2],
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: data3,
            itemStyle: {
                normal: {
                    lineStyle: {
                        color: "#86A44A",
                        width: 2
                    }
                }
            }
        }, {
            name: legends[3],
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: data4,
            itemStyle: {
                normal: {
                    lineStyle: {
                        color: "#6E548D",
                        width: 2
                    }
                }
            }
        }, {
            name: legends[4],
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: data5,
            itemStyle: {
                normal: {
                    lineStyle: {
                        color: "#3D96AE",
                        width: 2
                    }
                }
            }
        }, {
            name: legends[5],
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: data6,
            itemStyle: {
                normal: {
                    lineStyle: {
                        color: "#DA8137",
                        width: 2
                    }
                }
            }
        }]
    };

    var begin = 100; // 开始查询100条数据
    var url = "/data/waterTurbine?"
    $.get(url + "&number=" + begin).done(function (resultData) {
        data1 = resultData.x1Line;
        data2 = resultData.x2Line;
        data3 = resultData.x3Line;
        data4 = resultData.y1Line;
        data5 = resultData.y2Line;
        data6 = resultData.y3Line;
        option.series[0].data = data1;
        option.series[1].data = data2;
        option.series[2].data = data3;
        option.series[3].data = data4;
        option.series[4].data = data5;
        option.series[5].data = data6;
        myChart.setOption(option);
        myChart.hideLoading();
        var t2 = setInterval(function () {
            begin += 50;
            $.get(url + type + "&number=" + begin).done(function (nextData) {
                if (nextData === null || nextData.x1Line.length === 0) {
                    // 查询数据为空，清除定时器
                    window.clearInterval(t2);
                    return;
                }
                for (var i = 0; i <= 50; i++) {
                    data1.shift();
                    data2.shift();
                    data3.shift();
                    data4.shift();
                    data5.shift();
                    data6.shift();
                }
                for (var i = 0; i < nextData.x1Line.length; i++) {
                    data1.push(nextData.x1Line[i]);
                    data2.push(nextData.x2Line[i]);
                    data3.push(nextData.x3Line[i]);
                    data4.push(nextData.y1Line[i]);
                    data5.push(nextData.y2Line[i]);
                    data6.push(nextData.y3Line[i]);
                }
                myChart.setOption({
                    series: [{data: data1}, {data: data2}, {data: data3}, {data: data4}, {data: data5}, {data: data6}]
                });
            });
        }, 3000);
    });
}
