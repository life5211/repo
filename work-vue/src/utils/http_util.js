import axios from "axios";

axios.defaults.baseURL = '/api';

export default {

    getReq(path, params, fun) {
        axios.get(path, {params}).then(res => fun(res.data.data));
    },
    postReq(path, params, fun) {
        axios.post(path, {params}).then(res => fun(res.data.data));
    },
    deleteReq(path, params, fun) {
        axios.delete(path, {params}).then(res => fun(res.data.data));
    },
    putReq(path, params, fun) {
        axios.put(path, {params}).then(res => fun(res.data.data));
    }

}