<template>
    <div>
        <!--地图展示  -->
        <baidu-map class="map" :center="center" :zoom="mapZoomLevel" :scroll-wheel-zoom="true">
          <bm-boundary :name="areaName" :strokeWeight="3" strokeColor="blue" fill-color="#1890FF" map-types="BMAP_SATELLITE_MAP">
          </bm-boundary>
          <template v-for="point in points">
              <bm-marker v-bind:position="{lat: point.lat, lng: point.lng}"
                         :dragging="false" @click="openDrawer(point)"
                         :animation="point.animation">
              </bm-marker>
          </template>
        </baidu-map>

        <!-- 抽屉表格     -->
        <el-drawer :title="drawerTitle" ref="navDrawer" :visible.sync="showTable" :wrapper-closable=true direction="rtl" size="34%" :modal=false style="top: calc(100% - calc(100% - 50px));" close="currentRow = null">
          <el-card class="box-card">
            <el-table ref="singleTable" :data="points" highlight-current-row @current-change="handleCurrentChange" style="width: 100%">
                <el-table-column label="序号" width="50">
                  <template slot-scope="scope">
                    {{ scope.row.markId }}
                  </template>
                </el-table-column>
                <el-table-column label="经度" width="100">
                  <template slot-scope="scope">
                    {{ scope.row.lat }}
                  </template>
                </el-table-column>
                <el-table-column property="lng" label="纬度" width="100">
                  <template slot-scope="scope">
                    {{ scope.row.lng }}
                  </template>
                </el-table-column>
                <el-table-column property="lng" label="检测目标" width="90">
                  <template slot-scope="scope">
                    {{ scope.row.target }}
                  </template>
                </el-table-column>
                <el-table-column property="lng" label="检测日期" width="120">
                  <template slot-scope="scope">
                    {{ scope.row.testTime }}
                  </template>
                </el-table-column>
                <el-table-column label="操作" width="125">
                  <template slot-scope="scope">
                    <el-button size="mini" type="text" icon="el-icon-reading" @click="detailRow(scope.$index)">
                      详情
                    </el-button>
                  </template>
                </el-table-column>
            </el-table>
            <el-pagination
              background
              layout="prev, pager, next"
              :total="total">
            </el-pagination>
          </el-card>
        </el-drawer>
      <el-dialog title="检测情况" :visible.sync="detailDialogVisible" width="60%">
        <el-card>
          <el-row>
            <el-col :span="6">
              <img
                :src="suffix + this.detailItem.img"
                style="width: 160px; height: 168px"
              />
            </el-col>
            <el-col :span="18">
              <el-descriptions direction="vertical" :column="4" border>
                <el-descriptions-item label="序号">{{detailItem.markId}}</el-descriptions-item>
                <el-descriptions-item label="经纬度">{ {{detailItem.lng}} , {{detailItem.lat}} }</el-descriptions-item>
                <el-descriptions-item label="所属地区" :span="2">{{detailItem.province + detailItem.city + detailItem.district}}</el-descriptions-item>
                <el-descriptions-item label="检测目标">
                  <el-tag size="small" type="warning">{{detailItem.target}}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="详细地址">{{detailItem.address}}</el-descriptions-item>
              </el-descriptions>
            </el-col>
          </el-row>
          <el-form ref="form" :model="form" :rules="rules" label-width="100px" style="padding: 20px">
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
        <span slot="footer" class="dialog-footer">
          <el-button @click="detailDialogVisible = false">关闭</el-button>
          <el-button type="primary" @click="submitRectifyForm">整改</el-button>
        </span>
      </el-dialog>

    </div>
</template>

