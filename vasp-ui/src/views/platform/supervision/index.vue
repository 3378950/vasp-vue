<template>
  <div class="app-container">
    <el-row>
      <el-col :span="12" class="card-box">
        <el-card>
          <div slot="header"><span>整改情况</span></div>

          <el-tabs v-model="activeName">

            <el-tab-pane label="整改中" name="first">
              <el-table
                :data="unFinishedPoints"
                style="width: 100%">
                <el-table-column
                  label="序号"
                  width="60">
                  <template slot-scope="scope">
                    <span style="margin-left: 10px">{{ scope.row.markId }}</span>
                  </template>
                </el-table-column>

                <el-table-column
                  label="检测时间"
                  width="120">
                  <template slot-scope="scope">
                    <i class="el-icon-time"></i>
                    <span style="margin-left: 10px">{{ scope.row.testTime }}</span>
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
                  width="150">
                  <template slot-scope="scope">
                    <div slot="reference" class="name-wrapper">
                      {{ scope.row.process }}
                    </div>
                  </template>
                </el-table-column>
                <el-table-column label="操作">
                  <template slot-scope="scope">
                    <el-col :span="1.5">
                      <el-button type="text" @click="rectify(scope.$index)" v-if="scope.row.process == '未责令整改'">
                        <i class="el-icon-s-release el-icon--right"></i>整改
                      </el-button>
                    </el-col>
                    <el-col :span="1.5">
                      <el-button type="text" @click="unfinishedDetailRow(scope.$index, scope.row)"><i class="el-icon-view el-icon--right"></i>查看</el-button>
                    </el-col>
                  </template>
                </el-table-column>
              </el-table>
              <el-pagination
                background
                layout="prev, pager, next"
                :total="unfinishedTotal">
              </el-pagination>
            </el-tab-pane>

            <el-tab-pane label="已整改" name="second">
              <el-table
                :data="finishedPoints"
                style="width: 100%">
                <el-table-column
                  label="序号"
                  width="60">
                  <template slot-scope="scope">
                    <span style="margin-left: 10px">{{ scope.row.markId }}</span>
                  </template>
                </el-table-column>

                <el-table-column
                  label="检测时间"
                  width="120">
                  <template slot-scope="scope">
                    <i class="el-icon-time"></i>
                    <span style="margin-left: 10px">{{ scope.row.testTime }}</span>
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
                  width="150">
                  <template slot-scope="scope">
                    <div slot="reference" class="name-wrapper">
                      {{ scope.row.process }}
                    </div>
                  </template>
                </el-table-column>
                <el-table-column label="操作">
                  <template slot-scope="scope">
                    <el-col :span="1.5">
                      <el-button type="text" @click="finishedDetailRow(scope.$index, scope.row)"><i class="el-icon-view el-icon--right"></i>查看</el-button>
                    </el-col>
                  </template>
                </el-table-column>
              </el-table>
              <el-pagination
                background
                layout="prev, pager, next"
                :total="finishedTotal">
              </el-pagination>
            </el-tab-pane>

          </el-tabs>


        </el-card>
      </el-col>

      <el-col :span="6" class="card-box">


        <el-card>

          <div slot="header"><span>办件箱</span></div>

          <el-col :span="8">
            <el-card class="box-card">
                <div>待处理</div>
                <span class="number">{{undo}}</span> <span class="unit">件</span>
                <el-button type="text">立即处理></el-button>
            </el-card>
          </el-col>

          <el-col :span="8">

            <el-card class="box-card">
              <div style="color: #1890ff">
                <div>已处理</div>
                <span class="number">{{finishedTotal}}</span> <span class="unit">件</span>
                <el-button type="text">查看></el-button>
              </div>
            </el-card>

          </el-col>

          <el-col :span="8">
            <el-card class="box-card">
              <div style="color: orangered;">
                <div>未通过</div>
                <span class="number">5</span> <span class="unit">件</span>
                <el-button type="text">立即处理></el-button>
              </div>
            </el-card>
          </el-col>

        </el-card>

      </el-col>

      <el-col :span="6" class="card-box">
        <el-card>
          <div slot="header"><span>公示公告</span></div>
