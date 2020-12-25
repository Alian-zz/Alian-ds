<template>
  <div>
    <div>
      <div class="crumbs">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item>
            <i class="el-icon-lx-cascades"></i> 商品类型->属性列表
          </el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <div class="container">
        <div class="handle-box">
          <el-button
            type="primary"
            icon="el-icon-circle-plus"
            class="handle-del mr10"
            @click="addAttr"
          >属性新增
          </el-button>
          <el-input v-model="query.name" placeholder="属性名称" class="handle-input mr10"></el-input>
          <el-button type="primary" icon="el-icon-search" @click="handleSearch">查询</el-button>
        </div>
        <el-table
          :data="tableData"
          border
          class="table"
          header-cell-class-name="table-header"
        >
          <el-table-column prop="id" label="编号" width="55" align="center"></el-table-column>
          <el-table-column prop="name" label="属性名称"></el-table-column>
          <el-table-column prop="productAttributeCategoryId" label="商品类型">
            <template slot-scope="scope">{{$route.query.cname}}</template>
          </el-table-column>
          <el-table-column prop="selectType" label="属性是否可选">
            <template slot-scope="scope">{{scope.row.selectType|selectTypeFilter}}</template>
          </el-table-column>
          <el-table-column prop="inputType" label="属性值录入方式">
            <template slot-scope="scope">{{scope.row.inputType|inputTypeFilter}}</template>
          </el-table-column>
          <el-table-column prop="inputList" label="可选值列表"></el-table-column>
          <el-table-column prop="sort" label="排序"></el-table-column>
          <el-table-column label="操作" width="180" align="center">
            <template slot-scope="scope">
              <el-button
                type="text"
                icon="el-icon-edit"
                @click="handleEdit(scope.row)"
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
      <el-dialog :title="titleName" :visible.sync="editVisible" width="50%" v-if="editVisible">
        <el-form ref="attrForm" :model="attrForm" label-width="140px">
          <el-form-item label="属性名称" prop="name">
            <el-input v-model="attrForm.name"></el-input>
          </el-form-item>
          <el-form-item label="类型名称" prop="typeName">
          <el-input readOnly v-model="attrForm.typeName"></el-input>
          </el-form-item>
          <el-form-item label="分类筛选样式:">
            <el-radio-group v-model="attrForm.filterType">
              <el-radio :label="0">普通</el-radio>
              <el-radio :label="1">颜色</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="能否进行检索:">
            <el-radio-group v-model="attrForm.searchType">
              <el-radio :label="0">不需要检索</el-radio>
              <el-radio :label="1">关键字检索</el-radio>
              <el-radio :label="2">范围检索</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="商品属性关联:">
            <el-radio-group v-model="attrForm.relatedStatus">
              <el-radio :label="1">是</el-radio>
              <el-radio :label="0">否</el-radio>
            </el-radio-group>
          </el-form-item>

          <el-form-item label="属性是否可选:">
            <el-radio-group v-model="attrForm.selectType">
              <el-radio :label="0">唯一</el-radio>
              <el-radio :label="1">单选</el-radio>
              <el-radio :label="2">复选</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="属性值的录入方式:">
            <el-radio-group v-model="attrForm.inputType">
              <el-radio :label="0">手工录入</el-radio>
              <el-radio :label="1">从下边列表中选择</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="属性值可选值列表:" prop="inputListFormat">
            <el-input
              type="textarea"
              :rows="2"
              v-model="inputListFormat">
            </el-input>
          </el-form-item>
          <el-form-item label="是否支持手动新增:">
            <el-radio-group v-model="attrForm.relatedStatus">
              <el-radio :label="1">是</el-radio>
              <el-radio :label="0">否</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="排序:" prop="sort">
            <el-input v-model="attrForm.sort"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible=false">取 消</el-button>
                <el-button type="primary" @click="saveAttr">确 定</el-button>
            </span>
      </el-dialog>

    </div>
  </div>
</template>

<script>
  import {fetchAttributeCategoryListData,getArrtCategoryById,saveOrUpdateCategory} from "../../../api/pms/attrList";

  export const attrForm = {
    name: "",
    selectType:0,
    inputType:0,
    typeName:null,
    type:0,
    productAttributeCategoryId:null,
    inputList:null,
    sort:0,
    filterType:0,
    searchType:0,
    relatedStatus:0,
    handAddStatus:0
  }
  export default {
    name: "AttrList",
    data() {
      return {
        query: {
          name: '',
          cid: this.$route.query.cid,
          type: this.$route.query.type,
          size: 5,
          current: 1
        },
        attrForm: Object.assign({}, attrForm),
        inputListFormat:null,
        tableData: [],
        pageSizes: [5, 10, 15, 20],
        editVisible: false,
        pageTotal: 0,
        titleName: "属性新增",
        idx: -1,
        id: -1
      }
    },
    methods: {
      getData() {
        fetchAttributeCategoryListData(this.query).then(result => {
          this.tableData = result.data.records;
          this.pageTotal = result.data.total;
        })

      },
      restAttrFormDate(){
        this.attrForm = Object.assign({}, attrForm);
        this.attrForm.productAttributeCategoryId=this.$route.query.cid;
        this.attrForm.typeName=this.$route.query.cname;
        this.attrForm.type=this.$route.query.type;
      },
      handleSearch() {
        this.$set(this.query, 'current', 1);
        this.getData();
      },
      saveAttr() {
        saveOrUpdateCategory(this.attrForm).then(result => {
          if (result.code === 200) {
            this.$message.success("保存类型数据成功");
            this.editVisible = false;
            if (this.titleName === "类型新增") {
              this.$set(this.query, 'current', 1);
            }
            this.getData();

          } else {
            this.$message.error("保存类型数据失败");
          }
        })
      },
      addAttr() {
        this.editVisible = true;
        this.titleName = "属性新增";
        this.restAttrFormDate();
      },
      handlePageChange(val) {
        this.$set(this.query, 'current', val);
        this.getData();
      },
      handleSizeChange(val) {
        this.$set(this.query, 'current', 1);
        this.$set(this.query, 'size', val);
        this.getData();
      },
      handleEdit(row) {
        let id = row.id;
        getArrtCategoryById(id).then(result => {
          this.attrForm = result.data;
          this.editVisible = true;
          this.titleName = "类型修改";
          this.attrForm.typeName=this.$route.query.cname;

          this.inputListFormat = this.attrForm.inputList;

        })
      }
    },
    watch:{
      inputListFormat:function (newValue,oldValue) {
        if(newValue.indexOf(",") === -1 ){
          newValue=newValue.replace(/\n/g,',');
          this.attrForm.inputList=newValue;
        }else {
          newValue=newValue.replace(',','\n');
          this.inputListFormat=newValue;
        }

      }
    },
    filters: {
      selectTypeFilter(value){
        if(value === 0){
          return "唯一";
        }else if(value === 1){
          return "单选";
        }else{
          return "多选";
        }
      },
      inputTypeFilter(value){
        if(value === 0){
          return "手工录入";
        }else{
          return "从列表中选取";
        }
      }

    },
    mounted() {
      this.getData();
      this.restAttrFormDate();
    }
  }
</script>

<style scoped>

</style>
