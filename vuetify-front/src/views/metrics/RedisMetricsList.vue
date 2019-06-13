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
                :hide-actions="true"
                fix-header
        >
            <template v-slot:items="props">
                <td class="text-xs-left">{{ props.item.Name }}</td>
                <td class="text-xs-left">{{ props.item.Usage }}</td>
                <td class="text-xs-left">{{ Math.round(props.item.InstanceUsage / 1024) }}</td>
                <td class="text-xs-left">{{ props.item.QPS }}</td>
                <td class="text-xs-left">{{ props.item.NICOut }}</td>
                <td class="text-xs-left">{{ props.item.NICIn }}</td>
                <td class="text-xs-left">{{ props.item.URedisInstanceConnCount }}</td>
                <td class="text-xs-left">{{ props.item.InstanceKey }}</td>
                <td class="text-xs-left">{{ props.item.InstanceGetHit }}</td>
                <td class="text-xs-left">{{ props.item.InstanceGetHitRate }}</td>
                <td class="text-xs-left">{{ props.item.RedisSinglenodeMaxload }}</td>
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
                    {text: 'redis名称', align: 'left', value: 'Name'},
                    {text: '内存使用率(%)', value: 'Usage'},
                    {text: '空间使用量(Mb)', value: 'InstanceUsage'},
                    {text: 'QPS', value: 'QPS'},
                    {text: '网卡出带宽', value: 'NICOut'},
                    {text: '网卡入带宽', value: 'NICIn'},
                    {text: '实例连接数', value: 'URedisInstanceConnCount'},
                    {text: '实例Key数量', value: 'InstanceKey'},
                    {text: '命中次数', value: 'InstanceGetHit'},
                    {text: '命中率(%)', value: 'InstanceGetHitRate'},
                    {text: 'Redis负载', value: 'RedisSinglenodeMaxload'},
                ],
                dataList: [],
                loading: false,
                pagination: {
                    descending: false,
                    page: 1,
                    rowsPerPage: -1,
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
                const result = await this.$fly.get('/api/v1/metrics/redis');
                this.dataList = result.DataSet;
                this.loading = false;
            }
        }
    }
</script>

<style scoped>

</style>