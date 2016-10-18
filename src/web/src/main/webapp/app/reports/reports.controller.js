(function () {
    'use strict';

    angular
        .module('app')
        .controller('ReportsController', ReportsController);

    ReportsController.$inject = ['LocMsg', '$log', 'Utils', 'ReportsService'];
    function ReportsController(LocMsg, $log, Utils, ReportsService) {
        var vm = this;
        vm.currReport = "-1";

        vm.signInOutReport = {
            createReport: function() {
                Utils.showConfirm("Info",
                        "Start create signInOutReport: " + vm.signInOutReport.dateFrom + " - " + vm.signInOutReport.dateTo,
                    function(dialogRef) {});
            }
        };
        vm.nameUsersReport = {
            createReport: function() {
//                Utils.showConfirm("Info", "Start create nameUsersReport: ",function(dialogRef) {});
                ReportsService.reportUsers(function (data) {
                    $log.info("Success");
//                    $log.info(data.data.reportContent);

/*
                    var file = new Blob([data.data.reportContent], { type: 'application/pdf' });
                    var fileURL = URL.createObjectURL(file);
                    window.open(fileURL);
*/
//                    window.open(fileURL, "EPrescription");

                    //PDF
                    var pdfAsDataUri = "data:application/pdf;base64, " + data.data.reportContent;
                    var myWindow = window.open(pdfAsDataUri);
                    myWindow.focus();

//                    var data = "<p>This is 'myWindow'</p>";
/*
                    var myWindow = window.open("data:text/html;base64," + data.data.reportContent,
                        "_blank", "width=100%,height=100%");
                    myWindow.focus();
*/

/*
                    var htmlAsDataUri = "data:application/html;base64, " + data.data.reportContent;
                    window.open(htmlAsDataUri);
*/

                    /*
                                        var url = URL.createObjectURL(new Blob([data.data.reportContent]));
                                        var a = document.createElement('a');
                                        a.href = url;
                                        a.download = 'users.pdf';
                                        a.target = '_blank';
                                        a.click();
                    */

                });

            }
        };

        vm.tmpReport = {
            createReport: function() {
                Utils.showConfirm("Info",
                        "Start create tmpReport: " + vm.tmpReport.dateFrom + " - " + vm.tmpReport.dateTo,
                    function(dialogRef) {});
            }
        };

    }

})();
