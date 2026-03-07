export default {
    uuid: '__f864324f7bfa4115a4aa4f7c827ec7b5',
    alert(text) {
        alert(text);
        return false;
    },
    clone(obj) {
        return JSON.parse(JSON.stringify(obj));
    },
    localSet(key, value) {
        localStorage.setItem(key + this.uuid, JSON.stringify(value));
    },
    localGet(key, def) {
        let parse = JSON.parse(localStorage.getItem(key + this.uuid));
        if (parse || parse === 0 || parse === false)
            return parse;
        return def;
    },
    getDateStr(date) {
        const fullYear = date.getFullYear();
        const month = date.getMonth() + 1;
        const day = date.getDate();
        return fullYear + (month < 10 ? '-0' : '-') + month + (day < 10 ? '-0' : '-') + day;
    },
    getTimeStr(date) {
        return date.toTimeString().substring(0, 8);
    },
    getDateTimeStr(date) {
        return this.getDateStr(date) + ' ' + this.getTimeStr(date);
    }
};
