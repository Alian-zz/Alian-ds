<template>
<div>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"></i> 品牌列表
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box">
        <el-button
                type="primary"
                icon="el-icon-add"
                class="handle-add mr10"
                @click="handleAdd"
        >新增品牌</el-button>
        <el-button
          type="primary"
          icon="el-icon-delete"
          class="handle-del mr10"
          @click="delAllSelection"
        >批量删除</el-button>
        <el-input v-model="query.name" placeholder="品牌名称" class="handle-input mr10"></el-input>
        <el-button type="primary" icon="el-icon-search" @click="handleSearch">查询</el-button>
      </div>
      <el-table
        :data="tableData"
        border
        class="table"
        ref="multipleTable"
        header-cell-class-name="table-header"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="编号" width="55" align="center"></el-table-column>
        <el-table-column prop="name" label="品牌名称"></el-table-column>
        <el-table-column prop="firstLetter" label="首字母"></el-table-column>
        <el-table-column prop="sort" label="排序"></el-table-column>
        <el-table-column label="头像(查看大图)" align="center">
          <template slot-scope="scope">
            <el-image
              class="table-td-thumb"
              :src="scope.row.logo"
              :preview-src-list="[scope.row.logo]"
            ></el-image>
          </template>
        </el-table-column>
        <el-table-column label="品牌制造商" width="100" align="center">
          <template slot-scope="scope">
            <el-switch
              @change="handleFactoryStatusChange(scope.$index, scope.row)"
              :active-value="1"
              :inactive-value="0"
              v-model="scope.row.factoryStatus">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column label="是否显示" width="100" align="center">
          <template slot-scope="scope">
            <el-switch
              @change="handleShowStatusChange(scope.$index, scope.row)"
              :active-value="1"
              :inactive-value="0"
              v-model="scope.row.showStatus">
            </el-switch>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="180" align="center">
          <template slot-scope="scope">
            <el-button
              type="text"
              icon="el-icon-edit"
              @click="handleEdit(scope.$index, scope.row)"
            >编辑</el-button>
            <el-button
              type="text"
              icon="el-icon-delete"
              class="red"
              @click="handleDelete(scope.$index, scope.row)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination
          background
          layout="total, sizes, prev, pager, next, jumper"
          :current-page="query.current"
          :page-size="query.size"
          :page-sizes="pageSizes"
          :total="pageTotal"
          @current-change="handlePageChange"
          @size-change="handleSizeChange"
        ></el-pagination>
      </div>
    </div>

    <!-- 新增/修改弹出框 -->
    <el-dialog :title="brandTitle" :visible.sync="editVisible" width="35%">
      <el-form ref="form" :model="brandForm" label-width="40%" v-if="editVisible">
        <el-input type="hidden" v-model="brandForm.id"></el-input>
        <el-form-item label="品牌名称" label-width="30%" prop="name">
          <el-input v-model="brandForm.name"></el-input>
        </el-form-item>
        <el-form-item label="首字母" label-width="30%">
          <el-input v-model="brandForm.firstLetter"></el-input>
        </el-form-item>
        <el-form-item label="品牌logo" label-width="30%" prop="logo">
          <single-upload v-model="brandForm.logo"></single-upload>
        </el-form-item>
        <el-form-item label="是否为品牌制造商" label-width="30%" prop="factoryStatus">
          <el-radio-group v-model="brandForm.factoryStatus">
            <el-radio v-model="brandForm.factoryStatus" :label="1">是</el-radio>
            <el-radio v-model="brandForm.factoryStatus" :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否显示" label-width="30%" prop="showStatus">
          <el-radio-group v-model="brandForm.showStatus">
            <el-radio v-model="brandForm.showStatus" :label="1">是</el-radio>
            <el-radio v-model="brandForm.showStatus" :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>

      </el-form>
      <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
    </el-dialog>
  </div>
</div>
</template>

