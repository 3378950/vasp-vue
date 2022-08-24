<template>
  <div class="app-container">
    <el-row>
      <el-col :span="12" class="card-box">
        <table-layout></table-layout>
      </el-col>

      <el-col :span="6" class="card-box">

        <el-card>
          <div slot="header"><span>办件箱</span></div>
          <el-col :span="8">
            <demand></demand>
          </el-col>
          <el-col :span="8">
            <feedback></feedback>
          </el-col>
          <el-col :span="8">
            <refused></refused>
          </el-col>
        </el-card>
      </el-col>

      <el-col :span="6" class="card-box">
        <post></post>
      </el-col>

      <el-col :span="12" class="card-box">
        <el-card>
          <div slot="header"><span>区域情况汇总</span></div>
          <div class="col-sm-1 col-md-12 col-lg-12" id="myChart" ref="myChart" style="height: 500px;"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import bd from '../../../assets/geo/bd.json';
  import { getUserProfile } from "../../../api/system/user";
  import Feedback from "./feedback";
  import Demand from "./demand";
  import TableLayout from "./table/tableLayout";
  import Post from "./post";
  import Refused from "./refused";
  export default {
      components: { Refused, TableLayout, Demand, Feedback, Post },
      data() {
        return {
          // 当前用户信息
          currentUser: {
              username: "",
              role: "",
              province: "",
              city: "",
              district: "",
              level: null,
              region: "",
          },
          // 地图统计信息
          mapdata: [
            { name: '涞源县', value: 130 },
            { name: '涞水县', value: 70 },
            { name: '涿州市', value: 6 },
            { name: '易县', value: 100 },
            { name: '定兴县', value: 25 },
            { name: '高碑店市', value: 60 },
            { name: '莲池区', value: 80 },
            { name: '雄县', value: 65 },
            { name: '徐水区', value: 45 },
            { name: '安新县', value: 46 },
            { name: '蠡县', value: 48 },
            { name: '容城县', value: 51 },
            { name: '高阳县', value: 100 },
            { name: '清苑县', value: 70 },
            { name: '曲阳县', value: 60 },
            { name: '唐县', value: 5 },
            { name: '阜平县', value: 20 },
            { name: '定州市', value: 61 },
            { name: '望都县', value: 45 },
            { name: '竞秀区', value: 97 },
            { name: '顺平县', value: 50 },
            { name: '清苑区', value: 40 },
            { name: '博野县', value: 55 },
            { name: '满城区', value: 60 },
            { name: '安国市', value: 30 },
          ],
        }
      },
      created() {
        this.initData();
      },
      methods: {
        initData() {
          getUserProfile().then(response => {
            // 初始化当前用户信息
            this.currentUser.username = response.data.userName;
            this.currentUser.role = response.data.roles[0].roleKey;
            this.currentUser.province = response.data.province;
            this.currentUser.city = response.data.city;
            this.currentUser.district = response.data.district;
            this.currentUser.level = response.data.level;

            if (this.currentUser.level == 1) this.currentUser.region = this.currentUser.province;
            if (this.currentUser.level == 2) this.currentUser.region = this.currentUser.city;
            if (this.currentUser.level == 3) this.currentUser.region = this.currentUser.district;

            this.initCharts();
          });
        },
        // 地图初始化
        initCharts() {
          this.$echarts.registerMap('bd', bd);
          let myChart = this.$echarts.init(this.$refs.myChart)
          let option = {
            backgroundColor: 'transparent',
            title: {},
            tooltip: {
              trigger: 'item'
            },
            visualMap: {
              min: 0,
              max: 150,
              text: ['High', 'Low'],
              realtime: false,
              calculable: true,
              inRange: {
                color: ['lightskyblue', 'yellow', 'orangered']
              }
            },
            series: [
              {
                type: 'map',
                map: 'bd',
                geoIndx: 0,
                aspectScale: 0.75,
                showLegendSymbol: false,
                label: {
                  normal: {
                    show: true
                  },
                  emphasis: {
                    show: false,
                    textStyle: {
                      color: '#fff'
                    }
                  }
                },
                roam: true,
                animation: false,
                data: this.mapdata
              }
            ]

          };
          myChart.setOption(option);
          window.addEventListener('resize', function () {
            myChart.resize()
          });
        },
      }
    }
</script>

<style scoped>
</style>
