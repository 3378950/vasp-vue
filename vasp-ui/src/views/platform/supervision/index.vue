<template>
  <div class="app-container">
    <el-row>
      <el-col :span="12" class="card-box">
        <el-card>
          <div slot="header"><span>整改情况</span></div>

          <el-tabs v-model="activeName" @tab-click="handleClick">

            <el-tab-pane label="整改中" name="first">
              <el-table
                :data="tableData"
                style="width: 100%">
                <el-table-column
                  label="序号"
                  width="60">
                  <template slot-scope="scope">
                    <span style="margin-left: 10px">{{ scope.$index }}</span>
                  </template>
                </el-table-column>

                <el-table-column
                  label="检测时间"
                  width="120">
                  <template slot-scope="scope">
                    <i class="el-icon-time"></i>
                    <span style="margin-left: 10px">{{ scope.row.date }}</span>
                  </template>
                </el-table-column>
                <el-table-column
                  label="整改目标"
                  width="100">
                  <template slot-scope="scope">
                    <div slot="reference" class="name-wrapper">
                      <el-tag :type="scope.row.type">{{ scope.row.target }}</el-tag>
                    </div>
                  </template>
                </el-table-column>
                <el-table-column
                  label="所在位置"
                  width="80">
                  <template slot-scope="scope">
                    <div slot="reference" class="name-wrapper">
                      {{ scope.row.address }}
                    </div>
                  </template>
                </el-table-column>
                <el-table-column
                  label="整改进程"
                  width="100">
                  <template slot-scope="scope">
                    <div slot="reference" class="name-wrapper">
                      <el-tag size="medium" effect="plain" :type="scope.row.attType">{{ scope.row.process }}</el-tag>
                    </div>
                  </template>
                </el-table-column>
                <el-table-column
                  label="详情"
                  width="120">
                  <template slot-scope="scope">
                    <div slot="reference" class="name-wrapper">
                      {{ scope.row.process }}
                    </div>
                  </template>
                </el-table-column>
                <el-table-column label="操作">
                  <template slot-scope="scope">
                    <el-button type="text" @click="dialogVisible = true"><i class="el-icon-view el-icon--right"></i>查看</el-button>
                  </template>
                </el-table-column>
              </el-table>
              <el-pagination
                background
                layout="prev, pager, next"
                :total="1000">
              </el-pagination>
            </el-tab-pane>

            <el-tab-pane label="已整改" name="second">
              <el-table
                :data="tableData1"
                style="width: 100%">
                <el-table-column
                  label="序号"
                  width="60">
                  <template slot-scope="scope">
                    <span style="margin-left: 10px">{{ scope.$index }}</span>
                  </template>
                </el-table-column>

                <el-table-column
                  label="检测时间"
                  width="120">
                  <template slot-scope="scope">
                    <i class="el-icon-time"></i>
                    <span style="margin-left: 10px">{{ scope.row.date }}</span>
                  </template>
                </el-table-column>
                <el-table-column
                  label="整改目标"
                  width="100">
                  <template slot-scope="scope">
                    <div slot="reference" class="name-wrapper">
                      <el-tag :type="scope.row.type">{{ scope.row.target }}</el-tag>
                    </div>
                  </template>
                </el-table-column>
                <el-table-column
                  label="所在位置"
                  width="80">
                  <template slot-scope="scope">
                    <div slot="reference" class="name-wrapper">
                      {{ scope.row.address }}
                    </div>
                  </template>
                </el-table-column>
                <el-table-column
                  label="整改进程"
                  width="100">
                  <template slot-scope="scope">
                    <div slot="reference" class="name-wrapper">
                      <el-tag size="medium" effect="plain" :type="scope.row.attType">{{ scope.row.process }}</el-tag>
                    </div>
                  </template>
                </el-table-column>
                <el-table-column
                  label="详情"
                  width="120">
                  <template slot-scope="scope">
                    <div slot="reference" class="name-wrapper">
                      {{ scope.row.process }}
                    </div>
                  </template>
                </el-table-column>
                <el-table-column label="操作">
                  <template slot-scope="scope">
                    <el-button type="text" @click="dialogVisible = true"><i class="el-icon-view el-icon--right"></i>查看</el-button>
                  </template>
                </el-table-column>
              </el-table>
              <el-pagination
                background
                layout="prev, pager, next"
                :total="1000">
              </el-pagination>
            </el-tab-pane>

          </el-tabs>


        </el-card>
      </el-col>

      <el-col :span="6" class="card-box">


        <el-card :body-style="{height: '120px'}">
          <div slot="header"><span>办件箱</span></div>
          <el-col :span="8">
            <el-card class="box-card">
                <div>待办</div>
                <span class="number">3</span> <span class="unit">件</span>
                <el-button type="text">立即处理></el-button>
            </el-card>
          </el-col>



          <el-col :span="8">

            <el-card class="box-card">
              <div style="color: #1890ff">
                <div>已办</div>
                <span class="number">12</span> <span class="unit">件</span>
                <el-button type="text">立即处理></el-button>
              </div>


            </el-card>

          </el-col>

          <el-col :span="8">
            <el-card class="box-card">
              <div style="color: orangered;">
                <div>逾期</div>
                <span class="number">5</span> <span class="unit">件</span>
                <el-button type="text">立即处理></el-button>
              </div>
            </el-card>
          </el-col>
        </el-card>

      </el-col>
      <el-col :span="6" class="card-box">
        <el-card :body-style="{height: '120px'}">
          <div slot="header"><span>公示公告</span></div>
          <el-row>
            <el-col :span="2"><i class="el-icon-bell"></i></el-col>
            <el-col :span="16">关于做好全镇农村人居环境村容...</el-col>
            <el-col :span="6">2016-05-02</el-col>
          </el-row>

          <el-row>
            <el-col :span="2"><i class="el-icon-bell"></i></el-col>
            <el-col :span="16">关于做好全镇农村人居环境村容...</el-col>
            <el-col :span="6">2016-05-02</el-col>
          </el-row>
          <el-row>
            <el-col :span="2"><i class="el-icon-bell"></i></el-col>
            <el-col :span="16">关于做好全镇农村人居环境村容...</el-col>
            <el-col :span="6">2016-05-02</el-col>
          </el-row>
          <el-row>
            <el-col :span="2"><i class="el-icon-bell"></i></el-col>
            <el-col :span="16">关于做好全镇农村人居环境村容...</el-col>
            <el-col :span="6">2016-05-02</el-col>
          </el-row>
        </el-card>
      </el-col>

      <el-col :span="12" class="card-box">
        <el-card>
          <div slot="header"><span>区域情况汇总</span></div>
          <div class="col-sm-1 col-md-12 col-lg-12" id="myChart" ref="myChart" style="height: 500px;"></div>

        </el-card>
      </el-col>


    </el-row>



    <el-dialog
      title="责令整改"
      :visible.sync="dialogVisible"
      width="60%"
      :before-close="handleClose">
      <detail></detail>
    </el-dialog>



  </div>

