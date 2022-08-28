<template>
    <div>
      <el-card class="box-card">
        <div style="color: #1890ff">
          <div>反馈情况</div>
          <span class="number">{{toVertifyList.length}}</span> <span class="unit">件</span>
          <el-button type="text" :disabled="disable" @click="toVertifyDrawerVisible = true">查看></el-button>
        </div>
      </el-card>
      <!-- 反馈抽屉      -->
      <el-drawer
        title="整改反馈"
        :visible.sync="toVertifyDrawerVisible"
        direction="rtl"
        size="60%">
        <el-table
          :data="toVertifyList"
          style="width: 100%">
          <el-table-column type="expand">
            <template slot-scope="props">
              <el-timeline>


                <el-timeline-item v-for="(activity, index) in props.row.activityList"
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

                <el-timeline-item :timestamp="props.row.testTime + ' ' + '发现违章点'" placement="top" size="large" icon="el-icon-warning" type="warning">
                  <el-card>
                    <el-row>
                      <el-col :span="8">
                        <el-image :src="require('@/assets/images/1.png')" fit="fill" style="width: 220px; height: 200px"></el-image>
                      </el-col>
                      <el-col :span="16">
                        <el-descriptions title="违规情况" direction="vertical" :column="4" border>
                          <el-descriptions-item label="序号">{{props.row.markId}}</el-descriptions-item>
                          <el-descriptions-item label="经纬度">{ {{props.row.lat}} , {{props.row.lng}} }</el-descriptions-item>
                          <el-descriptions-item label="所属地区" :span="2">{{props.row.province + props.row.city + props.row.district}}</el-descriptions-item>
                          <el-descriptions-item label="检测目标">
                            <el-tag size="small" type="warning">{{props.row.target}}</el-tag>
                          </el-descriptions-item>
                          <el-descriptions-item label="详细地址">{{props.row.address}}</el-descriptions-item>
                        </el-descriptions>
                      </el-col>
                    </el-row>
                  </el-card>
                </el-timeline-item>
              </el-timeline>
            </template>
          </el-table-column>
          <el-table-column
            label="检测时间"
            prop="testTime">
          </el-table-column>
          <el-table-column
            label="检测目标"
            prop="target">
          </el-table-column>
          <el-table-column
            label="所在位置"
            prop="address">
          </el-table-column>
          <el-table-column label="审核操作">
            <template slot-scope="scope">

              <el-col :span="1.5">
                  <el-button size="mini" type="success" icon="el-icon-check" @click="handlePass(scope.$index)"></el-button>
              </el-col>
              <el-col :span="1.5">
                  <el-button size="mini" type="danger" icon="el-icon-close" @click="handleRefuse(scope.$index)"></el-button>
              </el-col>
            </template>
          </el-table-column>
        </el-table>
      </el-drawer>

      <el-dialog title="反馈未通过" :visible.sync="refusedDialogVisible" width="60%">
        <el-card>
          <el-row>
            <el-col :span="8">
              <el-image :src="require('@/assets/images/1.png')" fit="fill" style="width: 220px; height: 200px"></el-image>
            </el-col>
            <el-col :span="16">
              <el-descriptions title="违规情况" direction="vertical" :column="4" border>
                <el-descriptions-item label="序号">{{vertifyItem.markId}}</el-descriptions-item>
                <el-descriptions-item label="经纬度">{ {{vertifyItem.lat}} , {{vertifyItem.lng}} }</el-descriptions-item>
                <el-descriptions-item label="所属地区" :span="2">{{vertifyItem.province + vertifyItem.city + vertifyItem.district}}</el-descriptions-item>
                <el-descriptions-item label="检测目标">
                  <el-tag size="small" type="warning">{{vertifyItem.target}}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="详细地址">{{vertifyItem.address}}</el-descriptions-item>
              </el-descriptions>
            </el-col>
          </el-row>

          <el-form ref="form" :model="form" label-width="100px" style="padding: 20px">

            <el-form-item label="动态详情" prop="detail">
              <el-input :rows="5" type="textarea" v-model="form.detail" placeholder="请输入审核意见" />
            </el-form-item>

          </el-form>

        </el-card>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitVertifyForm">确 定</el-button>
        </div>
      </el-dialog>


    </div>
</template>

