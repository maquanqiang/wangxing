function redirectUrl(targetUrl) {
    window.location.href=targetUrl;
}
function errorHandlerFun(data,errorPlaceId)
{
    if(!data.success_is_ok&&data.code==999) {
        var errorMsg="";
        var map=data.errorMap;
        for (var key in map)
        {
            errorMsg=errorMsg+map[key]+";";
        }
        $(errorPlaceId).text(errorMsg);
        return;
    }
    if(!data.success_is_ok) {
        $(errorPlaceId).text(data.error);
        return;
    }
}
