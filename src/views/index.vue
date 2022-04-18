<template>
    <div style="margin-top:15px">
     <el-row>
        <el-col :span="24">
                <div class="panel">
        <div class="panel-body" style="margin-top:-5px;margin-bottom:-5px">
            <div style="height: 30px;margin-top:5px;margin-bottom:-7px">
            
	        <div  style="float:left;color:#8492A6" class="infobox-icon">
				<i class="ace-icon fa fa-desktop"></i>
			</div>
			<div style="float:left;margin-left:10px;" class="infobox-data">
				<span class="infobox-data-number"><strong style="color: #8492A6">16</strong>&nbsp个服务器</span>
			</div>

			<div  style="float:left;margin-left:70px;color:#FF4949" class="infobox-icon">
				<i class="ace-icon fa fa-users"></i>
			</div>
			<div style="float:left;margin-left:10px" class="infobox-data">
				<span class="infobox-data-number"><strong style="color: #FF4949">16</strong>&nbsp位对等成员</span>
			</div>
            
			<div  style="float:left;margin-left:70px;color:#324057" class="infobox-icon">
				<span class="el-icon-date"></span>
			</div>
			<div style="float:left;margin-left:10px" class="infobox-data">
				<span class="infobox-data-number"><strong style="color: #324057">3127</strong>&nbsp个订单</span>
			</div>
	
			<div  style="float:left;margin-left:70px;color:#20A0FF" class="infobox-icon">
				<i class="el-icon-upload2 " ></i>
			</div>
			<div style="float:left;margin-left:10px" class="infobox-data">
				<span class="infobox-data-number"><strong style="color: #20A0FF">156351</strong>&nbsp次分发</span>
			</div>  


			<div  style="float:left;margin-left:70px;color: #13CE66" class="infobox-icon">
				<i class="el-icon-check " ></i>
			</div>
			<div style="float:left;margin-left:10px" class="infobox-data">
				<span class="infobox-data-number"><strong style="color: #13CE66">93%</strong>分发成功率</span>
			</div>  
            
            </div>
          </div>
        </div>
        </el-col>
        </el-row>
    <el-row>
        <el-col :span="8">
     <div class="panel">
        <panel-title :title="msg1"></panel-title>
        <div class="panel-body">
            <div ref="chartsA" style="height: 500px"></div>
        </div>
    </div>
        </el-col>

        <el-col :span="8">
            <div class="panel" style="margin-left: 10px">
            <panel-title :title="msg2"></panel-title>
            <div class="panel-body">
                <div ref="chartsB" style="height: 500px"></div>
            </div>
            </div>
        </el-col>
        <el-col :span="8">
            <div class="panel" style="margin-left: 10px">
            <panel-title :title="msg3"></panel-title>
            <div class="panel-body">
                <div ref="chartsC" style="height: 500px"></div>
            </div>
            </div>
        </el-col>
    </el-row>
   </div>
