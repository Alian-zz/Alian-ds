<template>
    <div class="app-container">
        <el-card class="filter-container" shadow="never">
            <div>
                <i class="el-icon-search"></i>
                <span>筛选搜索</span>
                <el-button
                        style="float:right"
                        type="primary"
                        @click="handleSearchList()"
                        size="small">
                    查询搜索
                </el-button>
                <el-button
                        style="float:right;margin-right: 15px"
                        @click="handleResetSearch()"
                        size="small">
                    重置
                </el-button>
            </div>
            <div style="margin-top: 15px">
                <el-form :inline="true" :model="listQuery" size="small" label-width="140px">
                    <el-form-item label="资源名称：">
                        <el-input v-model="listQuery.name" class="input-width" placeholder="资源名称" clearable></el-input>
                    </el-form-item>
                    <el-form-item label="资源路径：">
                        <el-input v-model="listQuery.url" class="input-width" placeholder="资源路径" clearable></el-input>
                    </el-form-item>
                    <el-form-item label="资源分类：">
                        <el-select v-model="listQuery.categoryId" placeholder="全部" clearable class="input-width">
                            <el-option v-for="item in categoryOptions"
                                       :key="item.id"
                                       :label="item.name"
                                       :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-form>
            </div>
        </el-card>
        <el-card class="operate-container" shadow="never">
            <i class="el-icon-tickets"></i>
            <span>数据列表</span>
            <el-button size="mini" class="btn-add" @click="handleAdd()" style="margin-left: 20px">添加</el-button>
            <el-button size="mini" class="btn-add" @click="handleShowCategory()">资源分类</el-button>
        </el-card>
        <div class="table-container">
            <el-table ref="resourceTable"
                      :data="list"
                      style="width: 100%;"
                      v-loading="listLoading" border>
                <el-table-column label="编号" width="100" align="center">
                    <template slot-scope="scope">{{scope.row.id}}</template>
                </el-table-column>
                <el-table-column label="资源名称" align="center">
                    <template slot-scope="scope">{{scope.row.name}}</template>
                </el-table-column>
                <el-table-column label="资源路径" align="center">
                    <template slot-scope="scope">{{scope.row.url}}</template>
                </el-table-column>
                <el-table-column label="描述" align="center">
                    <template slot-scope="scope">{{scope.row.description}}</template>
                </el-table-column>
                <el-table-column label="添加时间" width="160" align="center">
                    <template slot-scope="scope">{{scope.row.createTime}}</template>
                </el-table-column>
                <el-table-column label="操作" width="140" align="center">
                    <template slot-scope="scope">
                        <el-button size="mini"
                                   type="text"
                                   @click="handleUpdate(scope.$index, scope.row)">
                            编辑
                        </el-button>
                        <el-button size="mini"
                                   type="text"
                                   @click="handleDelete(scope.$index, scope.row)">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div class="pagination-container">
            <el-pagination
                    background
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    layout="total, sizes,prev, pager, next,jumper"
                    :current-page.sync="listQuery.current"
                    :page-size="listQuery.size"
                    :page-sizes="[10,15,20]"
                    :total="total">
            </el-pagination>
        </div>
        <el-dialog
                :title="isEdit?'编辑资源':'添加资源'"
                :visible.sync="dialogVisible"
                width="40%">
            <el-form :model="resourceForm"
                     ref="resourceForm"
                     label-width="150px" size="small">
                <el-form-item label="资源名称：">
                    <el-input v-model="resourceForm.name" style="width: 250px"></el-input>
                </el-form-item>
                <el-form-item label="资源路径：">
                    <el-input v-model="resourceForm.url" style="width: 250px"></el-input>
                </el-form-item>
                <el-form-item label="资源分类：">
                    <el-select v-model="resourceForm.categoryId" placeholder="全部" clearable style="width: 250px">
                        <el-option v-for="item in categoryOptions"
                                   :key="item.id"
                                   :label="item.name"
                                   :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="描述：">
                    <el-input v-model="resourceForm.description"
                              type="textarea"
                              :rows="5"
                              style="width: 250px"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false" size="small">取 消</el-button>
        <el-button type="primary" @click="handleDialogConfirm()" size="small">确 定</el-button>
      </span>
        </el-dialog>
    </div>
</template>

<script>
    import {
        fetchResourceList,
        fetchCategoryOptions,
        saveOrUpdateResource,
        getResourceById,
        deleteResource
    } from "@/api/ums/resource";

    const defaultListQuery = {
        current: 1,
        size: 10,
        name: '',
        url: '',
        categoryId: null
    };
    const defaultResource = {
        id: null,
        name: null,
        url: null,
        categoryId: null,
        description: ''
    };

    export default {
        name: "resource",
        data() {
            return {
                listQuery: Object.assign({}, defaultListQuery),
                list: null,
                total: null,
                listLoading: false,
                dialogVisible: false,
                resourceForm: Object.assign({}, defaultResource),
                isEdit: false,
                categoryOptions: [],
                defaultCategoryId: null
            }
        },
        methods: {
            getList() {
                this.listLoading = true;
                fetchResourceList(this.listQuery).then(response => {
                    this.listLoading = false;
                    this.list = response.data.records;
                    this.total = response.data.total;
                });
            },
            getCategoryOptions() {
                fetchCategoryOptions().then(result => {
                    this.categoryOptions = result.data;
                })
            },
            handleSizeChange(val) {
                this.listQuery.current = 1;
                this.listQuery.size = val;
                this.getList()
            },
            handleCurrentChange(val) {
                this.listQuery.current = val;
                this.getList()
            },
            handleSearchList() {
                this.listQuery.current = 1;
                this.getList();
            },
            handleResetSearch() {
                this.listQuery = Object.assign({}, defaultListQuery)
                this.getList();
            },
            handleAdd() {
                this.resourceForm = Object.assign({}, defaultResource)
                this.dialogVisible = true;
                this.isEdit = false;
            },
            handleUpdate(index, row) {
                getResourceById(row.id).then(result => {
                    if (result.code === 200) {
                        this.resourceForm = result.data;
                        this.dialogVisible = true;
                        this.isEdit = true;
                    }

                })
            },
            handleDelete(index,row){
                this.$confirm('是否要删除该资源?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    deleteResource(row.id).then(result => {
                        if(result.code === 200){
                            this.$message({
                                type: 'success',
                                message: '删除成功!'
                            });
                            this.getList();
                        }else {
                            this.$message.error("删除异常")
                        }

                    });
                });
            },
            handleDialogConfirm() {
                this.$confirm('是否要确认?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    if (this.isEdit) {
                        saveOrUpdateResource(this.resourceForm).then(result => {
                            if (result.code === 200) {
                                this.$message({
                                    message: '修改成功！',
                                    type: 'success'
                                });
                                this.dialogVisible = false;
                                this.getList();
                            } else {
                                this.$message.error("修改失败")
                            }

                        })
                    } else {
                        saveOrUpdateResource(this.resourceForm).then(result => {
                            if (result.code === 200) {
                                this.$message({
                                    message: '添加成功！',
                                    type: 'success'
                                });
                                this.dialogVisible = false;
                                this.getList();
                            } else {
                                this.$message.error("添加失败")
                            }

                        })
                    }
                })
            },
            handleShowCategory(){
                this.$router.push({path: 'resourceCategory'});
            }



        },
        mounted() {
            this.getList();
            this.getCategoryOptions();
        }
    }
</script>

<style scoped>

</style>