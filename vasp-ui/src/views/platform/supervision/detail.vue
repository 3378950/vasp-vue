<template>

  <el-timeline>
    <el-timeline-item :timestamp="'2022-04-12' + ' ' + '发现违章点'" placement="top" size="large" icon="el-icon-warning" type="warning">
      <el-card>
        <el-row>
          <el-col :span="8">
            <el-image :src="require('@/assets/images/1.png')" fit="fill" style="width: 270px; height: 210px"></el-image>
          </el-col>
          <el-col :span="16">
            <el-descriptions title="违规情况" direction="vertical" :column="4" border>
              <el-descriptions-item label="序号">075589</el-descriptions-item>
              <el-descriptions-item label="经纬度">{102.55, 78.55}</el-descriptions-item>
              <el-descriptions-item label="所属地区" :span="2">五尧乡</el-descriptions-item>
              <el-descriptions-item label="检测目标">
                <el-tag size="small" type="warning">乱堆乱放</el-tag>
              </el-descriptions-item>
              <el-descriptions-item label="详细地址">河北省保定市五尧乡xx</el-descriptions-item>
            </el-descriptions>
          </el-col>
        </el-row>
      </el-card>
    </el-timeline-item>

    <el-timeline-item v-for="activity in activities" :timestamp="activity.timestamp + ' ' + activity.content" placement="top" :size="activity.size" :icon="activity.icon" :type="activity.type">
        <el-card>

        </el-card>
      </el-timeline-item>
  </el-timeline>
</template>

<script>
    export default {
        name: "detail",
        data() {
          return {
            activities: [
            {
              content: '责令整改通知',
              timestamp: '2022-05-03 8:03',
              size: 'large',
              icon: 'el-icon-s-release',
              type: 'danger',
              detail: '',
            },
            {
              content: '整改情况反馈',
              timestamp: '2022-05-15 9:26',
              size: 'large',
              type: 'warning',
              icon: 'el-icon-s-check',
              detail: '',
            },
            {
              content: '反馈情况通过',
              timestamp: '2022-06-01 8:16',
              size: 'large',
              type: 'success',
              icon: 'el-icon-s-claim',
              detail: '',
            }],
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
          };
        }
    }
</script>

<style scoped>

</style>
