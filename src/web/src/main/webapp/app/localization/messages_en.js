(function () {
    'use strict';

    angular.module('localization')
        .value('localizedTextsEN', {
            '': '+ Kategorie',
            'edit Carte': 'Karte bearbeiten',

            /*common*/
            'common.new' : 'New',
            'common.edit' : 'Edit',
            'common.remove' : 'Remove',
            'common.save' : 'Save',
            'common.cancel' : 'Cancel',
            'common.close' : 'Close',
            'common.clear' : 'Clear',
            'common.error' : 'Danger',
            'common.loading' : 'Loading',
            'common.no.result.found' : 'No Results Found',
            'common.yes' : 'Yes',
            'common.no' : 'No',
            'common.warning' : 'Warning',
            'common.filter.hide' : 'Hide filter',
            'common.filter.show' : 'Show filter',
            'common.filter.clear.tooltip' : 'Clear filter',
            'common.document.title' : 'Furniture',

            'calendar.current.day' : 'Today',

            /*top.menu*/
            'top.menu.users' : 'Users',
            'top.menu.reports' : 'Reports',
            'top.menu.dictionary' : 'Dictionaries',
            'top.menu.product.type' : 'Product type',
            'top.menu.job.type' : 'Job type',
            'top.menu.job.sub.type' : 'Job sub type',
            'top.menu.job.price' : 'Job price',
            'top.menu.jobs' : 'Jobs',
            'top.menu.logout' : 'Logout',
            'top.menu.app.name' : 'Furniture',

            /*top.menu.user.details*/
            'top.menu.user.details.name' : 'User name',
            'top.menu.user.details.roles' : 'Roles',

            /*user.table.column*/
            'user.table.column.name' : 'Name',
            'user.table.column.login' : 'Login',
            'user.table.column.roles' : 'Roles',

            /*user.table.edit.name*/
            'user.table.edit.title.new' : 'New user',
            'user.table.edit.title.edit' : 'Edit user',

            'user.table.edit.name' : 'Full name',
            'user.table.edit.name.hint' : 'Enter full name',
            'user.table.edit.name.required' : 'Username is required',

            'user.table.edit.login' : 'Login',
            'user.table.edit.login.hint' : 'Enter login',
            'user.table.edit.login.required' : 'Login is required',

            'user.table.edit.edit.password.btn' : 'Edit password',

            'user.table.edit.password' : 'Password',
            'user.table.edit.password.hint' : 'Enter password',
            'user.table.edit.password.required' : 'Password is required',
            'user.table.edit.password.repeat' : 'Repeat password',
            'user.table.edit.password.not.equals' : 'Repeat password must be equals password',

            'user.table.edit.roles' : 'Roles',
            'user.table.edit.roles.not.defined' : 'Not defined any role',

            'user.table.edit.locale' : 'Language',
            'language.ru_RU' : 'Russian',
            'language.en_UK' : 'English',

            /*user.table.edit.messages*/
            'user.table.delete.confirm' : 'Do you want to delete user?',
            'user.table.delete.confirm.many' : 'Do you want to delete {0} users?',

            /* ROLES */
            'ROLE_ADMIN': 'Administrator',
            'ROLE_REGULAR': 'Regular',

            /* REPORTS */
            'report.name.sign.in.out.user' : 'Login/Logout users',
            'report.name.users' : 'Users list',
            'report.create' : 'Create report',
            'report.date.from' : 'From',
            'report.date.to' : 'To',
            'report.create.date' : 'Data creation: {0}',

            'report.users.full_name.column' : 'Name',
            'report.users.login.column' : 'Login',
            'report.users.roles.column' : 'Roles',

            'report.salary' : 'Salary report',
            'report.name.salary' : 'Salary report',
            'report.salary.title.period' : 'Period: {0} - {1}',
            'report.salary.job.type.column' : 'Job type',
            'report.salary.cost.column' : 'Summary cost',

            'report.salary.with.subtypes' : 'Salary report with subtypes',
            'report.name.salary.with.subtypes' : 'Salary report with subtypes',

            'report.salary.with.group' : 'Grouped salary report',
            'report.name.salary.with.group' : 'Grouped salary report',

            'report.please.enable.popup.window' : 'Unable open a new window (tab) for report. Please check a blocking window for your browser',

            /*product.table*/
            'product.table.column.name' : 'Name',
            'product.table.delete.confirm' : 'Do you want to delete product?',
            'product.table.delete.confirm.many' : 'Do you want to delete {0} products?',

            /*product.table.edit*/
            'product.table.edit.title.new': 'New product',
            'product.table.edit.title.edit': 'Update product',
            'product.table.edit.name': 'Product name',
            'product.table.edit.name.hint': 'Name',
            'product.table.edit.name.required': 'Name is required',

            /*job-type.table*/
            'job-type.table.column.name' : 'Name',
            'job-type.table.delete.confirm' : 'Do you want to delete job type?',
            'job-type.table.delete.confirm.many' : 'Do you want to delete {0} job types?',

            /*job-type.table.edit*/
            'job-type.table.edit.title.new': 'New job type',
            'job-type.table.edit.title.edit': 'Update job type',
            'job-type.table.edit.name': 'Job type name',
            'job-type.table.edit.name.hint': 'Name',
            'job-type.table.edit.name.required': 'Name is required',

            /*job-subtype.table*/
            'job-subtype.table.column.name' : 'Name',
            'job-subtype.table.column.use-in-salary-report' : 'Salary report',
            'job-subtype.table.column.job-type': 'Job type',
            'job-subtype.table.delete.confirm' : 'Do you want to delete job sub type?',
            'job-subtype.table.delete.confirm.many' : 'Do you want to delete {0} job sub types?',
            'job-subtype.table.filter.use-in-salary-report': 'Use in salary report',
            'job-subtype.table.filter.not-use-in-salary-report': 'Don`t use in salary report',

            /*job-subtype.table.edit*/
            'job-subtype.table.edit.title.new': 'New job sub type',
            'job-subtype.table.edit.title.edit': 'Update job sub type',
            'job-subtype.table.edit.name': 'Job sub type name',
            'job-subtype.table.edit.name.hint': 'Name',
            'job-subtype.table.edit.name.required': 'Name is required',
            'job-subtype.table.edit.job-type.required': 'Job type is required',
            'job-subtype.table.edit.job-type': 'Job type',
            'job-subtype.table.edit.use-in-salary-report': 'Use in salary report',
            'job-subtype.table.edit.use-in-salary-report.hint': 'Use in salary report',
            'job-subtype.table.edit.report-group': 'Report group',
            'job-subtype.table.edit.report-group.hint': 'Group for salary report',

             /*job-subtype-cost.table*/
            'job-subtype-cost.table.column.job-type' : 'Job type',
            'job-subtype-cost.table.column.job-subtype' : 'Job sub type',
            'job-subtype-cost.table.column.product': 'Product',
            'job-subtype-cost.table.column.cost': 'Cost ($)',
            'job-subtype-cost.table.delete.confirm' : 'Do you want to delete job type cost?',
            'job-subtype-cost.table.delete.confirm.many' : 'Do you want to delete {0} job type costs?',

             /*job-subtype-cost.table.edit*/
            'job-subtype-cost.table.edit.title.new': 'New job type cost',
            'job-subtype-cost.table.edit.title.edit': 'Update job type cost',
            'job-subtype-cost.table.edit.job-subtype': 'Job sub type',
            'job-subtype-cost.table.edit.job-subtype.hint': 'Job sub type',
            'job-subtype-cost.table.edit.job-subtype.required': 'Job sub type is required',
            'job-subtype-cost.table.edit.product': 'Product',
            'job-subtype-cost.table.edit.product.hint': 'product',
            'job-subtype-cost.table.edit.product.required': 'Product is required',
            'job-subtype-cost.table.edit.cost': 'Cost',
            'job-subtype-cost.table.edit.cost.hint': 'Cost',
            'job-subtype-cost.table.edit.cost.required': 'Cost is required',
            'job-subtype-cost.already.exists.cost.update.question': 'Cost for this job subtype and product already exists. Do you want update cost?',

            /*job.table*/
            'job.table.column.number': 'Number',
            'job.table.column.complete.date': 'Complete date',
            'job.table.column.job-type' : 'Job type',
            'job.table.column.product': 'Product',
            'job.table.delete.confirm' : 'Do you want to delete job?',
            'job.table.delete.confirm.many' : 'Do you want to delete {0} jobs?',

            'job.table.edit.title.new': 'New job',
            'job.table.edit.title.edit': 'Update job',
            'job.table.edit.job-type': 'Job type',
            'job.table.edit.product': 'Product',
            'job.table.edit.job-type.required': 'Job type is required',
            'job.table.edit.product.required': 'Product is required',
            'job.table.edit.number': 'Number',
            'job.table.edit.number.hint': 'Number',
            'job.table.edit.number.required': 'Number is required',
            'job.table.edit.complete.date':'Complete job data',
            'job.table.edit.complete.date.hint': 'Complete job data',
            'job.table.edit.complete.date.required': 'Complete job data is required',
            'job.table.show.tail': 'Show tail',
            'job.table.hide.tail': 'Hide tail',
            'job.table.hide.tail.hint': 'Jobs from previous week that not finished',

            'typeahead.fixed.element.hint' : 'Use last selected element'
        });

})();