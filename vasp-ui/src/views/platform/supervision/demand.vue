<template>
   <div>
     <el-card class="box-card">
       <div>整改通知</div>
       <span class="number">{{demandList.length}}</span> <span class="unit">件</span>
       <el-button type="text" @click="demandVisible = true" :disabled="disable">立即处理></el-button>
     </el-card>

     <!-- 整改通知 查看   -->
     <el-drawer
       title="整改通知"
       :visible.sync="demandVisible"
       direction="rtl"
       size="60%">
       <el-table
         :data="demandList"
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
         <el-table-column label="操作">
           <template slot-scope="scope">
             <el-col :span="1.5">
               <el-button v-if="true" type="text" @click="rectify(scope.$index)" >
                 <i class="el-icon-s-release el-icon--right"></i>整改
               </el-button>
             </el-col>
             <el-col :span="1.5">
               <el-button type="text" @click="feedback(scope.$index)" >
                 <i class="el-icon-s-release el-icon--right"></i>反馈
               </el-button>
             </el-col>
           </template>
         </el-table-column>
       </el-table>
     </el-drawer>

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

     <el-dialog title="整改反馈" :visible.sync="feedbackVisible" width="60%">
       <el-card>
         <el-row>
           <el-col :span="8">
             <el-image :src="require('@/assets/images/1.png')" fit="fill" style="width: 220px; height: 200px"></el-image>
           </el-col>
           <el-col :span="16">
             <el-descriptions title="违规情况" direction="vertical" :column="4" border>
               <el-descriptions-item label="序号">{{feedbackItem.markId}}</el-descriptions-item>
               <el-descriptions-item label="经纬度">{ {{feedbackItem.lat}} , {{feedbackItem.lng}} }</el-descriptions-item>
               <el-descriptions-item label="所属地区" :span="2">{{feedbackItem.province + feedbackItem.city + feedbackItem.district}}</el-descriptions-item>
               <el-descriptions-item label="检测目标">
                 <el-tag size="small" type="warning">{{feedbackItem.target}}</el-tag>
               </el-descriptions-item>
               <el-descriptions-item label="详细地址">{{feedbackItem.address}}</el-descriptions-item>
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
                 v-if="currentUser.level > role.level"
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
    import {getUserProfile} from "../../../api/system/user";
    import {listMark, updateMark} from "../../../api/platform/mark";
    import {addActivity, updateActivity} from "../../../api/platform/activity";

    export default {
        name: "demand",
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
            // 获取所属地区监测点表单
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
            // 所属地区所有需整改的监测点
            unFinishedPoints: [],
            unfinishedTotal: null,
            // 收到整改通知 抽屉开关
            demandVisible: false,
            // 收到的整改通知列表
            demandList: [],
            // 按钮控制
            disable: true,
            // 动态发布表单
            form: {},
            // 表单校验规则
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
            // 系统角色表
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
            // 需整改项
            rectifyItem: {},
            // 整改对话框开关
            rectifyVisible: false,
            // 需反馈项
            feedbackItem: {},
            // 反馈对话框开关
            feedbackVisible: false,
            // 更新监测点信息表单
            markForm: {},
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

              if(this.currentUser.level == 1) this.currentUser.region = this.currentUser.province;
              if(this.currentUser.level == 2) this.currentUser.region = this.currentUser.city;
              if(this.currentUser.level == 3) this.currentUser.region = this.currentUser.district;

              const lev = response.data.level;
              // 初始化 获取所属地区监测点表单
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

                const tg = this.unFinishedPoints[i].target;
                if(tg == "乱搭乱建") this.unFinishedPoints[i].type = "danger";
                else if(tg == "乱堆乱放") this.unFinishedPoints[i].type = "warning";
                else if(tg == "乱贴乱画") this.unFinishedPoints[i].type = "info";

                const ps = this.unFinishedPoints[i].process;

                if(ps == "未责令整改") this.unFinishedPoints[i].attType = "warning";
                else if(ps == "整改中") this.unFinishedPoints[i].attType = "";
                else if(ps == "反馈未通过") this.unFinishedPoints[i].attType = "danger";
              }
              this.getDemandList();
            });
          },

          getDemandList() {
            this.demandList = [];
            // 获取上级通知整改的列表
            for(let i = 0; i < this.unFinishedPoints.length; i++) {
              if(this.unFinishedPoints[i].process === "待审核") continue;
              for(let j = 0; j < this.unFinishedPoints[i].activityList.length; j++) {
                const activitytoRole = this.unFinishedPoints[i].activityList[j].receiverRole;
                const activitytoRegion = this.unFinishedPoints[i].activityList[j].receiverRegion;

                if(activitytoRole === this.currentUser.role && activitytoRegion === this.currentUser.region) {
                  this.demandList.push(this.unFinishedPoints[i]);
                  this.disable = false;
                  break;
                }
              }
            }


            for(let i = 0; i < this.demandList.length; i++) {
              for(let j = 0; j < this.demandList[i].activityList.length; j++) {
                const act = this.demandList[i].activityList[j];
                if(act.name === "责令整改通知" || act.name === "反馈未通过") {
                  this.demandList[i].activityList[j].icon = "el-icon-s-release";
                  this.demandList[i].activityList[j].type = "danger";
                } else if(act.name === "整改反馈") {
                  this.demandList[i].activityList[j].icon = "el-icon-s-check";
                  this.demandList[i].activityList[j].type = "warning";
                } else if(act.name === "反馈通过") {
                  this.demandList[i].activityList[j].icon = "el-icon-s-claim";
                  this.demandList[i].activityList[j].type = "success";
                }
              }
            }
          },
          // 按照上级要求提出整改
          rectify(index) {
            // 清空动态发布表单
            this.reset();

            this.rectifyItem = null;
            // 整改对话框开启
            this.rectifyVisible = true;
            // 获取整改项
            this.rectifyItem = this.demandList[index];

            this.form.name = "责令整改通知";

            this.form.process = "整改中";

            this.form.createBy = this.currentUser.username;

            this.form.markId = this.rectifyItem.markId;
          },
          // 反馈情况
          feedback(index) {

            this.form = {};

            this.feedbackItem = null;

            this.feedbackVisible = true;

            this.feedbackItem = this.demandList[index];

            this.form.name = "整改反馈";

            this.form.process = "待审核";

            this.form.createBy = this.currentUser.username;

            this.form.markId = this.feedbackItem.markId;
          },

          // 关闭对话框
          cancel() {
            this.feedbackVisible = false;
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
                    this.demandVisible = false;
                    this.feedbackVisible = false;


                    this.markForm = {};
                    this.markForm.markId = this.form.markId;
                    this.markForm.process = this.form.process;

                    updateMark(this.markForm).then(response => {
                      // 重新获取未完成的监测点列表
                      this.getUnfinishedMarkList();
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
