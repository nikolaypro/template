(function () {
    'use strict';

    angular
        .module('app')
        .controller('ReportsController', ReportsController);

    ReportsController.$inject = ['LocMsg', '$log', 'Utils', 'ReportsService', '$rootScope'];
    function ReportsController(LocMsg, $log, Utils, ReportsService, $rootScope) {
        var vm = this;
        vm.currReport = "-1";

        vm.signInOutReport = {
            createReport: function () {
                Utils.showConfirm("Info",
                        "Start create signInOutReport: " + vm.signInOutReport.dateFrom + " - " + vm.signInOutReport.dateTo,
                    function (dialogRef) {
                    });
            }
        };
        vm.nameUsersReport = {
            createReport: function () {
                ReportsService.reportUsersData(function (data) {
                    ReportsService.openReport('users', 'Users report', data.data);
//                    win.reloadRoute();

                });


                    /*
                     ReportsService.reportUsers(function (data) {
                     $log.info("Success");
                     //                    $log.info(data.data.reportContent);

                     //                    var file = new Blob([data.data.reportContent], { type: 'application/pdf' });
                     //                    var fileURL = URL.createObjectURL(file);
                     //                    window.open(fileURL);


                     //PDF
                     //                    var pdfAsDataUri = "data:application/pdf;base64, " + data.data.reportContent;
                     //                    var myWindow = window.open(pdfAsDataUri);
                     //                    myWindow.focus();



                     //                    var data = "<p>This is 'myWindow'</p>";
                     //                    var myWindow = window.open("data:text/html;base64," + data.data.reportContent,
                     //                        "_blank", "width=100%,height=100%");
                     //                    myWindow.focus();




                     //                    var htmlAsDataUri = "data:application/html;base64, " + data.data.reportContent;
                     //                    window.open(htmlAsDataUri);

                     });
                     */

            }
        };

        vm.salaryReport = {
            createReport: function () {
                ReportsService.reportSalaryData(vm.salaryReport.date, function (data) {
                    data.data.date = vm.salaryReport.date;
                    ReportsService.openReport('salary', 'Salary report', data.data);
//                    win.reloadRoute();

                });
            },
            date: Utils.getCurrDateWOTime()
        };


        /*
                vm.tmpReport = {
                    createReport: function () {
                        Utils.showConfirm("Info",
                                "Start create tmpReport: " + vm.tmpReport.dateFrom + " - " + vm.tmpReport.dateTo,
                            function (dialogRef) {
                            });
                    }
                };
        */

    }

})();
