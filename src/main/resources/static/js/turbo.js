/**
 * Created by LL on 2017/9/18.
 */
var t1 = setTimeout(function () {
    showCharts("mainChart1", 1, "Turbo No.1");
    showCharts("mainChart2", 2, "Turbo No.2");
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
    var data1 = [];
    var color = "#0054A6";
    if (type === 1){
        color = "#FF2020";
    }
    var option = {
        title: {
            text: equipName
        },
        tooltip: {
            trigger: 'axis',
            formatter: function (params) {
                var tparam = params[0];
                var str = ""
                if (type === 1) {
                    str = "<div>" + tparam.seriesName + "</div>" +
                        "<div>时间（秒）：" + tparam.value[0] + "</div>" +
                        "<div>振幅A：" + tparam.value[1] + "</div>" +
                        "<div>状态：" + "<a style='color: red'>涡带，异常</a>" + "</div>"
                } else if (type === 2) {
                    str = "<div>" + tparam.seriesName + "</div>" +
                        "<div>时间（秒）：" + tparam.value[0] + "</div>" +
                        "<div>振幅A：" + tparam.value[1] + "</div>" +
                        "<div>状态：" + "<a style='color: darkgreen'>稳态，正常</a>" + "</div>"
                }
                return str;

            },
            axisPointer: {
                animation: false
            }
        },
        legend: {
            data: [],
            x: 'right',
            orient: 'vertical',
            bottom: 70,
            itemGap: 85
        },
        xAxis: {
            type: 'value',
            splitLine: {
                // show: true
            },
            name: '时间（秒）',
            nameLocation: 'middle',
            nameGap: 35,
            nameTextStyle: {fontWeight: 'lighter', fontSize: 13},
            min: function (value) {
                return value.min;
            },
            max: function (value) {
                return value.max;
            }
        },
        yAxis: {
            name: '振幅 A',
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
            data: data1,
            itemStyle: {
                normal: {
                    lineStyle: {
                        color: color,
                        width: 2
                    }
                }
            }
        }]
    };

    var begin = 1000; // 开始查询1000条数据
    var url = "/data/turboData?type="
    $.get(url + type + "&number=" + begin).done(function (resultData) {
        data1 = resultData;
        option.series[0].data = data1;
        myChart.setOption(option);
        myChart.hideLoading();
        var t2 = setInterval(function () {
            begin += 1000;
            $.get(url + type + "&number=" + begin).done(function (nextData) {
                if (nextData === null || nextData.length === 0) {
                    // 查询数据为空，清除定时器
                    window.clearInterval(t2);
                    return;
                }
                for (var i = 0; i < 250; i++) {
                    data1.shift();
                }
                for (var i = 0; i < nextData.length; i++) {
                    data1.push(nextData[i]);
                }
                myChart.setOption({
                    series: [{data: data1}]
                });
            });
        }, 3000);
    });
}
