export default {
    uuid: '__c7bv0',
    alert: function (text) {
        alert(text);
        return false;
    }, mathRandom() {
        return `${Math.random()}`.substr(2);
    },
    localSet: function (key, value) {
        localStorage.setItem(key + this.uuid, JSON.stringify(value));
    },
    localGet: function (key) {
        return JSON.parse(localStorage.getItem(key + this.uuid));
    },
    getDateStr: function (date) {
        const fullYear = date.getFullYear();
        const month = date.getMonth() + 1;
        const day = date.getDate();
        return fullYear + (month < 10 ? '-0' : '-') + month + (day < 10 ? '-0' : '-') + day;
    },
    getTimeStr: function (date) {
        return date.toTimeString().substring(0, 8);
    },
    getDateTimeStr: function (date) {
        return this.getDateStr(date) + ' ' + this.getTimeStr(date);
    },
    exportExcel: function (tableEle) {
        // let sheet = XLSX.utils.table_to_sheet(tableEle);
        // var workbook = XLSX.utils.book_new();
        // XLSX.utils.book_append_sheet(workbook, sheet, "Sheet1");
        let workbook = XLSX.utils.table_to_book(tableEle);
        XLSX.writeFile(workbook, this.getDateTimeStr(new Date()) + '.xlsx');
    }
};
