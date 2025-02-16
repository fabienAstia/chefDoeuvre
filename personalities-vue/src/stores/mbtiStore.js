import { defineStore } from "pinia";

export const useMbtiStore = defineStore('mbtiType', {
    state: () => ({result: null}),
    actions: {
        setResult(data) {
            this.result = data;
        },
        clearResult() {
            this.result = null;
        } 
    }
});