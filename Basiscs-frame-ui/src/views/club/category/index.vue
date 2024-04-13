<template>
    <div>
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
            <el-form-item label="部门名称" prop="deptName">
                <el-input v-model="queryParams.deptName" placeholder="请输入部门名称" clearable
                    @keyup.enter.native="handleQuery" />
            </el-form-item>
            <el-form-item label="状态" prop="status">
                <el-select v-model="queryParams.status" placeholder="部门状态" clearable>
                    <el-option v-for="dict in dict.type.sys_normal_disable" :key="dict.value" :label="dict.label"
                        :value="dict.value" />
                </el-select>
            </el-form-item>
            <!-- <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item> -->
        </el-form>
    </div>
</template>
<script>
import { getCategoryList } from '@/api/backend/subject/category'
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {

    data() {
        return {
            // 查询参数
            queryParams: {
                deptName: undefined,
                status: undefined
            },
            // 显示搜索条件
            showSearch: true,
            // 重新渲染表格状态
            refreshTable: true,
            // 遮罩层
            loading: true,
            // 表格树数据
            deptList: [],
            // 是否展开，默认全部展开
            isExpandAll: true,
            /** 搜索按钮操作 */
            handleQuery() {
                this.getList();
            },
        }
    },
    created() {
        this.getList();
    },
    methods: {
        /** 查询部门列表 */
        getList() {
            this.loading = true;
            getCategoryList(this.queryParams).then(response => {
                this.deptList = this.handleTree(response.data, "deptId");
                this.loading = false;
            });
        },
    }
}

</script>