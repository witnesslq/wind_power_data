/**
 * Created by LL on 2017/9/20.
 */
var t1 = setTimeout(function () {
    var myChart = echarts.init(document.getElementById('mainChart'));
// 显示动画加载效果
    myChart.showLoading();
    myChart.setOption({
        title: {
            show: true, //是否显示标题组件
            text: 'ECharts Demo', //主标题文本
            textStyle: {
                color: '#333',
                fontStyle: 'italic',
                fontWeight: 'bold',
                fontFamily: 'Courier New',
                align: 'center',
                fontSize: 20
            },
            left: 'center'
        },
        legend: {
            data: [{
                name: '散点图',
                icon: 'circle'
            }]
        },
        grid: {
            show: true
        },
        xAxis: {
            show: true,
            type: 'value',
            name: 'X轴坐标',
            nameLocation: 'end',
            nameGap: 35,
            nameTextStyle: {fontWeight: 'lighter', fontSize: 13},
            scale: false,
            axisLine: {
                lineStyle: {
                    type: 'dotted'
                }
            }
        },
        yAxis: {
            show: true,
            type: 'value',
            name: 'Y轴坐标',
            nameLocation: 'middle',
            nameGap: 40,
            nameTextStyle: {fontWeight: 'lighter', fontSize: 13},
            scale: false,
            axisLine: {
                lineStyle: {
                    type: 'dotted'
                }
            }
        },
        tooltip: {
            show: true,
            trigger: 'item'
        },
        series: [{
            type: 'scatter',
            name: '散点图Demo',
            coordinateSystem: 'cartesian2d',
            symbol: 'circle',
            symbolSize: 15,
            data: [[3.5, 4.5], [15, 10], [7.8, 9.3], {
                value: [15.8, 14.9],
                tooltip: {
                    position: ['50%', '50%'],
                    formatter: '{a}: <br />{c}: 异常数据',
                    textStyle: {
                        color: '#FFF',
                        fontSize: 20
                    },
                    extraCssText: 'box-shadow: 0 0 3px rgba(0, 0, 0, 0.3);'
                }
            }]
        }]
    });
    myChart.hideLoading();
    window.clearTimeout(t1);
}, 10);
