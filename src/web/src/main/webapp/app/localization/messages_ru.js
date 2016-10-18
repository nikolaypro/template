(function () {
    'use strict';

    angular.module('localization')
        .value('localizedTextsRU', {
            '+ Category': '+ Категория',
            'edit Carte': 'Карта',

            /*common*/
            'common.new' : 'Создать',
            'common.edit' : 'Редактировать',
            'common.remove' : 'Удалить',
            'common.save' : 'Сохранить',
            'common.cancel' : 'Отмена',
            'common.error' : 'Ошибка',

            /*top.menu*/
            'top.menu.users' : 'Пользователи',
            'top.menu.reports' : 'Отчеты',
            'top.menu.logout' : 'Выход',
            'top.menu.app.name' : 'Шаблон',

            /*top.menu.user.details*/
            'top.menu.user.details.name' : 'Пользователь',
            'top.menu.user.details.roles' : 'Роли',


            /*user.table.column*/
            'user.table.column.name' : 'Имя',
            'user.table.column.login' : 'Логин',
            'user.table.column.roles' : 'Роли',

            /*user.table.edit.name*/
            'user.table.edit.title.new' : 'Новый пользователь',
            'user.table.edit.title.edit' : 'Редактирование пользователя',

            'user.table.edit.name' : 'Имя пользователя',
            'user.table.edit.name.hint' : 'Введите имя пользователя',
            'user.table.edit.name.required' : 'Имя пользователя не задано',

            'user.table.edit.login' : 'Логин',
            'user.table.edit.login.hint' : 'Введите логин',
            'user.table.edit.login.required' : 'Логин не задан',

            'user.table.edit.edit.password.btn' : 'Редактировать пароль',

            'user.table.edit.password' : 'Пароль',
            'user.table.edit.password.hint' : 'Введите пароль',
            'user.table.edit.password.required' : 'Пароль не задан',
            'user.table.edit.password.repeat' : 'Повторите пароль',
            'user.table.edit.password.not.equals' : 'Пароли не совпадают',

            'user.table.edit.roles' : 'Роли',
            'user.table.edit.roles.not.defined' : 'Не задана ни одна роль',

            'user.table.edit.locale' : 'Язык',
            'language.ru_RU' : 'Русский',
            'language.en_UK' : 'Английский',

            /*user.table.edit.messages*/
            'user.table.delete.confirm' : 'Удалить пользователя?',
            'user.table.delete.confirm.many' : 'Удалить пользователей (количество: {0})?',

            /* ROLES */
            'ROLE_ADMIN': 'Администратор',
            'ROLE_REGULAR': 'Сотрудник',

            /* REPORTS */
            'report.name.sign.in.out.user' : 'Входы/выходы пользователей',
            'report.name.users' : 'Список пользователей',
            'report.create' : 'Создать отчет',
            'report.date.from' : 'От',
            'report.date.to' : 'До',

            'report.users.full_name.column' : 'Имя',
            'report.users.login.column' : 'Логин',
            'report.users.roles.column' : 'Роли'

        });

})();