</template>

<script>
  import bd from '../../../assets/geo/bd.json';
  import Detail from "./detail";
    export default {
      components: {Detail},
      data() {
        return {
          tableData: [
            {
            date: '2016-05-02',
            target: '乱搭乱建',
            type: 'danger',
            address: '莲池区',
            process: '未责令整改',
            attType: 'warning'
            },
            {
              date: '2016-06-02',
              target: '乱堆乱放',
              type: 'warning',
              address: '五尧乡',
              process: '整改中',
              attType: ''
            },
            {
              date: '2016-05-02',
              target: '乱搭乱建',
              itemType: 'danger',
              address: '莲池区',
              process: '反馈未通过',
              attType: 'danger'
            },
            {
              date: '2016-06-02',
              target: '乱堆乱放',
              type: 'warning',
              address: '五尧乡',
              process: '未责令整改',
              attType: 'warning',

            },
            {
              date: '2016-08-02',
              target: '乱贴乱画',
              type: 'info',
              address: '易县',
              process: '整改中',
              attType: '',
            },
            {
              date: '2016-05-02',
              target: '乱搭乱建',
              type: 'danger',
              address: '莲池区',
              process: '整改中',
              attType: '',
            },
            {
              date: '2016-06-02',
              target: '乱堆乱放',
              type: 'warning',
              address: '五尧乡',
              process: '整改中',
              attType: '',

            },
            {
              date: '2016-08-02',
              target: '乱贴乱画',
              type: 'info',
              address: '易县',
              process: '未责令整改',
              attType: 'warning',

            },
            {
              date: '2016-08-02',
              target: '乱贴乱画',
              type: 'info',
              address: '易县',
              process: '未责令整改',
              attType: 'warning',
              info: ''

            },
          ],
          tableData1: [
            {
              date: '2016-05-02',
              target: '乱搭乱建',
              type: 'danger',
              address: '莲池区',
              process: '整改完成',
              attType: 'success'
            },
            {
              date: '2016-06-02',
              target: '乱堆乱放',
              type: 'warning',
              address: '五尧乡',
              process: '整改完成',
              attType: 'success'
            },
            {
              date: '2016-05-02',
              target: '乱搭乱建',
              itemType: 'danger',
              address: '莲池区',
              process: '整改完成',
              attType: 'success'
            },
            {
              date: '2016-06-02',
              target: '乱堆乱放',
              type: 'warning',
              address: '五尧乡',
              process: '整改完成',
              attType: 'success',
            },
            {
              date: '2016-08-02',
              target: '乱贴乱画',
              type: 'info',
              address: '易县',
              process: '整改完成',
              attType: 'success',
            },
            {
              date: '2016-05-02',
              target: '乱搭乱建',
              type: 'danger',
              address: '莲池区',
              process: '整改完成',
              attType: 'success',
            }
          ],
          activeName: 'first',
          dialogVisible: false,
        }
      },
      created() {
        this.$nextTick(() => {
          this.initCharts();
        });
      },
      methods: {
        initCharts() {
          this.$echarts.registerMap('bd', bd);
          let myChart = this.$echarts.init(this.$refs.myChart)

          let option = {
            backgroundColor: 'transparent',
            title: {
              text: '示范基地数字地图',
              left: 'center',
              textStyle: {
                color: '#000'
              }
            },
            tooltip: {
              trigger: 'item'
            },
            visualMap: {
              min: 0,
              max: 20,
              text: ['High', 'Low'],
              realtime: false,
              calculable: true,
              inRange: {
                color: ['lightskyblue', 'yellow', 'orangered']
              }
            },
            series: [
              {
                name: '示范基地数',
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

          }

          myChart.setOption(option)
          window.addEventListener('resize', function () {
            myChart.resize()
          })
        },
        handleClose(done) {
          this.$confirm('确认关闭？')
            .then(_ => {
              done();
            })
            .catch(_ => {});
        },
        handleClick(tab, event) {
          console.log(tab, event);
        }
      }
    }
</script>

<style scoped>

  .box-card {
    width: 100px;
    margin-bottom: 10px;
    border-radius: 10px;
    border: 1px solid #dddfe3;
    text-align: center;
    line-height: 2;
    font-size: 13px;
  }
  .number {
    font-size: 25px;
  }
  .unit {
    font-size: 13px;
  }
  .el-pagination{
    text-align:center;
    margin-top:20px;
  }

</style>
