<template>
    <div class="app-container">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 商品分类
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="handle-box">
            <el-button
                    type="primary"
                    icon="el-icon-add"
                    class="handle-add mr10"
                    @click="handleAdd"
            >新增分类
            </el-button>
            <!--<el-button
                    type="primary"
                    icon="el-icon-delete"
                    class="handle-del mr10"
                    @click="delAllSelection"
            >批量删除
            </el-button>-->
        </div>
        <div class="table-container">
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
                <el-table-column prop="name" label="分类名称"></el-table-column>
                <el-table-column prop="level" label="级别">
                    <template slot-scope="scope">
                        <span v-if="scope.row.level === 0">一级</span>
                        <span v-else>二级</span>
                    </template>
                </el-table-column>

                <el-table-column label="分类图标" align="center">
                    <template slot-scope="scope">
                        <el-image v-if="scope.row.icon != null"
                                  class="table-td-thumb"
                                  :src="scope.row.icon"
                                  :preview-src-list="[scope.row.icon]"
                        ></el-image>
                    </template>
                </el-table-column>
                <el-table-column prop="productCount" label="商品数量"></el-table-column>
                <el-table-column prop="productUnit" label="数量单位"></el-table-column>
                <el-table-column label="导航栏" width="100" align="center">
                    <template slot-scope="scope">
                        <el-switch
                                @change="handleNavStatusChange(scope.row)"
                                :active-value="1"
                                :inactive-value="0"
                                v-model="scope.row.navStatus">
                        </el-switch>
                    </template>
                </el-table-column>
                <el-table-column label="是否显示" width="100" align="center">
                    <template slot-scope="scope">
                        <el-switch
                                @change="handleShowStatusChange(scope.row)"
                                :active-value="1"
                                :inactive-value="0"
                                v-model="scope.row.showStatus">
                        </el-switch>
                    </template>
                </el-table-column>
                <el-table-column prop="sort" label="排序"></el-table-column>

                <el-table-column label="设置" width="180" align="center">
                    <template slot-scope="scope">
                        <el-button
                                v-if="scope.row.level === 0"
                                size="mini"
                                icon="el-icon-caret-bottom"
                                @click="handleChildren( scope.row)"
                        >查看下级
                        </el-button>

                        <el-button
                                size="mini"
                                v-else
                                icon="el-icon-caret-top"
                                @click="handleParent( scope.row)"
                        >返回上级
                        </el-button>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="180" align="center">
                    <template slot-scope="scope">
                        <el-button
                                type="text"
                                icon="el-icon-edit"
                                @click="handleEdit(scope.row)"
                        >编辑
                        </el-button>
                        <el-button
                                type="text"
                                icon="el-icon-delete"
                                class="red"
                                @click="handleDelete(scope.$index, scope.row)"
                        >删除
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

            <!-- 新增/修改弹出框 -->
            <el-dialog :title="formTitle" :visible.sync="editVisible" width="50%">
                <el-form ref="form" :model="productCategoryForm" label-width="40%" v-if="editVisible">
                    <el-input type="hidden" v-model="productCategoryForm.id"></el-input>
                    <el-form-item label="分类名称：" prop="name">
                        <el-input v-model="productCategoryForm.name"></el-input>
                    </el-form-item>
                    <el-form-item label="上级分类：">
                        <el-select v-model="productCategoryForm.parentId"
                                   placeholder="请选择分类">
                            <el-option
                                    v-for="item in productCateList"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="数量单位：">
                        <el-input v-model="productCategoryForm.productUnit"></el-input>
                    </el-form-item>
                    <el-form-item label="排序：">
                        <el-input v-model="productCategoryForm.sort"></el-input>
                    </el-form-item>
                    <el-form-item label="是否显示：">
                        <el-radio-group v-model="productCategoryForm.showStatus">
                            <el-radio :label="1">是</el-radio>
                            <el-radio :label="0">否</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="是否显示在导航栏：">
                        <el-radio-group v-model="productCategoryForm.navStatus">
                            <el-radio :label="1">是</el-radio>
                            <el-radio :label="0">否</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="分类图标：">
                        <single-upload v-model="productCategoryForm.icon" style="width: 30px"></single-upload>
                    </el-form-item>
                    <el-form-item v-for="(filterProductAttr, index) in filterProductAttrList"
                                  :label="index | filterLabelFilter"
                                  :key="filterProductAttr.key"
                    >
                        <el-cascader
                                clearable
                                v-model="filterProductAttr.value"
                                :options="filterAttrs">
                        </el-cascader>
                        <el-button style="margin-left: 20px" @click.prevent="removeFilterAttr(filterProductAttr)">删除
                        </el-button>
                    </el-form-item>
                    <el-form-item>
                        <el-button size="small" type="primary" @click="handleAddFilterAttr()">新增</el-button>
                    </el-form-item>
                    <el-form-item label="关键词：">
                        <el-input v-model="productCategoryForm.keywords"></el-input>
                    </el-form-item>
                    <el-form-item label="分类描述：">
                        <el-input type="textarea" :autosize="true" v-model="productCategoryForm.description"></el-input>
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
        fetchCategoryData,
        fetchProductCateList,
        fetchCategoryById,
        fetchAddCategory,
        fetchUpdateCategory,
        fetchNavStatusStatus,
        fetchShowStatusCategory,
    } from "../../../api/pms/category";
    import SingleUpload from "../../../components/upload/singleUpload";


    export const productCategoryForm = {
        id: '',
        name: '',
        parentId: 0,
        keywords: '',
        productUnit: '',
        sort: 0,
        showStatus: 1,
        navStatus: 1,
        icon: '',
        description: '',
    }

    export default {
        name: "CategoryMain",
        components: {SingleUpload},
        data() {
            return {
                query: {
                    name: '',
                    size: 5,
                    level: 0,
                    pid: null,
                    current: 1
                },
                formTitle: '',
                tableData: [],
                multipleSelection: [],
                delList: [],
                pageSizes: [5, 10, 15, 20],
                editVisible: false,
                pageTotal: 0,
                productCateList: '',
                productCategoryForm: Object.assign({}, productCategoryForm),
                idx: -1,
                id: -1
            }
        },
        methods: {
            getData() {
                fetchCategoryData(this.query).then(res => {
                    this.tableData = res.data.records;
                    this.pageTotal = res.data.total;
                });
            },
            handleChildren(row) {
                this.$set(this.query, 'pid', row.id);
                this.$set(this.query, 'level', null);
                this.$set(this.query, 'current', 1);
                this.getData();
            },
            handleParent(row) {
                this.$set(this.query, 'pid', null);
                this.$set(this.query, 'level', 0);
                this.$set(this.query, 'current', 1);
                this.getData();
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
            /*------------------------多选操作------------------------------*/
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            handleDelete(){
                this.$message.success("删除成功")
            },
            /*----------------是否显示在导航栏按钮改变事件--------------------*/
            handleNavStatusChange(row){
                fetchNavStatusStatus(row.id,row.navStatus);
            },
            /*-------------------------是否显示-----------------------------------*/
            handleShowStatusChange(row){
                fetchShowStatusCategory(row.id,row.showStatus);
            },
            /*-------------------------查询上级分类下拉框--------------------------*/
            selectProductCateList() {
                fetchProductCateList(this.query.level).then(result => {
                    this.productCateList = result.data;
                    this.productCateList.unshift({id: 0, name: '无上级分类'});
                })
            },
            /*----------------新增按钮触发事件-------------------*/
            handleAdd() {
                this.selectProductCateList()
                this.productCategoryForm = Object.assign({}, productCategoryForm),
                    this.formTitle = "新增分类"
                    this.editVisible = true;
            },
            /*----------------修改按钮触发事件-------------------*/
            handleEdit(row) {
                this.selectProductCateList()
                this.editVisible = true;
                this.formTitle = "修改分类"
                fetchCategoryById(row.id).then(result => {
                    this.productCategoryForm = result.data
                });
            },
            saveEdit() {
                /*判断是新增还是修改*/
                if (this.formTitle === '新增分类') {
                    fetchAddCategory(this.productCategoryForm).then(result => {
                        if (result.code === 200) {
                            this.editVisible = false;
                            this.$message.success("新增成功");
                        } else {
                            this.editVisible = false;
                            this.$message.error("新增失败")
                        }
                    })
                } else {
                    fetchUpdateCategory(this.productCategoryForm).then(result => {
                        if (result.code === 200) {
                            this.editVisible = false;
                            this.$message.success("新增成功");
                        } else {
                            this.$message.error("修改失败");
                            this.getData();
                        }
                    })
                }
            }

        },
        mounted() {
            this.getData();
        }
    }

</script>

<style>
</style>