<template>
  <el-row>
    <el-col :span="12" class="card-box">
      <el-timeline>
        <el-timeline-item
          v-for="(activity, index) in activities"
          :key="index"
          :icon="activity.icon"
          :type="activity.type"
          :color="activity.color"
          :size="activity.size"
          :timestamp="activity.timestamp">
          {{activity.content}}
        </el-timeline-item>
      </el-timeline>
    </el-col>

    <el-col :span="12" class="card-box">
        表格
    </el-col>

  </el-row>
</template>

<script>
    export default {
        name: "detail",
        data() {
          return {
            activities: [{
              content: '发现违章点',
              timestamp: '2022-04-12 20:46',
              size: 'large',
              type: 'warning',
              icon: 'el-icon-warning'
            }, {
              content: '责令整改通知',
              timestamp: '2022-05-03 8:03',
              size: 'large',
              icon: 'el-icon-s-release',
              type: 'danger',
            }, {
              content: '整改情况反馈',
              timestamp: '2022-05-15 9:26',
              size: 'large',
              type: 'warning',
              icon: 'el-icon-s-check'
            }, {
              content: '反馈情况通过',
              timestamp: '2022-06-01 8:16',
              size: 'large',
              type: 'success',
              icon: 'el-icon-s-claim',
            }]
          };
        }
    }
</script>

<style scoped>

</style>
