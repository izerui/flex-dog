<template>
    <v-card>
        <v-card-title>
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
                <td class="text-xs-left">{{ props.item.CPUUtilization }}</td>
                <td class="text-xs-left">{{ props.item.MemUsage }}</td>
                <td class="text-xs-left">{{ props.item.DataSpaceUsage }}</td>
                <td class="text-xs-left">{{ props.item.RootSpaceUsage }}</td>
                <td class="text-xs-left">{{ props.item.RunnableProcessCount }}</td>
                <td class="text-xs-left">{{ props.item.BlockProcessCount }}</td>
                <td class="text-xs-left">{{ props.item.ProcessCount }}</td>
                <td class="text-xs-left">{{ props.item.TcpConnectCount }}</td>
                <td class="text-xs-left">{{ props.item.DiskReadOps }}</td>
                <td class="text-xs-left">{{ props.item.NICOut }}</td>
                <td class="text-xs-left">{{ props.item.NetPacketIn }}</td>
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
                    {text: '机器名', align: 'left', value: 'Name'},
                    {text: 'CPU使用率(%)', value: 'CPUUtilization'},
                    {text: '内存使用率(%)', value: 'MemUsage'},
                    {text: '数据盘使用率(%)', value: 'DataSpaceUsage'},
                    {text: '系统盘使用率(%)', value: 'RootSpaceUsage'},
                    {text: '运行进程数', value: 'RunnableProcessCount'},
                    {text: '阻塞进程数', value: 'BlockProcessCount'},
                    {text: '进程总数', value: 'ProcessCount'},
                    {text: 'TCP连接数', value: 'TcpConnectCount'},
                    {text: '磁盘读写次数', value: 'DiskReadOps'},
                    {text: '网卡出入带宽(Mb/s)', value: 'NICOut'},
                    {text: '网卡出入包(个/s)', value: 'NetPacketIn'},
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
            timeFormat(time){
                const newTime = new Date(time * 1000);
                return newTime.toISOString().
                                replace(/T/, ' ').
                                replace(/\..+/, '')
            },
            config(item){
                var _label = item.CPU + "/U、";
                _label += item.Memory + "/M、";

                var _disk = "";
                item.DiskSet.forEach(disk=>{
                    _disk += disk.Size + "/"+ disk.Type + "、";
                })
                _label +=_disk;
                return _label;
            },
            getIps(item){
                var _label = "";
                item.IPSet.forEach(ipObj=>{
                    if (_label) {
                        _label = _label + "\n" + ipObj.IP;
                    } else {
                        _label = ipObj.IP;
                    }
                })
                return _label;
            },
            async loadData() {
                this.loading = true;
                const result = await this.$fly.get('/api/v1/metrics');
                this.dataList = result.DataSet;
                this.loading = false;

                console.log(this.pagination)
            }
        }
    }
</script>

<style scoped>

</style>