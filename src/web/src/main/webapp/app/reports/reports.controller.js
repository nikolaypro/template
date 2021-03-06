(function () {
    'use strict';

    angular
        .module('app')
        .controller('ReportsController', ReportsController);

    ReportsController.$inject = ['LocMsg', '$log', 'Utils', 'ReportsService', '$rootScope'];
    function ReportsController(LocMsg, $log, Utils, ReportsService, $rootScope) {
        var vm = this;
        vm.currReport = "-1";
        vm.isCurrentAdminRole = Utils.isCurrentAdminRole;

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
                vm.salaryReport.started = true;
                vm.salaryReport.action = function(progressId, onFinishCallback) {
                    ReportsService.reportSalaryData(vm.salaryReport.date, progressId, function (data) {
                        onFinishCallback();
                        data.data.date = vm.salaryReport.date;
                        ReportsService.openReport('salary', 'Salary report', data.data);
//                    win.reloadRoute();

                    });
                };
/*
                ProgressService.start(function(progressId) {
                    ReportsService.reportSalaryData(vm.salaryReport.date, function (data) {
                        data.data.date = vm.salaryReport.date;
                        ReportsService.openReport('salary', 'Salary report', data.data);
//                    win.reloadRoute();

                    });
                });
*/
            },
            date: Utils.getCurrDateWOTime(),
            started: false
        };

        vm.salaryReportWithSubTypes = {
            createReport: function () {
                vm.salaryReportWithSubTypes.started = true;
                vm.salaryReportWithSubTypes.action = function(progressId, onFinishCallback) {
                    ReportsService.reportSalaryWithSubTypesData(vm.salaryReportWithSubTypes.date, progressId, function (data) {
                        onFinishCallback();
                        data.data.date = vm.salaryReportWithSubTypes.date;
                        var plainReport = [];
                        angular.forEach(data.data.rows, function(jobTypeItem) {
                            plainReport.push({
                                name: jobTypeItem.jobType,
                                cost: jobTypeItem.cost,
                                isJobType: true
                            });
                            angular.forEach(jobTypeItem.subTypes, function(subTypeItem) {
                                plainReport.push({
                                    name: subTypeItem.subtype,
                                    cost: subTypeItem.cost,
                                    isJobType: false
                                });
                            })
                        });
                        data.data.rows = plainReport;
                        ReportsService.openReport('salary-with-subtype', 'Salary report with subtype', data.data);
//                    win.reloadRoute();

                    });
                };
                /*
                 ProgressService.start(function(progressId) {
                 ReportsService.reportSalaryData(vm.salaryReport.date, function (data) {
                 data.data.date = vm.salaryReport.date;
                 ReportsService.openReport('salary', 'Salary report', data.data);
                 //                    win.reloadRoute();

                 });
                 });
                 */
            },
            date: Utils.getCurrDateWOTime(),
            started: false,
            enabled: $rootScope.globals.settings.salaryReportWithSubTypesEnabled
        };

        vm.salaryReportWithGroup = {
            createReport: function () {
                vm.salaryReportWithGroup.started = true;
                vm.salaryReportWithGroup.action = function(progressId, onFinishCallback) {
                    ReportsService.reportSalaryWithGroupData(vm.salaryReportWithGroup.date, progressId, function (data) {
                        onFinishCallback();
                        data.data.date = vm.salaryReportWithGroup.date;
                        var plainReport = [];
                        angular.forEach(data.data.rows, function(groupItem) {
                            angular.forEach(groupItem.subTypes, function(subTypeItem) {
                                plainReport.push({
                                    name: subTypeItem.subtype,
                                    cost: subTypeItem.cost,
                                    isGroup: false
                                });
                            });
                            plainReport.push({
                                cost: groupItem.groupCost,
                                isGroup: true
                            });
                        });
                        data.data.rows = plainReport;
                        ReportsService.openReport('salary-with-group', 'Salary report with group', data.data);

                    });
                };
            },
            date: Utils.getCurrDateWOTime(),
            started: false,
            enabled: $rootScope.globals.settings.reportGroupEnabled
        };

        vm.salaryReportInvestigation = {
            createReport: function () {
                vm.salaryReportInvestigation.started = true;
                vm.salaryReportInvestigation.action = function(progressId, onFinishCallback) {
                    ReportsService.reportSalaryInvestigationData(vm.salaryReportInvestigation.date, progressId, function (data) {
                        onFinishCallback();
                        data.data.date = vm.salaryReportInvestigation.date;
                        var plainReport = [];
                        angular.forEach(data.data.rows, function(subTypeItem) {
                            plainReport.push({
                                name: subTypeItem.subType.subtype,
                                cost: subTypeItem.subType.cost,
                                count: subTypeItem.jobCount,
                                isSubType: true
                            });
                            angular.forEach(subTypeItem.jobs, function(jobItem) {
                                plainReport.push({
                                    name: jobItem.product,
                                    subTypeCost: jobItem.subTypeCost,
                                    count: jobItem.count,
                                    cost: jobItem.subTypeCost * jobItem.count,
                                    isSubType: false
                                });
                            });
                        });
                        data.data.rows = plainReport;
                        ReportsService.openReport('salary-investigation', 'Salary report for investigation', data.data);

                    });
                };
            },
            date: Utils.getCurrDateWOTime(),
            started: false,
            enabled: $rootScope.globals.settings.salaryReportInvestigationEnabled
        };

        vm.salaryLogReport = {
            showLogFiles: function () {
                ReportsService.loadLogFileList(function (data) {
                    vm.logFiles = data.data;
                });
            },
            showLog: function(fileName) {
                ReportsService.loadLogFile(fileName, function (data) {
                    ReportsService.openReport('salary-log', 'Salary log report', data.data[0]);
                });
            }
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
