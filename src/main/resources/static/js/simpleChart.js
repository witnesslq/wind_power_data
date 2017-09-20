/**
 * Created by LL on 2017/9/19.
 */
function timeFromClick() {
    WdatePicker({
        skin: 'whyGreen',
        dateFmt: 'yyyy-MM-dd HH:mm:ss',
        firstDayOfWeek: 1,
        lang: 'zh-cn',
        maxDate: '#F{$dp.$D(\'toTime\')||\'%y-%M-{%d} %H:%m:%s\'}'
    });
}
function toTimeClick() {
    // 获取开始时间
    WdatePicker({
        skin: 'whyGreen',
        dateFmt: 'yyyy-MM-dd HH:mm:ss',
        firstDayOfWeek: 1,
        lang: 'zh-cn',
        minDate: '#F{$dp.$D(\'timeFrom\')}',
        maxDate: '%y-%M-{%d} %H:%m:%s'
    });
}
function getDataFormTime() {
    // 获取开始时间
    var startTime = $('#timeFrom').val();
    // 获取结束时间
    var toTime = $('#toTime').val()
    if (startTime === "") {
        // 没有选择开始时间
        $.messager.alert('Warning', '没有选择开始时间', 'warning');
    } else if (toTime === "") {
        // 没有选择结束时间
        $.messager.alert('Warning', '没有选择结束时间', 'warning');
    } else {
        getData(startTime, toTime);
    }
}

function getData(timeFrom, toTime) {
    var myChart = echarts.init(document.getElementById('mainChart'));
    myChart.showLoading();
    var url = '/data/windDataJson';
    if (timeFrom !== undefined && toTime !== undefined) {
        url = '/data/windDataJson?timeFrom=' + timeFrom + '&toTime=' + toTime;
    }
    $.get(url).done(function (mapData) {
        var legends = ['正常数据', '训练异常数据', '训练拟合曲线', '训练拟合曲线上界', '训练拟合曲线下界'];
        var data = mapData.resultData;
        var myRegressionPoints = [];
        var myRegressionDownPoints = [];
        var myRegressionUpPoints = [];
        for (var i = 0; i < data.length; i++) {
            myRegressionPoints.push([data[i].windSpeed, data[i].validPower]);
            myRegressionDownPoints.push([data[i].windSpeed, data[i].lineDown]);
            myRegressionUpPoints.push([data[i].windSpeed, data[i].lineUp]);
        }
        myRegressionPoints.sort(function (a, b) {
            return a[0] - b[0];
        });

        myRegressionDownPoints.sort(function (a, b) {
            return a[0] - b[0];
        });

        myRegressionUpPoints.sort(function (a, b) {
            return a[0] - b[0];
        });

        var normalData = mapData.normalData;
        var exceptionData = mapData.exceptionData;

        var scatterNormal = [];
        var scatterException = [];

        for (var i = 0; i < normalData.length; i++) {
            scatterNormal.push([normalData[i].windSpeed, normalData[i].powerValid]);
        }
        for (var i = 0; i < exceptionData.length; i++) {
            if (exceptionData[i].faultCause !== null) {
                scatterException.push({
                    value: [exceptionData[i].windSpeed, exceptionData[i].powerValid],
                    tooltip: {
                        formatter: '{a}:<br />{c}<br/>异常原因：' + exceptionData[i].faultCause,
                        borderColor: '#ED1C24'
                    }
                });
            } else {
                scatterException.push([exceptionData[i].windSpeed, exceptionData[i].powerValid]);
            }
        }
        var series = [{
            name: legends[0],
            type: 'scatter',
            symbolSize: 5,
            data: scatterNormal
        }, {
            name: legends[1],
            type: 'scatter',
            symbolSize: 5,
            data: scatterException
        }, {
            name: legends[2],
            type: 'line',
            data: myRegressionPoints
        }, {
            name: legends[3],
            type: 'line',
            data: myRegressionUpPoints
        }, {
            name: legends[4],
            type: 'line',
            data: myRegressionDownPoints
        }];

        myChart.hideLoading();
        myChart.setOption({
            title: {
                text: '风电数据分析',
                left: 'center'
            },
            tooltip: {
                show: true,
                trigger: 'item'
            },
            legend: {
                data: legends,
                left: 'center',
                top: 'bottom'
            },
            xAxis: {
                name: '一秒平均风速[m/s]',
                nameLocation: 'middle',
                nameGap: 15,
                nameTextStyle: {fontWeight: 'lighter', fontSize: 13},
                type: 'value',
                splitLine: {
                    lineStyle: {
                        type: 'dashed'
                    }
                }
            },
            yAxis: {
                name: '有功功率[kW]',
                nameGap: 50,
                min: -5,
                nameTextStyle: {fontWeight: 'lighter', fontSize: 13},
                nameLocation: 'middle',
                type: 'value',
                splitLine: {
                    lineStyle: {
                        type: 'dashed'
                    }
                }
            },
            series: series
        });
    });
}
var t1 = setTimeout(function () {
    getData();
    window.clearTimeout(t1);
}, 10);

