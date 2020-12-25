<template>
  <div>
    <div>
      <div class="crumbs">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item>
            <i class="el-icon-lx-cascades"></i> 商品类型
          </el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <div class="container">
        <div class="handle-box">
          <el-button
            type="primary"
            icon="el-icon-circle-plus"
            class="handle-del mr10"
            @click="addAttrCategory"
          >类型新增
          </el-button>
          <el-input v-model="query.name" placeholder="类型名称" class="handle-input mr10"></el-input>
          <el-button type="primary" icon="el-icon-search" @click="handleSearch">查询</el-button>
        </div>
        <el-table
          :data="tableData"
          border
          class="table"
          header-cell-class-name="table-header"
        >
          <el-table-column prop="id" label="编号" width="55" align="center"></el-table-column>
          <el-table-column prop="name" label="类型名称"></el-table-column>
          <el-table-column prop="attributeCount" label="属性数量"></el-table-column>
          <el-table-column prop="paramCount" label="参数数量">

          </el-table-column>
          <el-table-column label="设置" width="250" >
            <template slot-scope="scope">
            <el-button
              size="mini"
              icon="el-icon-setting"
              @click="handleAttrs(scope.row)"
            >属性设置</el-button>
            <el-button
              size="mini"
              icon="el-icon-setting"
              @click="handleParams(scope.row)"
            >参数设置</el-button>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="180" align="center">
            <template slot-scope="scope">
              <el-button
                type="text"
                icon="el-icon-edit"
                @click="handleEdit( scope.row)"
              >编辑
              </el-button>
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

      <!-- 编辑弹出框 -->
      <el-dialog :title="titleName" :visible.sync="editVisible" width="40%" v-if="editVisible">
        <el-form ref="attrCategoryForm" :model="attrCategoryForm" label-width="120px">
          <el-form-item label="类型名称" prop="name">
            <el-input v-model="attrCategoryForm.name"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible=false">取 消</el-button>
                <el-button type="primary" @click="saveAttrCategory">确 定</el-button>
            </span>
      </el-dialog>

    </div>
  </div>
</template>

<script>
  import {fetchAttrCategoryData,getArrtCategoryById,saveOrUpdateCategory} from "../../../api/pms/attrCategory";
  const defaultAttrCategoryForm ={
    name:""
  }
  export default {
        name: "ProductAttr",
        data() {
            return {
              query: {
                name: '',
                size: 5,
                current: 1
              },
              attrCategoryForm:Object.assign({}, defaultAttrCategoryForm),
              tableData:[],
              pageSizes: [5, 10, 15, 20],
              editVisible: false,
              pageTotal: 0,
              titleName:"类型新增",
              idx: -1,
              id: -1
            }
        },
        methods: {
          getData(){
            fetchAttrCategoryData(this.query).then(result=>{
              this.tableData=result.data.records;
              this.pageTotal=result.data.total;
            })
          },
          handleSearch(){

          },
          saveAttrCategory(){
            saveOrUpdateCategory(this.attrCategoryForm).then(result=>{
              if(result.code === 200){
                this.$message.success("保存类型数据成功");
                this.editVisible=false;
                if(this.titleName === "类型新增"){
                  this.$set(this.query, 'current', 1);
                }
                this.getData();

              }else{
                this.$message.error("保存类型数据失败");
              }
            })

          },
          addAttrCategory(){
            this.editVisible=true;
            this.titleName="类型新增";
            this.attrCategoryForm=Object.assign({}, defaultAttrCategoryForm);
          },
          handleAttrs(row){
              this.$router.push({path:"/AttrList",query:{cid:row.id,cname:row.name,type:0}})
          },
          handleParams(row){
            this.$router.push({path:"/ParamList",query:{cid:row.id,cname:row.name,type:1}})
          },
          handlePageChange(val){
            this.$set(this.query, 'current', val);
            this.getData();
          },
          handleSizeChange(val){
            this.$set(this.query, 'current', 1);
            this.$set(this.query, 'size', val);
            this.getData();
          },
          handleEdit(row){
            debugger;
            let id=row.id;
            getArrtCategoryById(id).then(result=>{
              this.attrCategoryForm=result.data;
              this.editVisible=true;
              this.titleName="类型修改";
            })
          }
        },
        mounted(){
          this.getData();
        }
    }
</script>

<style scoped>

</style>
