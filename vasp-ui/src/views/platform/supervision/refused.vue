<template>
    <div>
      <el-card class="box-card">
        <div style="color: orangered;">
          <div>举报信箱</div>
          <span class="number">5</span> <span class="unit">件</span>
          <el-button type="text">立即处理></el-button>
        </div>
      </el-card>
    </div>
</template>

<script>
    export default {
        name: "refused",
        data() {
          return {

          }
        },
        created() {
        },
        methods: {

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