</template>
<script type="text/javascript">
    import panelTitle from '../components/panelTitle'
    export default{
        data(){
            return {
                echarts_instance: null,
                msg1: '本日共分发',
                msg2: '本周共分发',
                msg3: '本月共分发',
                mapHeight:2000,
                activeName:"chartsD",
                totalmem: null,
                totalpub:null,
                totalproject:null,
                totalpubsuccess:null,
                hostsum:null
            }
        },
        created(){
            this.get_echarts_instance()
        },
        destroyed(){
            this.echarts_instance = null
        },
        methods: {
            // handleClick(tab, event) {
            //     if(tab.name=="chartsD"){
            //         this.create_charts(tab.name,"dayBypro")
            //     }
            //     if(tab.name=="chartsE"){
            //         this.create_charts(tab.name,"weekBypro")
            //     }
            //     if(tab.name=="chartsF"){
            //         this.create_charts(tab.name,"monthBypro")
            //     }
            // },
            get_echarts_instance(){
                //按需引入 ECharts 图表和组件，这里先全部引入
                require(['echarts'], (echarts) => {
                    this.echarts_instance = echarts
                this.create_chartsA()
                this.create_chartsB()
                this.create_chartsC()
                // this.create_totalmen()
                // this.create_charts("chartsD","dayBypro")
            })
        },   
            create_chartsA() {
                let _dom = this.$refs.chartsA
                let myChart = this.echarts_instance.init(_dom)
                    // var chartData=[];
                    var chartTitleData=[];
                    this.msg1="本日共分发"+3657+"条"
                    var option = {
                    title : {
                        text: '本日分发',
                        x:'center'
                    },
                    tooltip : {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: chartTitleData
                    },
                    series : [
                        {
                            name: '发布类别',
                            type: 'pie',
                            radius : '60%',
                            center: ['50%', '60%'],
                            data:[
                                { value: 1048, name: '服务器121' },
                                { value: 735, name: '服务器109' },
                                { value: 580, name: '服务器113' },
                                { value: 484, name: '服务器29' },
                                { value: 300, name: '服务器110' },                                
                                { value: 263, name: '服务器32' },
                                { value: 112, name: '服务器15' },
                                { value: 80, name: '服务器219' },
                                { value: 20, name: '服务器29' },
                                { value: 35, name: '服务器36' }
                            ],
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };

                myChart.setOption(option)
            },
            create_chartsB() {
                let _dom = this.$refs.chartsB
                let myChart = this.echarts_instance.init(_dom)
                var chartData=[];
                var chartTitleData=[];
                var total=0;
                for(var i in this.data){
                    total=total+(data[i].task_count*1)
                    chartData.push({value:data[i].task_count, name:data[i].name})
                    chartTitleData.push(data[i].name)
                }
                this.msg2="本周共分发"+25599+"条"
                var option = {
                    title : {
                        text: '本周分发',
                        x:'center'
                    },
                    tooltip : {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: chartTitleData
                    },
                    series : [
                        {
                            name: '发布类别',
                            type: 'pie',
                            radius : '60%',
                            center: ['50%', '60%'],
                            data:[
                                { value: 1048, name: '服务器121' },
                                { value: 1735, name: '服务器109' },
                                { value: 2735, name: '服务器142' },
                                { value: 435, name: '服务器132' },
                                { value: 235, name: '服务器108' },
                                { value: 435, name: '服务器109' },
                                { value: 580, name: '服务器113' },
                                { value: 484, name: '服务器29' },
                                { value: 500, name: '服务器110' },                                
                                { value: 663, name: '服务器32' },
                                { value: 212, name: '服务器15' },
                                { value: 180, name: '服务器219' },
                                { value: 90, name: '服务器29' },
                                { value: 85, name: '服务器36' }
                            ],
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };

                myChart.setOption(option)
            },
            create_chartsC() {
                let _dom = this.$refs.chartsC
                let myChart = this.echarts_instance.init(_dom)
                var chartData=[];
                var chartTitleData=[];
                var total=0;
                var data = this.data
                for(var i in data){
                    total=total+(data[i].task_count*1)
                    chartData.push({value:data[i].task_count, name:data[i].name})
                    chartTitleData.push(data[i].name)
                }
                this.msg3="本月共分发"+25599+"条"
                var option = {
                    title : {
                        text: '本月分发',
                        x:'center'
                    },
                    tooltip : {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: chartTitleData
                    },
                    series : [
                        {
                            name: '发布类别',
                            type: 'pie',
                            radius : '60%',
                            center: ['50%', '60%'],
                            data:[
                                { value: 1048, name: '服务器121' },
                                { value: 1735, name: '服务器109' },
                                { value: 2735, name: '服务器142' },
                                { value: 435, name: '服务器132' },
                                { value: 235, name: '服务器108' },
                                { value: 435, name: '服务器109' },
                                { value: 580, name: '服务器113' },
                                { value: 484, name: '服务器29' },
                                { value: 500, name: '服务器110' },                                
                                { value: 663, name: '服务器32' },
                                { value: 212, name: '服务器15' },
                                { value: 180, name: '服务器219' },
                                { value: 90, name: '服务器29' },
                                { value: 85, name: '服务器36' }
                            ],
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };

                myChart.setOption(option)
            },
            // create_charts(ref,query){
            //         var chartData=[];
            //     var chartTitleData=[];
            //     var dataSort=[];
            //     var data = this.data
            //     for(var i=0;i<data.length;i++){
            //         dataSort.push({name:data[i].name,task_count:data[i].task_count/1})
            //     }
            //     dataSort.sort(function (a,b) {
            //         return a.task_count-b.task_count
            //     });
            //     for(var i=0;i<dataSort.length;i++){
            //         chartData.push(dataSort[i].task_count)
            //         chartTitleData.push(dataSort[i].name)
            //     }
            //     var option={
            //         tooltip: {
            //             trigger: 'axis',
            //             axisPointer: {            // 坐标轴指示器，坐标轴触发有效
            //                 type: 'line'        // 默认为直线，可选为：'line' | 'shadow'
            //             }
            //         },
            //         legend: {
            //             data: ['发布次数']
            //         },
            //         grid: {
            //             left: '16px',
            //             right: '16px',
            //             bottom: '16px',
            //             top: '40px',
            //             containLabel: true
            //         },
            //         yAxis: [
            //             {
            //                 type: 'category',
            //                 data: chartTitleData,
            //                 axisLabel:{
            //                     interval:0
            //                 }
            //             }
            //         ],
            //         xAxis: [
            //             {
            //                 type: 'value'
            //             }
            //         ],
            //         series: [
            //             {
            //                 name: '发布次数',
            //                 type: 'bar',
            //                 data: chartData
            //             },

            //         ]
            //     };
            //     let _dom = this.$refs[ref]
            //     _dom.style.height=(data.length*20+200)+"px"
            //     let myChart = this.echarts_instance.init(_dom)
            //     myChart.setOption(option)
            // }
        },
        components: {
            panelTitle
        }
    }
</script>
<style lang="scss" type="text/css" rel="stylesheet/scss">

</style>
