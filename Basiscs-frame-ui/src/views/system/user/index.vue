<template>
    <div>
        <el-row>
            <el-col type="flex">
                <el-form :methods="queryParams" label-width="68px" size="small" :inline="true">
                    <el-form-item label="用户名称" prop="queryParams.userName">
                        <el-input />
                    </el-form-item>
                    <el-form-item label="电话号码" prop="queryParams.phonenumber">
                        <el-input />
                    </el-form-item>
                    <el-form-item label="状态" prop="queryParams.undefined">
                        <el-input />
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" icon="el-icon-search" size="mini">搜索</el-button>
                        <el-button icon="el-icon-refresh" size="mini">重置</el-button>
                    </el-form-item>
                </el-form>
                <el-table :data="userList">
                    <el-table-column fixed prop="userId" label="用户ID">
                    </el-table-column>
                    <el-table-column prop="userName" label="账号">
                    </el-table-column>
                    <el-table-column prop="nickName" label="昵称">
                    </el-table-column>
                    <el-table-column prop="phonenumber" label="电话">
                    </el-table-column>
                    <el-table-column prop="email" label="邮箱">
                    </el-table-column>
                    <el-table-column prop="status" label="状态">
                    </el-table-column>
                    <el-table-column fixed="right" label="操作">
                        <template slot-scope="scope">
                            <el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button>
                            <el-button type="text" size="small">编辑</el-button>
                            <el-button type="text" size="small">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="block">
                    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                        :current-page.sync="currentPage" :page-sizes="[20, 50, 100, 200]" :page-size=pageSize
                        layout="sizes, prev, pager, next" :total=total>
                    </el-pagination>
                </div>
            </el-col>
            <el-col type="flex">

            </el-col>

        </el-row>
    </div>
</template>
<script>
import { getUserList } from '@/api/user'
export default {
    data() {
        return {
            //当前页
            currentPage: null,
            //页面大小
            pageSize: 10,
            // 总条数
            total: 0,
            // 用户表格数据
            userList: null,
            queryParams: {
                pageNum: 1,
                pageSize: 10,
                userName: undefined,
                phonenumber: undefined,
                status: undefined,
            },
        }
    },
    created() {
        this.getUserList();
    },
    methods: {

        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
        },

        handleCurrentChange(val) {
            console.log(`当前页: ${val}`);
        },
        /**
         * 查看用户详情
         * @param {} row 
         */
        handleClick(row) {
            console.log(row);
        },
        /**
         * 获取用户列表
         */
        getUserList() {
            getUserList().then(response => {
                this.userList = response.data.result
                this.total = response.data.total
                this.currentPage = response.data.pageNo
                this.pageSize = response.data.pageSize
            })
        }
    },

}
</script>
<style>
.el-row {
    padding-left: 20px;
    margin: 10px 0;
    /* 设置行间隙 */
}

.el-col {
    margin-right: 10px;
    /* 设置列之间的间隙 */
}

.grid-content {
    border-radius: 4px;
    min-height: 36px;
}

.block {

    text-align: center;

}
</style>