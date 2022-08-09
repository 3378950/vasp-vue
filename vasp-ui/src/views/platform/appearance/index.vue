<template>
    <div>
        <!--地图展示  -->
        <baidu-map class="map" :center="center" :zoom="mapZoomLevel" :scroll-wheel-zoom="true">
          <bm-boundary :name="areaName" :strokeWeight="3" strokeColor="blue" fill-color="#1890FF" :map-types="BMAP_SATELLITE_MAP">
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
                  乱搭乱建
                </template>
              </el-table-column>

              <el-table-column property="lng" label="检测日期" width="120">
                <template slot-scope="scope">
                  2022-06-12
                </template>
              </el-table-column>

              <el-table-column label="操作" width="125">
                <template slot-scope="scope">
                  <el-button size="mini" type="text" icon="el-icon-reading" @click="centerDialogVisible = true">
                    详情
                  </el-button>

                  <el-button size="mini" type="text" icon="el-icon-notebook-1" @click="clickRow(scope.$index, scope.row)">
                    整改
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </el-drawer>

      <el-dialog title="检测情况" :visible.sync="centerDialogVisible" width="30%" center>
        <el-row>
          <el-col :span="12">
            <el-image :src="require('@/assets/images/1.png')" fit="fill" style="width: 250px; height: 220px"></el-image>
          </el-col>



          <el-col :span="12">
<!--            <div style="font-size: 20px">目标：乱堆乱放</div>-->
<!--            <div style="font-size: 20px">位置：XXX村XXX</div>-->
<!--            <div style="font-size: 20px">检测日期：2022-06-12 </div>-->
<!--            <div style="font-size: 20px">是否整改：否</div>-->
<!--            <div style="font-size: 20px">数据来源：</div>-->
            <el-form ref="form" :model="form" label-width="80px">
              <el-form-item label="目标">
                <el-tag type="danger">乱堆乱放</el-tag>
              </el-form-item>
              <el-form-item label="位置">
                <el-input type="textarea" v-model="form.desc"></el-input>
              </el-form-item>
              <el-form-item label="检测日期">
                  <el-date-picker type="date" placeholder="选择日期" v-model="form.date1" style="width: 100%;"></el-date-picker>
              </el-form-item>
              <el-form-item label="是否整改">
                <el-switch v-model="form.delivery"></el-switch>
              </el-form-item>


            </el-form>
          </el-col>
        </el-row>

        <span slot="footer" class="dialog-footer">
          <el-button @click="centerDialogVisible = false">关闭</el-button>
          <el-button type="primary" @click="centerDialogVisible = false">整改</el-button>
        </span>
      </el-dialog>

    </div>
</template>

<script>
    import {getUserProfile} from "../../../api/system/user";
    import { jsonp } from 'vue-jsonp';
    export default {
      name: "index",
      data() {
        return {
          mapZoomLevel: null,
          showTable: true,
          areaName: "",
          centerDialogVisible: false,
          drawerTitle: "",
          center: null,
          points: [
            {
              lat: 39.39404,
              lng: 115.71517,
              animation: ""
            },
            {

              lat: 39.670073,
              lng: 115.394534,
              animation: ""
            },
            {

              lat: 39.442261,
              lng: 115.704054,
              animation: ""
            },
          ],
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
          }
        }
      },
      created() {
        this.getArea();
      },
      methods: {
        getArea() {
          getUserProfile().then(response => {
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
        async clickRow(index, row) {
          alert(index);
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



</style>
