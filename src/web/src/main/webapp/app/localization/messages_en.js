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
            'common.error' : 'Danger',

            /*top.menu*/
            'top.menu.users' : 'Users',
            'top.menu.reports' : 'Reports',
            'top.menu.dictionary' : 'Dictionaries',
            'top.menu.product.type' : 'Product type',
            'top.menu.job.type' : 'Job type',
            'top.menu.job.sub.type' : 'Job sub type',
            'top.menu.job.price' : 'Job price',
            'top.menu.logout' : 'Logout',
            'top.menu.app.name' : 'Template',

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

            'report.users.full_name.column' : 'Name',
            'report.users.login.column' : 'Login',
            'report.users.roles.column' : 'Roles',

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
            'job-type.table.edit.name.required': 'Name is required'
        });

})();