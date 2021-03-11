<template>
  <q-layout view="lHh Lpr lFf">
    <q-header elevated>
      <q-toolbar>
        <q-btn
          flat
          dense
          round
          icon="menu"
          aria-label="Menu"
          @click="leftDrawerOpen = !leftDrawerOpen"
        />

        <q-toolbar-title>
          E commerce
        </q-toolbar-title>
        <div>
          <div class="q-pa-md q-gutter-sm">
            <q-btn round color="brown-5" icon="logout" @click="icon = true">
            </q-btn>
            <q-dialog v-model="icon">
              <q-card>
                <q-card-section class="row items-center q-pb-none">
                  <div class="text-h6"> Logout </div>
                  <q-space></q-space>
                  <q-btn icon="close" flat round dense v-close-popup></q-btn>
                </q-card-section>
                <q-card-section>
                  Do you want to logout ?
                </q-card-section>
                <q-card-actions align="right">
                  <q-btn flat label="Yes" color="primary" v-close-popup @click="logout" />
                </q-card-actions>
              </q-card>
            </q-dialog>
          </div>
        </div>
      </q-toolbar>
    </q-header>

    <q-drawer
      v-model="leftDrawerOpen"
      show-if-above
      bordered
      content-class="bg-grey-1"
    >
      <q-list>
        <q-item-label
          header
          class="text-grey-8"
        >
          Menu
        </q-item-label>
        <EssentialLink
          v-for="link in essentialLinks"
          :key="link.title"
          v-bind="link"
        />
      </q-list>
    </q-drawer>

    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script lang="ts">
import EssentialLink from 'components/EssentialLink.vue'

const linksData = [
  {
    title: 'Products',
    icon: 'sports_bar',
    link: '#/list'
  },
  {
    title: 'Shopping cart',
    icon: 'shopping_basket',
    link: '#/basket'
  },
  {
    title: 'History',
    icon: 'history',
    link: '#/history'
  }
]

import { defineComponent, ref } from '@vue/composition-api'

export default defineComponent({
  name: 'MainLayout',
  data: () => ({
    icon: false
  }),
  components: { EssentialLink },
  setup () {
    const leftDrawerOpen = ref(false)
    const essentialLinks = ref(linksData)

    return { leftDrawerOpen, essentialLinks }
  },
  methods: {
    logout () {
      localStorage.clear()
      this.$router.push('/')
    }
  }
})
</script>
