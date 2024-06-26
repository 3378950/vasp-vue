<template>
  <div>
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
      v-show="finishedPoints.length > 0"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      :total="finishedPoints.length"
      @pagination="getFinishedMarkList">
    </el-pagination>

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
            <div v-if="activity.name !== '整改反馈'">{{activity.detail}}</div>
            <div v-else-if="activity.imgsList.length">
              <el-carousel indicator-position="outside" height="250px">
                <el-carousel-item v-for="img in activity.imgsList" :key="item">
                  <img
                    :src="suffix + img"
                    style="display: block; max-width: 50%; margin: 0 auto"
                  />
                </el-carousel-item>
              </el-carousel>
            </div>
          </el-card>
        </el-timeline-item>

        <el-timeline-item :timestamp="detailItem.testTime + ' ' + '发现违章点'" placement="top" size="large" icon="el-icon-warning" type="warning">
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

  </div>
</template>

<script>
    import {getUserProfile} from "../../../../api/system/user";
    import {listMark} from "../../../../api/platform/mark";

    export default {
        name: "finishedTable",
        data() {
          return {
            suffix: process.env.VUE_APP_BASE_API,

            // TODO: 可通过props方式传参： currentUser、finishedPoints
            currentUser: {
              username: "",
              role: "",
              province: "",
              city: "",
              district: "",
              level: null,
              region: "",
            },
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
              finished: true,
            },
            finishedPoints: [],
            detailVisible: false,
            detailItem: {},
          }
        },
        created() {
          this.initData();
        },
        methods: {
          // 初始化用户信息
          initData() {
            getUserProfile().then(response => {
              this.currentUser.username = response.data.userName;
              this.currentUser.role = response.data.roles[0].roleKey;
              this.currentUser.province = response.data.province;
              this.currentUser.city = response.data.city;
              this.currentUser.district = response.data.district;
              this.currentUser.level = response.data.level;

              if(this.currentUser.level === 1) this.currentUser.region = this.currentUser.province;
              if(this.currentUser.level === 2) this.currentUser.region = this.currentUser.city;
              if(this.currentUser.level === 3) this.currentUser.region = this.currentUser.district;

              const lev = response.data.level;
              // 初始化 获取所属地区监测点表单
              this.queryParams.province = response.data.province;
              if(lev === 2) {
                this.queryParams.city = response.data.city;
              } else if(lev === 3) {
                this.queryParams.city = response.data.city;
                this.queryParams.district = response.data.district;
              }
              // 获取未完成监测点
              this.getFinishedMarkList()
            });
          },
          getFinishedMarkList() {
              listMark(this.queryParams).then(response => {
                this.finishedPoints = response.rows;

                for(let i = 0; i < this.finishedPoints.length; i++) {
                  this.finishedPoints[i].attType = "success";

                  const tg = this.finishedPoints[i].target;
                  if(tg == "乱搭乱建") this.finishedPoints[i].type = "danger";
                  else if(tg == "乱堆乱放") this.finishedPoints[i].type = "warning";
                  else if(tg == "乱贴乱画") this.finishedPoints[i].type = "info";
                }
              });
          },
          // 已完成监测点 详情页
          async finishedDetailRow(index, row) {
            this.detailItem = null;
            const rowData = this.finishedPoints[index];
            this.detailItem = rowData;

            for(let i = 0; i < this.detailItem.activityList.length; i++) {
              const act = this.detailItem.activityList[i];
              if(act.name === "责令整改通知" || act.name === "反馈未通过") {
                this.detailItem.activityList[i].icon = "el-icon-s-release";
                this.detailItem.activityList[i].type = "danger";
              } else if(act.name === "整改反馈") {
                this.detailItem.activityList[i].icon = "el-icon-s-check";
                this.detailItem.activityList[i].type = "warning";
                this.detailItem.activityList[i].imgsList = this.detailItem.activityList[i].imgs.split(',');
              } else if(act.name === "审核通过") {
                this.detailItem.activityList[i].icon = "el-icon-s-claim";
                this.detailItem.activityList[i].type = "success";
              }
            }
            this.detailVisible = true;
          },
        }
    }
</script>

<style scoped>
  .el-pagination {
    text-align:center;
    margin-top:25px;
  }
</style>
