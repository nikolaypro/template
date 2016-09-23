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


        });

})();