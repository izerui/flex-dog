<template>
    <v-card>
        <v-toolbar card prominent>
            <v-label>企业:&nbsp;&nbsp;</v-label>
            <v-combobox
                    v-model="selEntName"
                    :items="entNames"
                    persistent-hint
                    chips
            ></v-combobox>
            <v-spacer></v-spacer>
            <v-text-field
                    v-model="search"
                    label="货品编码"
                    single-line
            ></v-text-field>
            <v-btn :loading="loading"
                   color="primary"
                   @click="loadData">
                <v-icon left dark>search</v-icon>
                搜索
            </v-btn>
        </v-toolbar>
        <v-data-table
                :headers="headers"
                :items="dataList"
                :loading="loading"
                :pagination.sync="pagination"
                :total-items="pagination.totalItems"
                fix-header
                hide-actions
        >
            <template v-slot:items="props">
                <td class="text-xs-left">{{ props.item.inventoryCode }}</td>
                <td class="text-xs-left">{{ props.item.inventoryName }}</td>
                <td class="text-xs-left">{{ props.item.unitName }}</td>
                <td class="text-xs-left">{{ props.item.totalDemandQty }}</td>
                <td class="text-xs-left">{{ props.item.totalPurgeQty }}</td>
                <td class="text-xs-left">{{ props.item.stockQty }}</td>
                <td class="text-xs-left">{{ props.item.usableQty }}</td>
            </template>
        </v-data-table>
        <div class="text-xs-center pt-2">
            <v-pagination v-model="pagination.page" :total-visible="7" :length="pagination.totalPages"></v-pagination>
        </div>
    </v-card>
</template>

<script>
    export default {
        data() {
            return {
                selEntName: '',
                ents: [],
                entNames: [],
                search: '',
                headers: [
                    {text: '货品编码', align: 'left', value: 'inventoryCode'},
                    {text: '货品名称', value: 'inventoryName'},
                    {text: '单位', value: 'unitName'},
                    {text: '总需', value: 'totalDemandQty'},
                    {text: '总净需', value: 'totalPurgeQty'},
                    {text: '库存量', value: 'stockQty'},
                    {text: '可用量', value: 'usableQty'},
                ],
                dataList: [],
                loading: false,
                pagination: {
                    descending: false,
                    page: 1,
                    rowsPerPage: 10,
                    sortBy: null,
                    totalItems: 0,
                    totalPages: 0
                }
            }
        },
        created() {
            this.loadEnts();
        },
        watch: {
            pagination: {
                handler() {
                    this.loadData();
                },
                deep: true
            }
        },
        methods: {
            async loadEnts() {
                const result = await this.$fly.get('/api/v1/ents');
                this.ents = result.data;
                this.entNames = this.ents.map(t => t.name);
            },
            getEntCode() {
                const sss = this.ents.filter(t => t.name === this.selEntName);
                if (sss !== null && sss.length > 0) {
                    return sss[0].code;
                }
                return null;
            },
            async loadData() {
                const entCode = this.getEntCode();
                if (entCode) {
                    this.loading = true;
                    const params = {
                        entCode: entCode,
                        page: this.pagination.page - 1,
                        pageSize: this.pagination.rowsPerPage,
                        keyword: this.search
                    };
                    const result = await this.$fly.get('/api/v1/demands', params);
                    this.dataList = result.data.content;
                    if (result.data.number && result.data.number >= 0) {
                        this.pagination.page = result.data.number + 1;
                    }
                    this.pagination.totalPages = result.data.totalPages;
                    this.pagination.totalItems = result.data.totalElements;
                    this.loading = false;
                }
            }
        }
    }
</script>

<style scoped>

</style>