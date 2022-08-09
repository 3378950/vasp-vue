<template>
  <div class="app-container">
    <el-row>
      <el-col :span="12" class="card-box">
        <el-card>
          <div slot="header"><span>审批情况</span></div>
          <el-table
            :data="tableData"
            style="width: 100%">
            <el-table-column
              label="检测时间"
              width="180">
              <template slot-scope="scope">
                <i class="el-icon-time"></i>
                <span style="margin-left: 10px">{{ scope.row.date }}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="姓名"
              width="180">
              <template slot-scope="scope">

                  <div slot="reference" class="name-wrapper">
                    <el-tag size="medium">{{ scope.row.name }}</el-tag>
                  </div>
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                <el-button
                  size="mini"
                  type="danger"
                  @click="handleDelete(scope.$index, scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>

      <el-col :span="6" class="card-box">


        <el-card>
          <div slot="header"><span>办件箱</span></div>
          <el-col :span="8">
            <div class="statistics" style="text-align: center; width: 80px;">
              <div class="title" style="font-size: 12px ">
                <p>待办</p>
                <div style="font-size: 20px">12件</div>
                <div style="margin-bottom: 0px;" @click="">立即处理</div>
              </div>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="statistics" style="text-align: center;width: 80px;">
              <div class="title" style="color: #1890ff;font-size: 12px ">
                <p>已办</p>
                <div style="font-size: 20px">12件</div>

              </div>
            </div>
          </el-col>

          <el-col :span="8">
            <div class="statistics" style="text-align: center;width: 80px;">
              <div class="title" style=" color: red; font-size: 12px">
                <p>未办</p>
                <div style="font-size: 20px">12件</div>

              </div>
            </div>
          </el-col>
        </el-card>

      </el-col>
      <el-col :span="6" class="card-box">
        <el-card>
          <div slot="header"><span>公示公告</span></div>

        </el-card>
      </el-col>

      <el-col :span="12" class="card-box">
        <el-card>
          <div slot="header"><span>区域情况汇总</span></div>

        </el-card>
      </el-col>


    </el-row>
  </div>

</template>

<script>
    export default {
      data() {
        return {
          tableData: [
            {
            date: '2016-05-02',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1518 弄'
          }, {
            date: '2016-05-04',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1517 弄'
          }, {
            date: '2016-05-01',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1519 弄'
          }, {
            date: '2016-05-03',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1516 弄'
          },
            {
              date: '2016-05-02',
              name: '王小虎',
              address: '上海市普陀区金沙江路 1518 弄'
            },{
              date: '2016-05-04',
              name: '王小虎',
              address: '上海市普陀区金沙江路 1517 弄'
            }, {
              date: '2016-05-01',
              name: '王小虎',
              address: '上海市普陀区金沙江路 1519 弄'
            }, {
              date: '2016-05-03',
              name: '王小虎',
              address: '上海市普陀区金沙江路 1516 弄'
            },
            {
              date: '2016-05-02',
              name: '王小虎',
              address: '上海市普陀区金沙江路 1518 弄'
            },{
              date: '2016-05-04',
              name: '王小虎',
              address: '上海市普陀区金沙江路 1517 弄'
            }, {
              date: '2016-05-01',
              name: '王小虎',
              address: '上海市普陀区金沙江路 1519 弄'
            }, {
              date: '2016-05-03',
              name: '王小虎',
              address: '上海市普陀区金沙江路 1516 弄'
            },
            {
              date: '2016-05-02',
              name: '王小虎',
              address: '上海市普陀区金沙江路 1518 弄'
            },{
              date: '2016-05-04',
              name: '王小虎',
              address: '上海市普陀区金沙江路 1517 弄'
            }, {
              date: '2016-05-01',
              name: '王小虎',
              address: '上海市普陀区金沙江路 1519 弄'
            }, {
              date: '2016-05-03',
              name: '王小虎',
              address: '上海市普陀区金沙江路 1516 弄'
            },
            {
              date: '2016-05-02',
              name: '王小虎',
              address: '上海市普陀区金沙江路 1518 弄'
            }]
        }
      },
      methods: {

      }
    }
</script>

<style scoped>
.statistics {
  height: 100px;
  width: 30%;
  border-radius: 10px;
  border: 1px solid #2b2f3a;
  padding: 0 8px;
  margin-bottom: 18px;

  .title {
    display: flex;
    justify-content: center;
    align-items: center;
    color: #2b2f3a;
  }
  p {
    font-size: 8px;
    font-weight: bold;
  }
  .num {
    font-size: 30px;
    font-weight: bold;
    text-align: center;
  }
  .tip {
    font-size: 12px;
  }

}

</style>