<script>

  import {
    fetchAddBrand,
    fetchBrandData,
    fetchFactoryStatus,
    fetchShowStatus,
    fetchUpdateBrand
  } from '../../../api/pms/brand'

  import SingleUpload from "../../../components/upload/singleUpload";

  export const brandForm = {
            id: '',
            name: '',
            firstLetter: '',
            factoryStatus: 1,
            showStatus: 1,
            logo: '',
          }

    export default {
        name: "BrandMain",
      components: {SingleUpload},
      data() {
            return {
              query: {
                name: '',
                size: 5,
                current: 1
              },
              tableData: [],
              multipleSelection: [],
              delList: [],
              pageSizes:[5,10,15,20],
              editVisible: false,
              pageTotal: 0,
              brandTitle: '',
              brandForm: Object.assign({},brandForm),
              idx: -1,
              id: -1
            }
        },
        methods: {
          // 获取 easy-mock 的模拟数据
          getData() {
            fetchBrandData(this.query).then(res => {
              this.tableData = res.data.records;
              this.pageTotal =  res.data.total ;
            });
          },
          // 触发搜索按钮
          handleSearch() {
            this.$set(this.query, 'pageIndex', 1);
            this.getData();
          },
          // 删除操作
          handleDelete(index, row) {
            // 二次确认删除
            this.$confirm('确定要删除吗？', '提示', {
              type: 'warning'
            })
              .then(() => {
                this.$message.success('删除成功');
                this.tableData.splice(index, 1);
              })
              .catch(() => {});
          },
          // 多选操作
          handleSelectionChange(val) {
            this.multipleSelection = val;
          },
          //批量删除
          delAllSelection() {
            const length = this.multipleSelection.length;
            let str = '';
            this.delList = this.delList.concat(this.multipleSelection);
            for (let i = 0; i < length; i++) {
              str += this.multipleSelection[i].name + ' ';
            }
            this.$message.error(`删除了${str}`);
            this.multipleSelection = [];
          },
          //新增操作
          handleAdd(){
            this.brandForm = Object.assign({},brandForm)
            this.editVisible = true;
          },
          // 编辑操作
          handleEdit(index, row) {
            this.idx = index;
            this.brandForm = row;
            this.editVisible = true;
          },
          // 保存编辑
          saveEdit() {
            if(this.brandForm.id === '' && this.brandForm.id === null){
              fetchAddBrand(this.brandForm).then(res => {
                if(res.code === 200){
                  this.editVisible = false;
                  this.$message.success("新增成功");
                }else {
                  this.$message.success("新增失败");
                }
              });
            }else {
              fetchUpdateBrand(this.brandForm).then(res => {
                if(res.code === 200){
                  this.editVisible = false;
                  this.$message.success("修改成功");
                }else {
                  this.$message.success("修改失败");
                }
              });
            }

          },
          // 分页导航
          handlePageChange(val) {
            this.$set(this.query, 'current', val);
            this.getData();
          },
          handleSizeChange(val){
            this.$set(this.query, 'current', 1);
            this.$set(this.query, 'size', val);
            this.getData();
          },
          //是否为品牌制造商按钮改变事件
          handleFactoryStatusChange(val,row){
            fetchFactoryStatus(row.id,row.factoryStatus).then(res => {
            });
          },
          //是否显示按钮改变事件
          handleShowStatusChange(val,row){
            fetchShowStatus(row.id,row.showStatus).then(res => {
            });
          }
        },
        mounted() {
          this.getData();
        }
    }
</script>

<style >
  .handle-box {
    margin-bottom: 20px;
  }

  .handle-select {
    width: 120px;
  }

  .handle-input {
    width: 300px;
    display: inline-block;
  }
  .table {
    width: 100%;
    font-size: 14px;
  }
  .red {
    color: #ff0000;
  }
  .mr10 {
    margin-right: 10px;
  }
  .table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
  }

</style>
