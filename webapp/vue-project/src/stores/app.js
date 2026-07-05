import { defineStore } from 'pinia'
export const useAppStore = defineStore('app', {
  state: () => ({
    isCollapse: false,
    isDark: false
  }),
  actions: {
    toggleCollapse() {
      this.isCollapse = !this.isCollapse
    },
    toggleDark() {
      this.isDark = !this.isDark
    }
  }
})