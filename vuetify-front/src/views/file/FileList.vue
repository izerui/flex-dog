<template>
    <v-card>
        <v-toolbar card prominent>
            <v-text-field
                    v-model="search"
                    append-icon="search"
                    label="Search"
                    single-line
                    hide-details
            ></v-text-field>
            <v-spacer></v-spacer>
            <v-btn
                    :loading="loading"
                    color="primary"
                    @click="loadData"
            >
                <v-icon left dark>refresh</v-icon>
                重新加载
            </v-btn>
        </v-toolbar>
        <v-data-table
                :headers="headers"
                :items="dataList"
                :search="search"
                :loading="loading"
                :pagination.sync="pagination"
                fix-header
        >
            <template v-slot:items="props">
                <tr>
                    <td class="text-xs-left">{{ props.item.server }}</td>
                    <td class="text-xs-left">{{ props.item.serverAddress }}</td>
                    <td class="text-xs-left">{{ props.item.fileName }}</td>
                    <td class="text-xs-left">{{ props.item.size }}</td>
                    <td class="text-xs-left">{{ props.item.owner }}</td>
                    <td class="text-xs-left">{{ props.item.port }}</td>
                    <td class="text-xs-left">{{ props.item.url }}</td>
                    <td class="text-xs-left">{{ props.item.uploadTimeStr }}</td>
                    <td class="text-xs-left">{{ props.item.deployTimeStr }}</td>
                    <td class="text-xs-left">{{ props.item.status }}</td>
                </tr>
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
                    {text: '服务器', align: 'left', value: 'server'},
                    {text: 'IP地址', value: 'serverAddress'},
                    {text: '文件名称', value: 'fileName'},
                    {text: '文件大小', value: 'size'},
                    {text: '拥有者', value: 'owner'},
                    {text: '端口', value: 'port'},
                    {text: '服务地址', value: 'url'},
                    {text: '上传时间', value: 'uploadTimeStr'},
                    {text: '最后发布时间', value: 'deployTimeStr'},
                    {text: '状态', value: 'status'},
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
                const result = await this.$fly.get('/api/v1/files');
                this.dataList = result.data;
                this.loading = false;
            }
        }
    }
</script>

<style scoped>

</style>