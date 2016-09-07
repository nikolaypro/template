(function () {
    'use strict';
    $.isShowRequired = function(el) {
        return (el.$$parentForm.$submitted || el.$touched) && el.$error.required;
    };
    String.prototype.format = String.prototype.f = function(){
        var args = arguments;
        return this.replace(/\{(\d+)\}/g, function(m,n){
            return args[n] ? args[n] : m;
        });
    };
})();
