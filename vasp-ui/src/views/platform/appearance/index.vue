<template>
    <div>
        <!--地图展示  -->
        <baidu-map class="map" :center="center" :zoom="mapZoomLevel" :scroll-wheel-zoom="true">
          <bm-boundary :name="areaName" :strokeWeight="3" strokeColor="blue" fill-color="#1890FF" map-types="BMAP_SATELLITE_MAP">
          </bm-boundary>
          <template v-for="point in points">
              <bm-marker v-bind:position="{lat: point.lat, lng: point.lng}" :dragging="false" @click="openDrawer(point)" :animation="point.animation">
              </bm-marker>
          </template>
        </baidu-map>

        <!-- 抽屉表格     -->
        <el-drawer :title="drawerTitle" ref="navDrawer" :visible.sync="showTable" :wrapper-closable=true direction="rtl" size="34%" :modal=false style="top: calc(100% - calc(100% - 50px));" close="currentRow = null">
          <el-card class="box-card">
            <el-table ref="singleTable" :data="points" highlight-current-row @current-change="handleCurrentChange" style="width: 100%">

              <el-table-column label="序号" width="50">
                <template slot-scope="scope">
                  {{ scope.$index }}
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
                  <el-button size="mini" type="text" icon="el-icon-reading" @click="detailRow(scope.$index, scope.row)">
                    详情
                  </el-button>

                  <el-button size="mini" type="text" icon="el-icon-notebook-1" @click="reformRow(scope.$index, scope.row)">
                    整改
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
        <el-row>
          <el-col :span="8">
            <el-image :src="require('@/assets/images/1.png')" fit="fill" style="width: 250px; height: 200px"></el-image>
          </el-col>
          <el-col :span="16">
            <el-descriptions title="违规情况" direction="vertical" :column="4" border>
              <el-descriptions-item label="序号">{{detailItem.markId}}</el-descriptions-item>
              <el-descriptions-item label="经纬度">{ {{detailItem.lng}} , {{detailItem.lat}} }</el-descriptions-item>
              <el-descriptions-item label="所属地区" :span="2">{{detailItem.region}}</el-descriptions-item>
              <el-descriptions-item label="检测目标">
                <el-tag size="small" type="warning">{{detailItem.target}}</el-tag>
              </el-descriptions-item>
              <el-descriptions-item label="详细地址">{{detailItem.address}}</el-descriptions-item>
            </el-descriptions>
          </el-col>
        </el-row>

        <span slot="footer" class="dialog-footer">
          <el-button @click="detailDialogVisible = false">关闭</el-button>
          <el-button type="primary" @click="openReformDialog">整改</el-button>
        </span>
      </el-dialog>

    </div>
</template>

<script>
    import {getUserProfile} from "../../../api/system/user";
    import { listMark } from "@/api/platform/mark";

    import { jsonp } from 'vue-jsonp';
    export default {
      name: "index",
      data() {
        return {
          mapZoomLevel: null,
          showTable: true,
          areaName: "",
          detailDialogVisible: false,
          reformDialogVisible: false,
          drawerTitle: "",
          center: null,
          points: null,
          queryParams: {
            pageNum: 1,
            pageSize: 10,
            markId: null,
            lng: null,
            lat: null,
            region: null,
            target: null,
            address: null,
            testTime: null,
            animation: null,
            finished: null
          },
          total: null,
          currentRow: null,
          form: {
            name: '',
            region: '',
            date1: '2022-06-12',
            date2: '',
            delivery: false,
            type: [],
            resource: '',
            desc: 'XXX村XXX'
          },
          detailItem: {
            activityList: null,
            address: '',
            lat: '',
            lng: '',
            target: '',
            region: '',
            testTime: '',
            markId: '',
          },
        }
      },
      created() {
        this.getArea();
        this.getMarkList();
      },
      methods: {
        getArea() {
          getUserProfile().then(response => {
            // console.log(response.data);
            const level = response.data.dept.deptName;
            if(level == "省级部门") {
              this.mapZoomLevel = 8;
            } else if(level == "市级部门") {
              this.mapZoomLevel = 10;
            } else if(level == "县级部门") {
              this.mapZoomLevel = 13;
            }
            this.areaName = response.data.region;
            this.drawerTitle = response.data.region + "村容村貌监测数据";
            const res =  jsonp('http://api.map.baidu.com/geocoding/v3/', {
              address: this.areaName,
              output: 'json',
              ak: 'tambG09mID5LqjrXplXXzpG5Pjpgq2DH',
              callback: 'showLocation'
            });
            res.then(ress => {
              this.center = ress.result.location;
            })
          });
        },
        getMarkList() {
          listMark(this.queryParams).then(response => {
            this.points = response.rows;
            this.total = response.total;
            for(let i = 0; i < this.total; i++) {
              this.points[i].animation = "";
            }
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
        async detailRow(index, row) {
          const rowData = this.points[index];
          this.detailItem = rowData;
          this.detailDialogVisible = true;
          console.log(this.detailItem);
        },
        // 整改
        async reformRow(index, row) {
          alert(index);
        },
        openReformDialog(row) {
          this.reformDialogVisible = true;
          alert(row);
        }
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
