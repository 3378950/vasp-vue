<template>
  <div class="app-container">
    <el-row>
      <el-col :span="12" class="card-box">
        <table-layout></table-layout>
      </el-col>

      <el-col :span="6" class="card-box">
        <el-card>
          <div slot="header"><span>办件箱</span></div>
          <!-- 整改通知 -->
          <el-col :span="8">
            <demand></demand>
          </el-col>
          <!-- 反馈通知 -->
          <el-col :span="8">
            <feedback></feedback>
          </el-col>
          <!-- 反馈未通过 -->
          <el-col :span="8">
            <refused></refused>
          </el-col>
        </el-card>
      </el-col>
      <!-- 公告栏-->
      <el-col :span="6" class="card-box">
        <post></post>
      </el-col>

      <el-col :span="12" class="card-box">
        <chart></chart>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import { getUserProfile } from "../../../api/system/user";
  import Feedback from "./feedback";
  import Demand from "./demand";
  import TableLayout from "./table/tableLayout";
  import Post from "./post";
  import Refused from "./refused";
  import Chart from "./chart";
  export default {
      components: { Refused, TableLayout, Demand, Feedback, Post, Chart },
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
        }
      },
      created() {
        this.initData();
      },
      methods: {
        initData() {
          getUserProfile().then(response => {
            // 初始化当前用户信息
            this.currentUser.username = response.data.userName;
            this.currentUser.role = response.data.roles[0].roleKey;
            this.currentUser.province = response.data.province;
            this.currentUser.city = response.data.city;
            this.currentUser.district = response.data.district;
            this.currentUser.level = response.data.level;

            if (this.currentUser.level == 1) this.currentUser.region = this.currentUser.province;
            if (this.currentUser.level == 2) this.currentUser.region = this.currentUser.city;
            if (this.currentUser.level == 3) this.currentUser.region = this.currentUser.district;

          });
        },
      }
    }
</script>

<style scoped>
</style>
