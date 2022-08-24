<template>
  <div>
    <el-card>
      <div slot="header"><span>整改情况</span></div>

      <el-tabs v-model="activeName">
        <el-tab-pane label="整改中" name="first">
          <unfinished-table></unfinished-table>
        </el-tab-pane>

        <el-tab-pane label="已整改" name="second">
          <finished-table></finished-table>
        </el-tab-pane>
      </el-tabs>
    </el-card>

  </div>
</template>

<script>
    import UnfinishedTable from "./unfinishedTable";
    import FinishedTable from "./finishedTable";
    export default {
        name: "tableLayout",
        components: {FinishedTable, UnfinishedTable},
        data() {
          return {
            // 默认打开
            activeName: 'first',
          }
        }
    }
</script>

<style scoped>

</style>
