<template>
  <component :is="component">
    <slot/>
  </component>
</template>

<script>
  import MainLayout from './layouts/MainLayout'

  export default {
    name: 'App',

    components: {
      MainLayout
    },

    computed: {
      component() {
        return MainLayout
      }
    }
  }
</script>
