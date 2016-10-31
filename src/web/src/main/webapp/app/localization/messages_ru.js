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
            'common.loading' : 'Загрузка ...',
            'common.no.result.found' : 'Ничего не найдено',

            /*top.menu*/
            'top.menu.users' : 'Пользователи',
            'top.menu.reports' : 'Отчеты',
            'top.menu.dictionary' : 'Справочники',
            'top.menu.product.type' : 'Вид изделия',
            'top.menu.job.type' : 'Вид работ',
            'top.menu.job.sub.type' : 'Подвид работ',
            'top.menu.job.price' : 'Стоимость работ',
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
            'report.users.roles.column' : 'Роли',

            /*product.table*/
            'product.table.column.name' : 'Название изделия',
            'product.table.delete.confirm' : 'Удалить изделие?',
            'product.table.delete.confirm.many' : 'Удалить изделия (количество: {0})?',

            /*product.table.edit*/
            'product.table.edit.title.new': 'Новое изделие',
            'product.table.edit.title.edit': 'Редактирование изделия',
            'product.table.edit.name': 'Название изделия',
            'product.table.edit.name.hint': 'Название',
            'product.table.edit.name.required': 'Название не задано',

            /*job-type.table*/
            'job-type.table.column.name' : 'Название вида работ',
            'job-type.table.delete.confirm' : 'Удалить вид работы?',
            'job-type.table.delete.confirm.many' : 'Удалить вид работы (количество: {0})?',

            /*job-type.table.edit*/
            'job-type.table.edit.title.new': 'Новый вид работы',
            'job-type.table.edit.title.edit': 'Редактирование вида работы',
            'job-type.table.edit.name': 'Название вида работы',
            'job-type.table.edit.name.hint': 'Название',
            'job-type.table.edit.name.required': 'Название не задано',

            /*job-subtype.table*/
            'job-subtype.table.column.name' : 'Название подвида работ',
            'job-subtype.table.column.job-type': 'Вид работы',
            'job-subtype.table.delete.confirm' : 'Удалить подвид работы?',
            'job-subtype.table.delete.confirm.many' : 'Удалить подвид работы (количество: {0})?',

            /*job-subtype.table.edit*/
            'job-subtype.table.edit.title.new': 'Новый подвид работы',
            'job-subtype.table.edit.title.edit': 'Редактирование подвида работы',
            'job-subtype.table.edit.name': 'Название подвида работы',
            'job-subtype.table.edit.name.hint': 'Название',
            'job-subtype.table.edit.name.required': 'Название не задано',
            'job-subtype.table.edit.job-type.required': 'Вид работы не задан',
            'job-subtype.table.edit.job-type': 'Вид работы',

            /*job-subtype-cost.table*/
            'job-subtype-cost.table.column.job-subtype' : 'Подвид работы',
            'job-subtype-cost.table.column.product': 'Изделие',
            'job-subtype-cost.table.column.cost': 'Стоимость',
            'job-subtype-cost.table.delete.confirm' : 'Удалить стоимость работы?',
            'job-subtype-cost.table.delete.confirm.many' : 'Удалить стоимости работ (количество: {0})?',

                /*job-subtype-cost.table.edit*/
            'job-subtype-cost.table.edit.title.new': 'Новая стоимость работы',
            'job-subtype-cost.table.edit.title.edit': 'Редактироание стоимости работы',
            'job-subtype-cost.table.edit.job-subtype': 'Подвид работы',
            'job-subtype-cost.table.edit.job-subtype.hint': 'Подвид работы',
            'job-subtype-cost.table.edit.job-subtype.required': 'Подвид роботы не задан',
            'job-subtype-cost.table.edit.product': 'Изделие',
            'job-subtype-cost.table.edit.product.hint': 'Изделие',
            'job-subtype-cost.table.edit.product.required': 'Изделие не задано',
            'job-subtype-cost.table.edit.cost': 'Стоимость',
            'job-subtype-cost.table.edit.cost.hint': 'Стоимость',
            'job-subtype-cost.table.edit.cost.required': 'Стоимость не задана'

        });

})();