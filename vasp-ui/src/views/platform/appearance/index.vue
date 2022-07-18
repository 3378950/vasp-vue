<template>
    <div>

        <baidu-map class="map" :center="center" :zoom="11" :scroll-wheel-zoom="true">
          <bm-boundary :name="areaName" :strokeWeight="3" strokeColor="blue" fill-color="#1890FF">
          </bm-boundary>

          <template v-for="point in points">
            <bm-marker v-bind:position="{lat: point.lat, lng: point.lng}" :dragging="false" @click="openDrawer(point)" :animation="point.animation">
            </bm-marker>
          </template>


        </baidu-map>


        <el-drawer
          title="违章情况"
          ref="navDrawer"
          :visible.sync="table"
          :wrapper-closable=true
          direction="rtl"
          size="30%"
          :modal=false
          style="top: calc(100% - calc(100% - 50px));"
          close="currentRow = null">

          <el-card class="box-card">

            <el-table
              ref="singleTable"
              :data="points"
              highlight-current-row
              @current-change="handleCurrentChange"
              style="width: 100%">
              <el-table-column
                type="index"
                width="50">
              </el-table-column>
              <el-table-column
                property="lat"
                label="经度"
                width="120">
              </el-table-column>
              <el-table-column
                property="lng"
                label="纬度"
                width="120">
              </el-table-column>
              <el-table-column
                label="操作"
                width="120">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-reading"
                  @click="op">
                  详情</el-button>
              </el-table-column>

            </el-table>
          </el-card>
        </el-drawer>


    </div>
</template>

<script>
    export default {
      name: "index",
      data() {
        return {
          table: false,
          areaName: "河北省保定市涞水县",
          center: {
            lat: 39.39404, lng: 115.71517
          },
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
          currentRow: null
        }
      },
      created() {
      },
      methods: {
        openDrawer(val) {
          this.handleCurrentChange(val);
          this.table = true;
          this.currentRow = val;
          this.$refs.singleTable.setCurrentRow(val);

        },
        handleCurrentChange(val) {
          this.currentRow = val;
          for(let i = 0; i < this.points.length; i++) {
            this.points[i].animation = "";
          }
          val.animation = "BMAP_ANIMATION_BOUNCE";
        },
        op(index) {

          // console.log(this.currentRow);
          alert(index + 1);
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
</style>
