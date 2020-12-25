import request from "@/utils/request"

export function fetchMemberLevelList(defaultStatus) {
    return request({
        url: "/api/ums/memberLevel/queryMemberLevelList",
        method: "get",
        params: {
            defaultStatus: defaultStatus
        },
    })

}

export function fetchAddMemberLevel(defaultStatus) {
    return request({
        url: "/api/ums/memberLevel/addMemberLevel",
        method: "post",
        params: {
            defaultStatus: defaultStatus
        },
    })

}