<script>
    import {getUserProfile} from "../../../api/system/user";
    import {listMark, updateMark} from "../../../api/platform/mark";
    import {addActivity, updateActivity} from "../../../api/platform/activity";

    export default {
        name: "feedback",
        data() {
          return {
            currentUser: {
              username: "",
              role: "",
              province: "",
              city: "",
              district: "",
              level: null,
              region: "",
            },
            unFinishedPoints: [],
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
            },

            form: {},
            markForm: {},

            toVertifyList: [],
            disable: true,

            toVertify: null,
            vertifyItem: {},
            toVertifyDrawerVisible: false,

            refusedDialogVisible: false,
          }
        },

        created() {
            this.initData();
        },

        methods: {
          initData() {
            getUserProfile().then(response => {

              console.log(response);
              // 初始化当前用户信息
              this.currentUser.username = response.data.userName;
              this.currentUser.role = response.data.roles[0].roleKey;
              this.currentUser.province = response.data.province;
              this.currentUser.city = response.data.city;
              this.currentUser.district = response.data.district;
              this.currentUser.level = response.data.level;

              if(this.currentUser.level == 1) this.currentUser.region = this.currentUser.province;
              if(this.currentUser.level == 2) this.currentUser.region = this.currentUser.city;
              if(this.currentUser.level == 3) this.currentUser.region = this.currentUser.district;


              const lev = response.data.level;
              this.queryParams.province = response.data.province;

              if(lev == 2) {
                this.queryParams.city = response.data.city;
              } else if(lev == 3) {
                this.queryParams.city = response.data.city;
                this.queryParams.district = response.data.district;
              }
              // 获取未完成监测点
              this.getUnfinishedMarkList()
            });
          },
          // 获取未完成监测点
          getUnfinishedMarkList() {
            listMark(this.queryParams).then(response => {
              this.unFinishedPoints = response.rows;
              // this.unfinishedTotal = response.total;
              // this.demandList = [];

              // 增加动态各种信息 & 获取上级通知整改的列表
              for(let i = 0; i < this.unFinishedPoints.length; i++) {
                console.log(this.unFinishedPoints[i]);

                const tg = this.unFinishedPoints[i].target;
                if(tg == "乱搭乱建") this.unFinishedPoints[i].type = "danger";
                else if(tg == "乱堆乱放") this.unFinishedPoints[i].type = "warning";
                else if(tg == "乱贴乱画") this.unFinishedPoints[i].type = "info";

                const ps = this.unFinishedPoints[i].process;

                if(ps == "未责令整改") this.unFinishedPoints[i].attType = "warning";
                else if(ps == "整改中") this.unFinishedPoints[i].attType = "";
                else if(ps == "反馈未通过") this.unFinishedPoints[i].attType = "danger";
              }
              this.getToVertifyList();
            });
          },

          getToVertifyList() {
            this.toVertifyList = [];
            for(let i = 0; i < this.unFinishedPoints.length; i++) {
              if(this.unFinishedPoints[i].process !== "待审核") continue;
              // console.log(this.unFinishedPoints[i]);
              const activitytoRole = this.unFinishedPoints[i].activityList[0].receiverRole;
              const activitytoRegion = this.unFinishedPoints[i].activityList[0].receiverRegion;
              console.log(activitytoRole, activitytoRegion);
              console.log(this.currentUser.role, this.currentUser.region);
              if(activitytoRole === this.currentUser.role && activitytoRegion === this.currentUser.region) {
                this.toVertifyList.push(this.unFinishedPoints[i]);
                this.disable = false;
              }
            }
            for(let i = 0; i < this.toVertifyList.length; i++) {
              for(let j = 0; j < this.toVertifyList[i].activityList.length; j++) {
                const act = this.toVertifyList[i].activityList[j];
                if(act.name === "责令整改通知" || act.name === "反馈未通过") {
                  this.toVertifyList[i].activityList[j].icon = "el-icon-s-release";
                  this.toVertifyList[i].activityList[j].type = "danger";
                } else if(act.name === "整改反馈") {
                  this.toVertifyList[i].activityList[j].icon = "el-icon-s-check";
                  this.toVertifyList[i].activityList[j].type = "warning";
                } else if(act.name === "反馈通过") {
                  this.toVertifyList[i].activityList[j].icon = "el-icon-s-claim";
                  this.toVertifyList[i].activityList[j].type = "success";
                }
              }
            }
          },

          handlePass(index) {
            this.reset();

            this.vertifyItem = null;
            // 获取整改项
            this.vertifyItem = this.toVertifyList[index];

            console.log(this.vertifyItem);

            this.form.name = "审核通过";

            this.form.process = "整改完成";

            this.form.createBy = this.currentUser.username;

            this.form.markId = this.vertifyItem.markId;

            this.form.detail = "审核通过";

            this.form.receiverRole = "country-admin";

            this.form.receiverRegion = this.vertifyItem.district;

            this.submitVertifyForm();
          },

          handleRefuse(index) {
            this.reset();

            this.vertifyItem = null;
            // 获取整改项
            this.vertifyItem = this.toVertifyList[index];

            // console.log(this.vertifyItem);

            this.form.name = "反馈未通过";

            this.form.process = "整改中";

            this.form.createBy = this.currentUser.username;

            this.form.markId = this.vertifyItem.markId;

            // this.form.detail = "审核通过";

            this.form.receiverRole = "country-admin";

            this.form.receiverRegion = this.vertifyItem.district;

            this.refusedDialogVisible = true;

            // this.submitVertifyForm();
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
          submitVertifyForm() {

            if (this.form.receiverRole === "country-admin") this.form.receiverLevel = 3;
            else if (this.form.receiverRole === "city-admin") this.form.receiverLevel = 2;
            else if (this.form.receiverRole === "province-admin") this.form.receiverLevel = 1;

            addActivity(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");

              this.markForm = {};
              this.markForm.markId = this.form.markId;
              this.markForm.process = this.form.process;
              if(this.form.name === "审核通过") this.markForm.finished = true;
              else if(this.form.name === "反馈未通过") this.markForm.finished = false;

              this.refusedDialogVisible = false;
              this.toVertifyDrawerVisible = false;

              updateMark(this.markForm).then(response => {
                // 重新获取未完成的监测点列表
                this.getUnfinishedMarkList();
              });

            });
          }
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

</style>
