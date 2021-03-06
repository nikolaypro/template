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
            'common.close' : 'Закрыть',
            'common.clear' : 'Очистить',
            'common.error' : 'Ошибка',
            'common.loading' : 'Загрузка ...',
            'common.no.result.found' : 'Ничего не найдено',
            'common.yes' : 'Да',
            'common.no' : 'Нет',
            'common.warning' : 'Предупреждение',
            'common.filter.hide' : 'Скрыть фильтры',
            'common.filter.show' : 'Показать фильтр',
            'common.filter.clear.tooltip' : 'Очистить фильтр',
            'common.document.title' : 'Система недельного расчета стоимости выполненных работ',

            'calendar.current.day' : 'Сегодня',

            /*top.menu*/
            'top.menu.users' : 'Пользователи',
            'top.menu.reports' : 'Отчеты',
            'top.menu.dictionary' : 'Справочники',
            'top.menu.product.type' : 'Вид изделия',
            'top.menu.job.type' : 'Вид работ',
            'top.menu.job.sub.type' : 'Подвид работ',
            'top.menu.job.price' : 'Стоимость работ',
            'top.menu.jobs' : 'Выполненные работы',
            'top.menu.logout' : 'Выход',
            'top.menu.app.name' : 'Система расчета работ',

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

            'user.table.edit.web' : 'Доступен для портала',
            'user.table.edit.web.hint' : 'Доступен для портала',

            'language.ru_RU' : 'Русский',
            'language.en_UK' : 'Английский',

            /*user.table.edit.messages*/
            'user.table.delete.confirm' : 'Удалить пользователя?',
            'user.table.delete.confirm.many' : 'Удалить пользователей (количество: {0})?',

            /* ROLES */
            'ROLE_ADMIN': 'Администратор',
            'ROLE_REGULAR': 'Оператор',

            /* REPORTS */
            'report.name.sign.in.out.user' : 'Входы/выходы пользователей',
            'report.name.users' : 'Список пользователей',
            'report.create' : 'Создать отчет',
            'report.date.from' : 'От',
            'report.date.to' : 'До',
            'report.create.date' : 'Отчет был создан: {0}',

            'report.users.full_name.column' : 'Имя',
            'report.users.login.column' : 'Логин',
            'report.users.roles.column' : 'Роли',

            'report.salary' : 'Отчет о выполненных работах за неделю',
            'report.name.salary' : 'Отчет о выполненных работах за неделю',
            'report.salary.title.period' : 'Период: {0} - {1}',
            'report.salary.job.type.column' : 'Тип работы',
            'report.salary.cost.column' : 'Суммарная стоимость (руб)',
            'report.salary.count.column' : 'Количество',
            'report.salary.product.cost.column' : 'Ст-ть 1 изд-я',

            'report.salary.with.subtypes' : 'Отчет о выполненных работах за неделю с подвидами работ',
            'report.name.salary.with.subtypes' : 'Отчет о выполненных работах за неделю с подвидами работ',

            'report.salary.with.group' : 'Отчет о выполненных работах за неделю группированный',
            'report.name.salary.with.group' : 'Отчет о выполненных работах за неделю группированный',

            'report.salary.investigation' : 'Отчет для исследования расчета зарплаты',
            'report.name.salary.investigation' : 'Отчет для исследования расчета зарплаты',
            'report.salary.product.count' : 'кол-во изделий',
            'report.salary.investigation.job.type.column' : 'Подтип работы / изделие',

            'report.please.enable.popup.window' : 'Невозможно открыть новое окно (вкладку) для отчета. Возможно, запрещен показ высплывающих окон. Разрешите, пожалуйста, высплывающие окна для данного сайта.',

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
            'job-subtype.table.column.use-in-salary-report' : 'Исп. в рассчете зарплаты',
            'job-subtype.table.delete.confirm' : 'Удалить подвид работы?',
            'job-subtype.table.delete.confirm.many' : 'Удалить подвид работы (количество: {0})?',
            'job-subtype.table.filter.use-in-salary-report': 'Использовать для расчета зарплаты',
            'job-subtype.table.filter.not-use-in-salary-report': 'Не использовать для расчета зарплаты',

            /*job-subtype.table.edit*/
            'job-subtype.table.edit.title.new': 'Новый подвид работы',
            'job-subtype.table.edit.title.edit': 'Редактирование подвида работы',
            'job-subtype.table.edit.name': 'Название подвида работы',
            'job-subtype.table.edit.name.hint': 'Название',
            'job-subtype.table.edit.name.required': 'Название не задано',
            'job-subtype.table.edit.job-type.required': 'Вид работы не задан',
            'job-subtype.table.edit.job-type': 'Вид работы',
            'job-subtype.table.edit.use-in-salary-report': 'Использовать в расчете зарплаты',
            'job-subtype.table.edit.use-in-salary-report.hint': 'Использовать в расчете зарплаты',
            'job-subtype.table.edit.report-group': 'Группа в отчете',
            'job-subtype.table.edit.report-group.hint': 'Группа в отчете зарплаты',

            /*job-subtype-cost.table*/
            'job-subtype-cost.table.column.job-type' : 'Вид работы',
            'job-subtype-cost.table.column.job-subtype' : 'Подвид работы',
            'job-subtype-cost.table.column.product': 'Изделие',
            'job-subtype-cost.table.column.cost': 'Руб',
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
            'job-subtype-cost.table.edit.cost.required': 'Стоимость не задана',
            'job-subtype-cost.already.exists.cost.update.question': 'Стоимость выбранной работы для данного изделия уже задана. Хотите обновить стоимость?',

            /*job.table*/
            'job.table.column.number': 'Номер',
            'job.table.column.complete.date': 'Дата завершения',
            'job.table.column.job-type' : 'Вид работы',
            'job.table.column.product': 'Изделие',
            'job.table.delete.confirm' : 'Удалить выполненную работу?',
            'job.table.delete.confirm.many' : 'Удалить выполненные работы? (количество: {0})',

            'job.table.edit.title.new': 'Новая выполненная работа',
            'job.table.edit.title.edit': 'Редактирование выполненной работы',
            'job.table.edit.job-type': 'Вид работы',
            'job.table.edit.product': 'Изделие',
            'job.table.edit.job-type.required': 'Вид работы не задан',
            'job.table.edit.product.required': 'Изделие не задано',
            'job.table.edit.number': 'Номер',
            'job.table.edit.number.hint': 'Номер',
            'job.table.edit.number.required': 'Номер не задан',
            'job.table.edit.complete.date':'Дата завершения работы',
            'job.table.edit.complete.date.hint': 'Дата завершения работы',
            'job.table.edit.complete.date.required': 'Дата завершения работы не задана',
            'job.table.show.tail': 'Показать "хвост"',
            'job.table.hide.tail': 'Скрыть "хвост"',
            'job.table.hide.tail.hint': 'Незаконченные работы с прошлой недели',
            'job.table.edit.count': 'Количество',
            'job.table.edit.count.hint': 'Количество',
            'job.table.edit.count.required': 'Не задано количество',

            'typeahead.fixed.element.hint' : 'Последний выбранный элемент',

            'progress.bat.state.label' : 'Текущее действие',

            /*synchronization*/
            'top.menu.synchronization' : 'Синхронизация',
            'synchronize.page.button' : 'Синхронизировать',
            'synchronize.page.log.date' : 'Лог на дату',
            'synchronize.page.change.data.log.hint' : 'Сменить дату лога',
        });

})();