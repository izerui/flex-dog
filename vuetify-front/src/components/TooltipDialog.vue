<template>
    <v-card tile>
        <v-toolbar card dark color="primary">
            <v-btn icon dark @click="$emit('close')">
                <v-icon>close</v-icon>
            </v-btn>
            <v-toolbar-title>{{title}}</v-toolbar-title>
            <v-spacer></v-spacer>
        </v-toolbar>
        <v-card-text>
            {{text}}
        </v-card-text>
    </v-card>
</template>

<script>
    export default {
        name: "TooltipDialog",
        props:{
            text: {
                type: String,
                default: ''
            },
            title: {
                type: String,
                default: ''
            }
        }
    }
</script>

<style scoped>

</style>