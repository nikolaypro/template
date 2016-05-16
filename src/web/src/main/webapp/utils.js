(function () {
    'use strict';
    $.isShowRequired = function(el) {
        return (el.$$parentForm.$submitted || el.$touched) && el.$error.required;
    };
})();
