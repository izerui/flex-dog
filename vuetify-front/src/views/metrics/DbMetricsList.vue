<template>
    <v-card>
        <v-card-title>
            <v-btn
                    :loading="loading"
                    color="primary"
                    @click="loadData"
            >
                <v-icon left dark>refresh</v-icon>
                重新加载
            </v-btn>
            <v-spacer></v-spacer>
            <v-text-field
                    v-model="search"
                    append-icon="search"
                    label="Search"
                    single-line
                    hide-details
            ></v-text-field>

        </v-card-title>
        <v-data-table
                :headers="headers"
                :items="dataList"
                :search="search"
                :loading="loading"
                :pagination.sync="pagination"
                fix-header
        >
            <template v-slot:items="props">
                <td class="text-xs-left">{{ props.item.Name }}</td>
                <td class="text-xs-left">{{ props.item.MemSize }}</td>
                <td class="text-xs-left">{{ props.item.CPUUtilization }}</td>
                <td class="text-xs-left">{{ props.item.MemUsage }}</td>
                <td class="text-xs-left">{{ props.item.DiskUsage }}</td>
                <td class="text-xs-left">{{ props.item.IO }}</td>
                <td class="text-xs-left">{{ props.item.IOOps }}</td>
                <td class="text-xs-left">{{ props.item.ConnectionCount }}</td>
                <td class="text-xs-left">{{ props.item.QPS }}</td>
                <td class="text-xs-left">{{ props.item.TPS }}</td>
                <td class="text-xs-left">{{ props.item.ExpensiveQuery }}</td>
            </template>
            <template v-slot:no-results>
                <v-alert :value="true" color="error" icon="warning">
                    未找到包含 "{{ search }}" 的结果.
                </v-alert>
            </template>
        </v-data-table>
    </v-card>
</template>

<script>
    export default {
        data() {
            return {
                search: '',
                headers: [
                    {text: '数据库名称', align: 'left', value: 'Name'},
                    {text: '内存大小(Mb)', value: 'MemSize'},
                    {text: 'CPU使用率(%)', value: 'CPUUtilization'},
                    {text: '内存使用率(%)', value: 'MemUsage'},
                    {text: '磁盘使用率(%)', value: 'DiskUsage'},
                    {text: 'IO流量(Mb/s)', value: 'IO'},
                    {text: 'IO次数', value: 'IOOps'},
                    {text: '连接数', value: 'ConnectionCount'},
                    {text: 'QPS', value: 'QPS'},
                    {text: 'TPS', value: 'TPS'},
                    {text: '慢查询(个)', value: 'ExpensiveQuery'},
                ],
                dataList: [],
                loading: false,
                pagination: {
                    descending: false,
                    page: 1,
                    rowsPerPage: 10,
                    sortBy: null,
                    totalItems: 0
                }
            }
        },
        created() {
            this.loadData();
        },
        methods: {
            async loadData() {
                this.loading = true;
                const result = await this.$fly.get('/api/v1/dbs');
                this.dataList = result.DataSet;
                this.loading = false;
            }
        }
    }
</script>

<style scoped>

</style>