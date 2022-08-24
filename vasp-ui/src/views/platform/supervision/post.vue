<template>
    <div>
      <el-card>
        <div slot="header"><span>公示公告</span></div>
        <el-table
          :data="notice"
          height="141px"
          :show-header="false"
          size="mini"
        >
          <el-table-column
            label="标题"
            width="230">
            <template slot-scope="scope">
              <i class="el-icon-bell"></i>
              <span style="margin-left: 10px">{{ scope.row.title }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="发布时间"
            width="90">
            <template slot-scope="scope">
              <div slot="reference" class="name-wrapper">
                {{ scope.row.time }}
              </div>
            </template>
          </el-table-column>
          <el-table-column label="操作">

          </el-table-column>
        </el-table>
      </el-card>

    </div>
</template>

<script>
    export default {
        name: "post",
        data() {
          return {
            notice: [
              {
                title: '关于做好全镇农村人居环境村容',
                time: '2016-05-02',
              },
              {
                title: '关于做好全镇农村人居环境村容',
                time: '2016-05-02',
              },
              {
                title: '关于做好全镇农村人居环境村容',
                time: '2016-05-02',
              },
              {
                title: '关于做好全镇农村人居环境村容',
                time: '2016-05-02',
              }
            ],
          }
        },
        created() {
        },
        methods: {

        }
    }
</script>

<style scoped>

</style>