<script>
    import {getUserProfile} from "../../../api/system/user";
    import { listMark } from "@/api/platform/mark";

    import { jsonp } from 'vue-jsonp';
    import {getMark, updateMark} from "../../../api/platform/mark";
    import {addActivity, updateActivity} from "../../../api/platform/activity";
    export default {
      name: "index",
      data() {
        return {
          suffix: process.env.VUE_APP_BASE_API,
          mapZoomLevel: null,
          showTable: true,
          areaName: "",
          detailDialogVisible: false,
          reformDialogVisible: false,
          drawerTitle: "",
          center: null,
          points: null,
          // 获取当前用户信息
          currentUser: {
            username: "",
            role: "",
            province: "",
            city: "",
            district: "",
            level: null,
            region: "",
          },
          // 获取未整改监测点
          form: {},
          queryParams: {
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
            process: "未责令整改",
          },
          total: null,
          currentRow: null,
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
          },
          rectifyForm: {},
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
          roleList: [
            {
              label: "省级管理员",
              value: "province-admin",
              level: 1,
            },{
              label: "市级管理员",
              value: "city-admin",
              level: 2,
            },{
              label: "区县级管理员",
              value: "country-admin",
              level: 3,
            }
          ],
        }
      },
      created() {
        this.initData();
      },

      methods: {
        initData() {
          getUserProfile().then(response => {

            this.currentUser.username = response.data.userName;
            this.currentUser.role = response.data.roles[0].roleKey;
            this.currentUser.province = response.data.province;
            this.currentUser.city = response.data.city;
            this.currentUser.district = response.data.district;
            this.currentUser.level = response.data.level;

            const lev = response.data.level;
            let reg = "";
            this.queryParams.province = response.data.province;
            // console.log(this.queryParams.province);
            if(lev == 1) {
              this.mapZoomLevel = 8;
              reg += response.data.province;
            } else if(lev == 2) {
              this.mapZoomLevel = 10;
              reg += response.data.province + response.data.city;
              this.queryParams.city = response.data.city;
            } else if(lev == 3) {
              this.mapZoomLevel = 13;
              reg += response.data.province + response.data.city + response.data.district;
              this.queryParams.district = response.data.district;
            }
            this.areaName = reg;
            this.drawerTitle = this.areaName + "村容村貌监测数据";
            const res =  jsonp('http://api.map.baidu.com/geocoding/v3/', {
              address: this.areaName,
              output: 'json',
              ak: 'tambG09mID5LqjrXplXXzpG5Pjpgq2DH',
              callback: 'showLocation'
            });
            res.then(ress => {
              this.center = ress.result.location;
            });

            listMark(this.queryParams).then(response => {
              this.points = response.rows;
              this.total = response.total;
              for(let i = 0; i < this.total; i++) {
                this.points[i].animation = "";
              }
            });
          });
        },
        async openDrawer(val) {
          this.handleCurrentChange(val);
          this.showTable = true;
          this.currentRow = val;
          this.$refs.singleTable.setCurrentRow(val);
        },
        handleCurrentChange(val) {
          const index = this.points.indexOf(val);
          this.currentRow = val;
          for(let i = 0; i < this.points.length; i++) {
            this.points[i].animation = "";
          }
          this.points[index].animation = "BMAP_ANIMATION_BOUNCE";
        },
        // 详情
        async detailRow(index) {
          this.form = {};

          const rowData = this.points[index];

          this.detailItem = rowData;

          this.form.name = "责令整改通知";

          this.form.process = "整改中";

          this.form.createBy = this.currentUser.username;

          this.form.markId = this.detailItem.markId;

          this.detailDialogVisible = true;
        },

        submitRectifyForm() {
          this.$refs["form"].validate(valid => {
            if (valid) {
                if(this.form.receiverRole === "country-admin") this.form.receiverLevel = 3;
                else if(this.form.receiverRole === "city-admin") this.form.receiverLevel = 2;
                else if(this.form.receiverRole === "province-admin") this.form.receiverLevel = 1;

                addActivity(this.form).then(response => {
                  this.$modal.msgSuccess("新增成功");
                  this.showTable = false;
                  this.detailDialogVisible = false;

                  this.markForm = {};
                  this.markForm.markId = this.form.markId;
                  this.markForm.process = this.form.process;

                  updateMark(this.markForm).then(response => {
                    this.$modal.msgSuccess("监测点进程更新成功");
                    this.initData();
                    this.showTable = false;
                  });
                });
              }
          });
        },
      }
    }

</script>

<style scoped>
 .map {
   width: 100%;
   height: 1000px;
   /* 高度务必要定义  */
 }

 .el-pagination {
   text-align:center;
   margin-top:25px;
 }

</style>
