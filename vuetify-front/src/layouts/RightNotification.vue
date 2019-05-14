<template>
    <v-navigation-drawer
            temporary
            :right="true"
            :value="drawer"
            fixed
            app
            @input="drawerUpdate"
    >
        <v-toolbar flat prominent dark class="primary">
            <v-toolbar-title>Notifications</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-btn icon @click.stop="drawerUpdate(false)">
                <v-icon>close</v-icon>
            </v-btn>
        </v-toolbar>
        <v-list subheader dense>
            <v-subheader>All notifications</v-subheader>
            <v-list-tile @click="">
                <v-list-tile-action>
                    <v-icon>person_add</v-icon>
                </v-list-tile-action>
                <v-list-tile-title>
                    12 new users registered
                </v-list-tile-title>
            </v-list-tile>
            <v-divider></v-divider>
            <v-list-tile @click="">
                <v-list-tile-action>
                    <v-icon>data_usage</v-icon>
                </v-list-tile-action>
                <v-list-tile-title>
                    DB overloaded 80%
                </v-list-tile-title>
            </v-list-tile>
        </v-list>
    </v-navigation-drawer>
</template>

<script>
    export default {
        name: "RightNotification",
        props: {
            drawer: {
                type: Boolean,
                default: false
            }
        },
        methods: {
            drawerUpdate(val){
                this.$emit("update:drawer",val);
            }
        }
    }
</script>

<style scoped>

</style>