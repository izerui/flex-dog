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
                <td class="text-xs-left">{{ props.item.UHostId }}</td>
                <td class="text-xs-left">{{ props.item.Tag }}</td>
                <td class="text-xs-left">{{ props.item.Name }}</td>
                <td class="text-xs-left">{{ config(props.item) }}</td>
                <td class="text-xs-left">{{ props.item.OsName }}</td>
                <td class="text-xs-left">{{ getIps(props.item) }}</td>
                <td class="text-xs-left">{{ timeFormat(props.item.CreateTime) }}</td>
                <td class="text-xs-left">{{ timeFormat(props.item.ExpireTime) }}</td>
                <td class="text-xs-left">{{ props.item.State }}</td>
                <td class="text-xs-left">{{ props.item.NetworkState }}</td>
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
                    {
                        text: '资源ID',
                        align: 'left',
                        value: 'UHostId'
                    },
                    {text: '标签', value: 'Tag'},
                    {text: '机器名', value: 'Name'},
                    {text: '配置', value: 'Tag'},
                    {text: '操作系统', value: 'OsName'},
                    {text: 'IP地址', value: 'IPSet'},
                    {text: '创建时间', value: 'CreateTime'},
                    {text: '过期时间', value: 'ExpireTime'},
                    {text: '运行状态', value: 'State'},
                    {text: '网络状态', value: 'NetworkState'}
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
                const result = await this.$fly.get('/api/v1/servers');
                this.dataList = result.UHostSet;
                this.loading = false;

                console.log(this.pagination)
            }
        }
    }
</script>

<style scoped>

</style>