<!--          <el-row>-->
<!--            <el-col :span="2"><i class="el-icon-bell"></i></el-col>-->
<!--            <el-col :span="16">关于做好全镇农村人居环境村容...</el-col>-->
<!--            <el-col :span="6">2016-05-02</el-col>-->
<!--          </el-row>-->

<!--          <el-row>-->
<!--            <el-col :span="2"><i class="el-icon-bell"></i></el-col>-->
<!--            <el-col :span="16">关于做好全镇农村人居环境村容...</el-col>-->
<!--            <el-col :span="6">2016-05-02</el-col>-->
<!--          </el-row>-->
<!--          <el-row>-->
<!--            <el-col :span="2"><i class="el-icon-bell"></i></el-col>-->
<!--            <el-col :span="16">关于做好全镇农村人居环境村容...</el-col>-->
<!--            <el-col :span="6">2016-05-02</el-col>-->
<!--          </el-row>-->
<!--          <el-row>-->
<!--            <el-col :span="2"><i class="el-icon-bell"></i></el-col>-->
<!--            <el-col :span="16">关于做好全镇农村人居环境村容...</el-col>-->
<!--            <el-col :span="6">2016-05-02</el-col>-->
<!--          </el-row>-->
          <el-table
            :data="notice"
            height="141px"
            :show-header="false"
            size="mini"
          >
            <el-table-column
              label="标题"
              width="230">
              <template slot-scope="scope">
                <i class="el-icon-bell"></i>
                <span style="margin-left: 10px">{{ scope.row.title }}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="发布时间"
              width="90">
              <template slot-scope="scope">
                <div slot="reference" class="name-wrapper">
                  {{ scope.row.time }}
                </div>
              </template>
            </el-table-column>
            <el-table-column label="操作">

            </el-table-column>
          </el-table>
        </el-card>
      </el-col>

      <el-col :span="12" class="card-box">
        <el-card>
          <div slot="header"><span>区域情况汇总</span></div>
          <div class="col-sm-1 col-md-12 col-lg-12" id="myChart" ref="myChart" style="height: 500px;"></div>

        </el-card>
      </el-col>
    </el-row>


    <!--  整改详情-->
    <el-dialog title="整改详情" :visible.sync="detailVisible" width="60%">
      <el-timeline>


        <el-timeline-item v-for="(activity, index) in detailItem.activityList"
                          :timestamp="activity.createTime + ' ' + activity.createBy + ' ' + activity.name"
                          placement="top"
                          size="large"
                          :icon="activity.icon"
                          :type="activity.type"
                          :key="index">
          <el-card>
            {{activity.detail}}
          </el-card>
        </el-timeline-item>

        <el-timeline-item :timestamp="detailItem.testTime + ' ' + '发现违章点'" placement="top" size="large" icon="el-icon-warning" type="warning">
          <el-card>
            <el-row>
              <el-col :span="8">
                <el-image :src="require('@/assets/images/1.png')" fit="fill" style="width: 220px; height: 200px"></el-image>
              </el-col>
              <el-col :span="16">
                <el-descriptions title="违规情况" direction="vertical" :column="4" border>
                  <el-descriptions-item label="序号">{{detailItem.markId}}</el-descriptions-item>
                  <el-descriptions-item label="经纬度">{ {{detailItem.lat}} , {{detailItem.lng}} }</el-descriptions-item>
                  <el-descriptions-item label="所属地区" :span="2">{{detailItem.province + detailItem.city + detailItem.district}}</el-descriptions-item>
                  <el-descriptions-item label="检测目标">
                    <el-tag size="small" type="warning">{{detailItem.target}}</el-tag>
                  </el-descriptions-item>
                  <el-descriptions-item label="详细地址">{{detailItem.address}}</el-descriptions-item>
                </el-descriptions>
              </el-col>
            </el-row>
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </el-dialog>

    <!-- 发布整改通知-->
    <el-dialog title="责令整改" :visible.sync="rectifyVisible" width="60%">
      <el-card>
        <el-row>
          <el-col :span="8">
            <el-image :src="require('@/assets/images/1.png')" fit="fill" style="width: 220px; height: 200px"></el-image>
          </el-col>
          <el-col :span="16">
            <el-descriptions title="违规情况" direction="vertical" :column="4" border>
              <el-descriptions-item label="序号">{{rectifyItem.markId}}</el-descriptions-item>
              <el-descriptions-item label="经纬度">{ {{rectifyItem.lat}} , {{rectifyItem.lng}} }</el-descriptions-item>
              <el-descriptions-item label="所属地区" :span="2">{{rectifyItem.province + rectifyItem.city + rectifyItem.district}}</el-descriptions-item>
              <el-descriptions-item label="检测目标">
                <el-tag size="small" type="warning">{{rectifyItem.target}}</el-tag>
              </el-descriptions-item>
              <el-descriptions-item label="详细地址">{{rectifyItem.address}}</el-descriptions-item>
            </el-descriptions>
          </el-col>
        </el-row>
        <span> </span>
        <el-form ref="form" :model="form" :rules="rules" label-width="100px">
          <el-form-item label="动态名称" prop="name">
            <el-input :disabled="true" v-model="form.name" :placeholder="form.name" />
          </el-form-item>
          <el-form-item label="监测点编号" prop="markId">
            <el-input  :disabled="true" v-model="form.markId" :placeholder="form.markId" />
          </el-form-item>
          <el-form-item label="动态详情" prop="detail">
            <el-input :rows="5" type="textarea" v-model="form.detail" placeholder="请输入动态详情" />
          </el-form-item>

          <el-form-item label="接收者角色" prop="receiverRole">
            <el-select v-model="form.receiverRole" placeholder="请选择接收者角色">
              <el-option
                v-for="role in roleList"
                v-if="currentUser.level < role.level"
                :key="role.value"
                :label="role.label"
                :value="role.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="接收者区域" prop="receiverRegion">
            <el-input v-model="form.receiverRegion" placeholder="请输入接收者区域" />
          </el-form-item>
        </el-form>

      </el-card>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  import bd from '../../../assets/geo/bd.json';
  import {listMark} from "../../../api/platform/mark";
  import {getUserProfile} from "../../../api/system/user";
  import {jsonp} from "vue-jsonp";
  import {addActivity, updateActivity} from "../../../api/platform/activity";
    export default {
      data() {
        return {

          currentUser: {
              username: "",
              province: "",
              city: "",
              district: "",
              level: null,
          },

          activeName: 'first',
          rectifyVisible: false,
          detailVisible: false,
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
          notice: [
            {
              title: '关于做好全镇农村人居环境村容',
              time: '2016-05-02',
            },
            {
              title: '关于做好全镇农村人居环境村容',
              time: '2016-05-02',
            },
            {
              title: '关于做好全镇农村人居环境村容',
              time: '2016-05-02',
            },
            {
              title: '关于做好全镇农村人居环境村容',
              time: '2016-05-02',
            }
          ],
          unFinishedPoints: null,
          unfinishedTotal: null,
          finishedPoints: null,
          finishedTotal: null,
          // not finished
          queryParams1: {
            pageNum: 1,
            pageSize: 10,
            markId: null,
            lng: null,
            lat: null,
            province: "",
            city: "",
            district: "",
            target: null,
            address: null,
            testTime: null,
            animation: null,
            finished: false,
          },
          // finished
          queryParams2: {
            pageNum: 1,
            pageSize: 10,
            markId: null,
            lng: null,
            lat: null,
            province: "",
            city: "",
            district: "",
            target: null,
            address: null,
            testTime: null,
            animation: null,
            finished: true,
          },
          total: null,
          detailItem: {
            activityList: null,
            address: '',
            lat: '',
            lng: '',
            target: '',
            province: '',
            city: '',
            district: '',
            testTime: '',
            markId: '',
            finished: null,
          },
          rectifyItem: {},
          undo: 0,
          doing: null,
          form: {},
          rules: {
            name: [
              { required: true, message: "动态名称不能为空", trigger: "blur" }
            ],
            markId: [
              { required: true, message: "所属监测点不能为空", trigger: "blur" }
            ],
            createTime: [
              { required: true, message: "创建时间不能为空", trigger: "blur" }
            ],
            createBy: [
              { required: true, message: "创建者不能为空", trigger: "blur" }
            ],
            receiverRole: [
              { required: true, message: "接收者角色不能为空", trigger: "blur" }
            ],
            receiverRegion: [
              { required: true, message: "接收者区域不能为空", trigger: "blur" }
            ],
          },
          roleList: [{
              value: "省级管理员",
              level: 1,
            },{
              value: "市级管理员",
              level: 2,
            },{
              value: "区县级管理员",
              level: 3,
            }
          ]
        }
      },
      created() {

        this.initData();

        // this.$nextTick(() => {
        //   this.initCharts();
        // });
        //
        // this.getNotFinishedMarkList();
        //
        // this.getFinishedMarkList();

      },
      methods: {

        initData() {
          getUserProfile().then(response => {

            // 初始化当前用户信息
            this.currentUser.username = response.data.userName;
            this.currentUser.province = response.data.province;
            this.currentUser.city = response.data.city;
            this.currentUser.district = response.data.district;
            this.currentUser.level = response.data.level;

            console.log(response);
            const lev = response.data.level;
            this.queryParams1.province = response.data.province;
            this.queryParams2.province = response.data.province;
            // console.log(this.queryParams.province);
            if(lev == 2) {
              this.queryParams1.city = response.data.city;
              this.queryParams2.city = response.data.city;
            } else if(lev == 3) {
              this.queryParams1.district = response.data.district;
              this.queryParams2.district = response.data.district;
            }

            // console.log(this.queryParams1.province, this.queryParams1.city, this.queryParams1.district);
            this.initCharts();

            // 获取未完成监测点
            this.getUnfinishedMarkList()


            // 获取已完成监测点
            this.getFinishedMarkList();
          });
        },


        initCharts() {
          this.$echarts.registerMap('bd', bd);
          let myChart = this.$echarts.init(this.$refs.myChart)
          let option = {
            backgroundColor: 'transparent',
            title: {
            },
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

          }

          myChart.setOption(option)
          window.addEventListener('resize', function () {
            myChart.resize()
          })
        },

        // 获取未完成监测点
        getUnfinishedMarkList() {
          listMark(this.queryParams1).then(response => {
            this.unFinishedPoints = response.rows;
            this.unfinishedTotal = response.total;
            for(let i = 0; i < this.unfinishedTotal; i++) {
              if(this.unFinishedPoints[i].process == "未责令整改") this.undo++;
              const tg = this.unFinishedPoints[i].target;
              if(tg == "乱搭乱建") this.unFinishedPoints[i].type = "danger";
              else if(tg == "乱堆乱放") this.unFinishedPoints[i].type = "warning";
              else if(tg == "乱贴乱画") this.unFinishedPoints[i].type = "info";

              const ps = this.unFinishedPoints[i].process;

              if(ps == "未责令整改") this.unFinishedPoints[i].attType = "warning";
              else if(ps == "整改中") this.unFinishedPoints[i].attType = "";
              else if(ps == "反馈未通过") this.unFinishedPoints[i].attType = "danger";
            }
            // console.log(this.unFinishedPoints);
          });
        },

        // 获取已完成监测点
        getFinishedMarkList() {
          listMark(this.queryParams2).then(response => {
            this.finishedPoints = response.rows;
            this.finishedTotal = response.total;

            for(let i = 0; i < this.finishedTotal; i++) {
              this.finishedPoints[i].attType = "success";

              const tg = this.finishedPoints[i].target;
              if(tg == "乱搭乱建") this.finishedPoints[i].type = "danger";
              else if(tg == "乱堆乱放") this.finishedPoints[i].type = "warning";
              else if(tg == "乱贴乱画") this.finishedPoints[i].type = "info";

            }
            // console.log(this.finishedPoints);
          });
        },

        // 已完成监测点 详情页
        async finishedDetailRow(index, row) {
          this.detailItem = null;
          const rowData = this.finishedPoints[index];
          this.detailItem = rowData;

          for(let i = 0; i < this.detailItem.activityList.length; i++) {
            const act = this.detailItem.activityList[i];
            if(act.name == "责令整改通知") {
              this.detailItem.activityList[i].icon = "el-icon-s-release";
              this.detailItem.activityList[i].type = "danger";
            } else if(act.name == "整改反馈") {
              this.detailItem.activityList[i].icon = "el-icon-s-check";
              this.detailItem.activityList[i].type = "warning";
            } else if(act.name == "反馈通过") {
              this.detailItem.activityList[i].icon = "el-icon-s-claim";
              this.detailItem.activityList[i].type = "success";
            }
          }

          this.detailVisible = true;
          console.log(this.detailItem);
        },

        rectify(index) {
          this.rectifyItem = null;

          this.rectifyVisible = true;

          this.rectifyItem = this.unFinishedPoints[index];

          this.form.name = "责令整改通知";

          this.form.process = "整改中";

          this.form.createBy = this.currentUser.username;

          this.form.markId = this.rectifyItem.markId;
        },

        // 未完成监测点 详情页
        async unfinishedDetailRow(index, row) {
          this.detailItem = null;
          const rowData = this.unFinishedPoints[index];
          this.detailItem = rowData;

          for(let i = 0; i < this.detailItem.activityList.length; i++) {
            const act = this.detailItem.activityList[i];
            if(act.name == "责令整改通知") {
              this.detailItem.activityList[i].icon = "el-icon-s-release";
              this.detailItem.activityList[i].type = "danger";
            } else if(act.name == "整改反馈") {
              this.detailItem.activityList[i].icon = "el-icon-s-check";
              this.detailItem.activityList[i].type = "warning";
            } else if(act.name == "反馈通过") {
              this.detailItem.activityList[i].icon = "el-icon-s-claim";
              this.detailItem.activityList[i].type = "success";
            }
          }
          this.detailVisible = true;
          console.log(this.detailItem);
        },

        // 取消按钮
        cancel() {
          this.rectifyVisible = false;
          this.reset();
        },
        // 表单重置
        reset() {
          this.form = {
            activityId: null,
            name: null,
            markId: null,
            detail: null,
            process: null,
            createTime: null,
            createBy: null,
            receiverRole: null,
            receiverRegion: null,
            receiverLevel: null
          };
          this.resetForm("form");
        },

        /** 提交按钮 */
        submitForm() {
          this.$refs["form"].validate(valid => {
            if (valid) {
              if (this.form.activityId != null) {
                updateActivity(this.form).then(response => {
                  this.$modal.msgSuccess("修改成功");
                  this.rectifyVisible = false;
                  this.initData();
                });
              } else {
                addActivity(this.form).then(response => {
                  this.$modal.msgSuccess("新增成功");
                  this.rectifyVisible = false;
                  this.initData();
                });
              }
            }
          });
        },

      }
    }
</script>

<style scoped>

  .box-card {
    width: 100px;
    margin-bottom: 10px;
    border-radius: 10px;
    height: 100%;
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
  .el-pagination {
    text-align:center;
    margin-top:25px;
  }

</style>
