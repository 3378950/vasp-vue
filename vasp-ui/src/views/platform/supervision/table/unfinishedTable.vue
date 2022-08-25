<template>
  <div>
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
        width="150">
        <template slot-scope="scope">
          <div slot="reference" class="name-wrapper">
            {{ scope.row.address }}
          </div>
        </template>
      </el-table-column>
      <el-table-column
        label="整改进程"
        width="120">
        <template slot-scope="scope">
          <div slot="reference" class="name-wrapper">
            <el-tag size="medium" effect="plain" :type="scope.row.attType">{{ scope.row.process }}</el-tag>
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
      v-show="unFinishedPoints.length > 0"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      :total="unFinishedPoints.length"
      @pagination="getUnfinishedMarkList">
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
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitRectifyForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
    import {getUserProfile} from "../../../../api/system/user";
    import {listMark, updateMark} from "../../../../api/platform/mark";
    import {addActivity, updateActivity} from "../../../../api/platform/activity";

    export default {
        name: "unfinishedTable",
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
            queryParams: {
              pageNum: 1,
              pageSize: 20,
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
            unFinishedPoints: [],

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
            detailItem: {},
            detailVisible: false,

            rectifyItem: {},
            rectifyVisible: false,
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
              this.getUnfinishedMarkList()
            });
          },
          // 获取未完成监测点
          getUnfinishedMarkList() {
            listMark(this.queryParams).then(response => {
              this.unFinishedPoints = response.rows;
              // 增加动态各种信息
              for(let i = 0; i < this.unFinishedPoints.length; i++) {

                const tg = this.unFinishedPoints[i].target;
                if(tg == "乱搭乱建") this.unFinishedPoints[i].type = "danger";
                else if(tg == "乱堆乱放") this.unFinishedPoints[i].type = "warning";
                else if(tg == "乱贴乱画") this.unFinishedPoints[i].type = "info";

                const ps = this.unFinishedPoints[i].process;

                if(ps == "未责令整改") this.unFinishedPoints[i].attType = "warning";
                else if(ps == "整改中") this.unFinishedPoints[i].attType = "";
                else if(ps == "反馈未通过") this.unFinishedPoints[i].attType = "danger";
              }
            });
          },

          // 自主提出整改
          rectify(index) {

            this.form = {},

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
          submitRectifyForm() {
            this.$refs["form"].validate(valid => {
              if (valid) {
                if (this.form.activityId != null) {
                  updateActivity(this.form).then(response => {
                    this.$modal.msgSuccess("修改成功");
                    this.rectifyVisible = false;
                    this.initData();
                  });
                } else {
                  if(this.form.receiverRole === "country-admin") this.form.receiverLevel = 3;
                  else if(this.form.receiverRole === "city-admin") this.form.receiverLevel = 2;
                  else if(this.form.receiverRole === "province-admin") this.form.receiverLevel = 1;

                  addActivity(this.form).then(response => {
                    this.$modal.msgSuccess("新增成功");
                    this.rectifyVisible = false;

                    this.markForm = {};
                    this.markForm.markId = this.form.markId;
                    this.markForm.process = this.form.process;

                    // console.log(this.markForm);

                    updateMark(this.markForm).then(response => {
                      this.$modal.msgSuccess("监测点进程更新成功");
                      this.getUnfinishedMarkList();
                      this.getFinishedMarkList();
                    });
                  });
                }
              }
            });
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
